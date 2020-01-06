package reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MethodTest {
    public static void main(String[] args) throws Exception{
        Class<?> aClass = Class.forName("reflect.Test");
        Constructor<?>[] constructors = aClass.getDeclaredConstructors();
        Test test1 = (Test) constructors[1].newInstance(2, "test1");
        Method setId = aClass.getDeclaredMethod("setId", int.class);
        setId.invoke(test1,1);
        System.out.println(test1);
        Method gotoTest = aClass.getDeclaredMethod("gotoTest");
        gotoTest.invoke(test1);
        Method setName = aClass.getDeclaredMethod("setName", String.class);
        setName.invoke(test1,"test2");
        System.out.println(test1);
    }
}
