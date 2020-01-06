/*
package socket.nio;


import socket.bs.HttpServer;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class ServerTest {
    public static void main(String[] args) throws IOException {
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
        System.out.println(requestMsg);
//        System.out.println("Request: " + url);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HTTP/1.1 200 OK\r\n");
        stringBuilder.append("Content-Type:text/html;charset=utf-8\r\n");
        stringBuilder.append("\r\n");
        stringBuilder.append("<html><head><title>HttpTest</title></head><body>");

        String content = HttpServer.contentMap.get(url);
        stringBuilder.append(content != null ? content : "404");
        stringBuilder.append("</body></html>");

        socketChannel.write(ByteBuffer.wrap(stringBuilder.toString().getBytes()));
        socketChannel.close();
    }

}
*/
