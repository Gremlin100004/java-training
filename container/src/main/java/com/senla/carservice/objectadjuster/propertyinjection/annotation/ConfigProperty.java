package com.senla.carservice.objectadjuster.propertyinjection.annotation;

import com.senla.carservice.objectadjuster.propertyinjection.enumeration.TypeField;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigProperty {
    String configName() default "";

    String propertyName() default "";

    TypeField type() default TypeField.DEFAULT;
}