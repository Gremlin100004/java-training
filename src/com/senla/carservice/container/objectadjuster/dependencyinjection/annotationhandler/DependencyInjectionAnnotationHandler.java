package com.senla.carservice.container.objectadjuster.dependencyinjection.annotationhandler;

import com.senla.carservice.container.contex.Context;
import com.senla.carservice.container.exception.InitializationException;
import com.senla.carservice.container.objectadjuster.AnnotationHandler;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;

import java.lang.reflect.Field;

public class DependencyInjectionAnnotationHandler implements AnnotationHandler {

    public DependencyInjectionAnnotationHandler() {
    }

    @Override
    public void configure(Object classInstance, Context context) {
        for (Field field : classInstance.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Dependency.class)) {
                field.setAccessible(true);
                try {
                    field.set(classInstance, context.getObject(field.getType()));
                } catch (IllegalAccessException e) {
                    throw new InitializationException("Error set value to a field");
                }
            }
        }
    }
}