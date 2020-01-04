package work.work01.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)//运行时使用
@Target(ElementType.METHOD)//注解使用在方法上
public @interface MyRequestMapping {
    String value() default "/";
}
