package reflect;

import java.lang.reflect.Constructor;


public class ConstructorTest {
    //反射，不用new对象
    public static void main(String[] args) throws Exception {
        Class<?> aClass = Class.forName("reflect.Test");
        //得到其构造方法
        Constructor<?> constructor = aClass.getDeclaredConstructor(int.class,String.class);
        //通过构造方法创建对象
        Test test = (Test) constructor.newInstance(1,"test1");
        System.out.println(test);

        Constructor<?>[] constructors = aClass.getConstructors();
        Test o = (Test) constructors[1].newInstance(2, "test2");
        System.out.println(o);

        Constructor<?> constructor1 = aClass.getConstructor(int.class, String.class);
        Test test3 = (Test) constructor1.newInstance(3,"test3");
        System.out.println(test3);

        Test o1 = (Test) aClass.newInstance();
        o1.setId(4);
        o1.setName("test4");
        System.out.println(o1);
    }
}
