package hw07.custom.annotations.methods;

import hw07.custom.annotations.CustomRequestMapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@CustomRequestMapping(
        httpMethod = "PUT"
)
public @interface CustomPutMapping {
    String value() default "/";
}