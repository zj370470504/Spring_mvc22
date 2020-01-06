package reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class FieldTest {
    public static void main(String[] args) throws Exception {
        Class<?> aClass = Class.forName("reflect.Test");
        Constructor<?> declaredConstructor = aClass.getDeclaredConstructor(int.class,String.class);
        Test test = (Test) declaredConstructor.newInstance(1,"");
        Field field = aClass.getDeclaredField("name");
        field.setAccessible(true);
        field.set(test,"test1");
        System.out.println(test);
    }
}
