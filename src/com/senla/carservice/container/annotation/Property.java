package com.senla.carservice.container.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
// в задании указано точное название для аннотации
public @interface Property {
    String configName() default "";

    String propertyName() default "";

    // для типа лучше использовать енам
    String type() default "";
}