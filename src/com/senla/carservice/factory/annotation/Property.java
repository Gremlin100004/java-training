package com.senla.carservice.factory.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
// нарушено условие задания: такой аннотации нет в задаче, не реализованы атрибуты, которые требовались по условию
// задачи
public @interface Property {
    String value() default "";
}