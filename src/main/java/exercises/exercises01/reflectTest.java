package exercises.exercises01;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class reflectTest {
    public static void main(String[] args) throws Exception{
        //1、得到Class类
        Class<?> cls = Class.forName("exercises.exercises01.People");

        Object o = cls.newInstance();

        //2、反射构造函数
        //getFields() 只会得到public修饰的field
        Field[] fields = cls.getFields();
        for (Field field : fields) {
            System.out.println(field);
        }
        System.out.println("====================");
        //getDeclaredFields() 得到所有的成员变量
        Field[] fields1 = cls.getDeclaredFields();
        for (Field field : fields1) {
            System.out.println(field);
        }
        System.out.println("=====================");
        Field a = cls.getDeclaredField("a");
        Field b = cls.getDeclaredField("b");
        Field c = cls.getDeclaredField("c");
        //如果方法是private，Accessible无障碍要设置true
        Field d = cls.getDeclaredField("d");
        //此时的get 、 set方法是成员变量自带的，不管People类里面有没有set，get方法
        a.set(o,"1");
        b.set(o,"2");
        c.set(o,"3");
        d.setAccessible(true);
        d.set(o,"4");
        System.out.println(o);
        System.out.println("======================");
        //getMethods
        //getDeclaredMethods
        Method[] methods = cls.getMethods();
        for (Method method : methods) {
            System.out.println(method);
        }
        System.out.println("=======================");
        Method[] methods1 = cls.getDeclaredMethods();
        for (Method method : methods1) {
            System.out.println(method);
        }
        System.out.println("=====================");
        //getConstructors
        //getDeclaredConstructors 包含了public和非public
        Constructor<?>[] constructors = cls.getConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println(constructor);
        }
        System.out.println("===========================");
        Constructor<?>[] constructors1 = cls.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors1) {
            System.out.println(constructor);
        }

    }
}
