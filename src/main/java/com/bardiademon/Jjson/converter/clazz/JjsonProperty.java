package com.bardiademon.Jjson.converter.clazz;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JjsonProperty {
    String name() default "";

    boolean ignore() default false;

    boolean ignoreIfNull() default false;

    boolean nullable() default true;

    // If field value is null and nullable == false
    String defaultValueMethodName() default "";
}
