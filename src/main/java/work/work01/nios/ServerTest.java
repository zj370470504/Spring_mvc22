package work.work01.nios;



import work.work01.annotation.MyRequestMapping;
import work.work01.annotation.MyRestController;
import work.work01.model.MothedPojo;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class ServerTest {
    public static void main(String[] args) throws IOException {
        refreshBeanFactory("work.work01.controller");

        //1、开始Socket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //2、线程池监控的端口
        serverSocketChannel.socket().bind(new InetSocketAddress(80));
        //3、配置非阻塞
        serverSocketChannel.configureBlocking(false);
        //4、开启选择器(线程池),    里面装的socket？
        Selector selector = Selector.open();
        //5、socket去向选择去注册，注册模式是accept接收，  打开一个socket就要去注册？
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Http Server Started.");
        //6、循环去池子里面拿socket
        while (true){
            //3秒内没有的话，直接continue
            if (selector.select(3000) == 0)
                continue;
        //7、遍历取出池子里面的socket
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()){
                SelectionKey key = keyIterator.next();
        //8、处理这个socket
                httphandle(key);
                keyIterator.remove();
            }
        }
    }

    /**
     * beanMap。（IOC容器）
     * param1：方法所在类的类名
     * param2：这个类名的实例类
     * 怎么得到实例类？
     * 需要Class类后newInstance
     */
    public static Map<String, Object> beanMap = new HashMap<>();
    /**
     * methodMap
     * param1:url  /hello
     * param2:<method,String> String 方法所在的类的类名
     */
    public static Map<String, MothedPojo> methodMap = new HashMap<>();

    /**
     * 通过包名，得到该包下的所有.class文件
     * 初始化beanMap、methodMap
     * @param pkg
     * @see #beanParse(File)
     */
    public static void refreshBeanFactory(String pkg){
        String path = pkg.replace(".","/");
        //得到全路径
        URL url = ServerTest.class.getClassLoader().getResource(path);
//        System.out.println(url);
//        System.out.println(url.getPath());
        File rootdir = new File(url.getPath());
        //解析class
        beanParse(rootdir);

    }

    /**
     * 解析class
     * @param rootdir
     * @see #controllerParse(Class)
     */
    private static void beanParse(File rootdir) {
        if (!rootdir.isDirectory())
            return;
        File[] files = rootdir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.isDirectory()){
                    beanParse(pathname);
                    return false;
                }
                return pathname.getName().endsWith(".class");
            }
        });
        for (File file : files) {
            String path = file.getAbsolutePath();
            //System.out.println(path);
            String className = path.split("classes\\\\")[1].split("\\.class")[0].replace("\\", ".");
            System.out.println(className);
            try {
                Class<?> aClass = Class.forName(className);
                MyRestController myRestController = aClass.getAnnotation(MyRestController.class);
//                System.out.println(myRestController);
                  if (myRestController == null) {
                      System.out.println("没有这个注解");
                  }else
                      //终于得到了这个这个类的字节码文件了。。。。。。。。处理
                      controllerParse(aClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 得到class后，进行初始化 注入。结束。
     * @param aClass
     * @throws Exception
     */
    private static void controllerParse(Class<?> aClass) throws Exception {
        //得到实例，注入BeanMap
        Object object = aClass.newInstance();
        beanMap.put(aClass.getSimpleName(),object);
        //遍历所有的方法
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            MyRequestMapping annotation = method.getAnnotation(MyRequestMapping.class);
            if (annotation == null)
                continue;
            methodMap.put(annotation.value(),new MothedPojo(method,aClass.getSimpleName()));
        }
    }


    public static void httphandle(SelectionKey key) throws IOException {
        if (key.isAcceptable()){
            acceptHandle(key);
        }else if (key.isReadable()){
            requestHandle(key);
        }
    }

    public static void acceptHandle(SelectionKey key) throws IOException {
        SocketChannel socketChannel = ((ServerSocketChannel)key.channel()).accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(key.selector(),SelectionKey.OP_READ, ByteBuffer.allocate(1024));
    }

    public static void requestHandle(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
        byteBuffer.clear();
        if (socketChannel.read(byteBuffer) == -1) {
            socketChannel.close();
            return;
        }
        byteBuffer.flip();
        String requestMsg = new String(byteBuffer.array());
        String url = requestMsg.split("\r\n")[0].split(" ")[1];
        List<String> urlParams = new ArrayList<>();
        //解析url
        urlParamsParse(url, urlParams);
        System.out.println(url);
        //url: /hello?id=1
        url = url.split("\\?")[0];
        //url: /hello
        String content = null;
        try {
            //
            content = methodInvoke(url, urlParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (content == null)
            content = "404";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HTTP/1.1 200 OK\r\n");
        stringBuilder.append("Content-Type:text/html;charset=utf-8\r\n");
        stringBuilder.append("\r\n");
        stringBuilder.append(content);
        socketChannel.write(ByteBuffer.wrap(stringBuilder.toString().getBytes()));
        socketChannel.close();
    }


    /**
     * 解析url参数
     * 为什么要解析url呢？因为要处理有参数的情况。没参数就return回去正常运行，有参数就处理。怎么处理呢？
     * @param url
     * @param urlParams
     */
    private static void urlParamsParse(String url, List<String> urlParams) {
        if (!url.contains("?"))
            return;
        // /hello?id=1&name=dd&ssss => id=1&name=dd&ssss => [id=1, name=dd, ssss]
        String[] ps = url.replaceFirst(".*?\\?", "").split("&");
        for (String p: ps) {
            // 过滤ssss
            if (!p.contains("="))
                continue;
            urlParams.add(p);
        }
    }

    /**
     * 调用url所映射的方法
     * @param url
     * @param urlParams
     * @return
     */
    private static String methodInvoke(String url, List<String> urlParams) throws InvocationTargetException, IllegalAccessException {
        MothedPojo methodInfo = methodMap.get(url);
        if (methodInfo == null)
            return "404";
        String methodClassName = methodInfo.getMethodClassName();
        //方法名
        Method methodName = methodInfo.getMethodName();
        //实体类
        Object o = beanMap.get(methodClassName);
        //得到手动输入的参数的个数
        Object[] params = new Object[urlParams.size()];
        //得到方法的参数的个数
        Parameter[] parameters = methodName.getParameters();
        if (parameters.length != params.length)
            System.out.println("输入的参数个数不匹配");
        int i=0;
        for (Parameter parameter : parameters) {
            String types = parameter.getType().getSimpleName();
            String name = parameter.getName();
            boolean flag = false;
            for (String urlParam : urlParams) {
                String[] split = urlParam.split("=");
                if (name.equals(split[0])){
                    Object paramTranslate = paramTranslate(types, split[1]);
                    params[i++] = paramTranslate;
                    flag = true;
                    continue;
                }
            }
            if (!flag)
                return "参数名称不匹配";
        }
        return (String) methodName.invoke(o, params);
    }

    private static Object paramTranslate(String types, String s) {
        switch (types) {
            case "int":
                return Integer.valueOf(s);
            case "Double":
                return Double.valueOf(s);
            case "Float":
                return Float.valueOf(s);
            default:
                return s;
        }
    }

}
