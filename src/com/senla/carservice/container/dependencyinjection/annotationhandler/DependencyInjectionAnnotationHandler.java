package com.senla.carservice.container.dependencyinjection.annotationhandler;

import com.senla.carservice.container.contex.Context;
import com.senla.carservice.container.dependencyinjection.annotation.Dependency;
import com.senla.carservice.exception.BusinessException;

import java.lang.reflect.Field;

public class DependencyInjectionAnnotationHandler {
    private final Context context;

    public DependencyInjectionAnnotationHandler(Context context) {
        this.context = context;
    }

    public void configure(Object classObject) {
        for (Field field : classObject.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Dependency.class)) {
                field.setAccessible(true);
                try {
                    field.set(classObject, context.getObject(field.getType()));
                } catch (IllegalAccessException e) {
                    throw new BusinessException("Error set value to a field");
                }
                field.setAccessible(false);
            }
        }
    }
}