package com.senla.carservice.objectadjuster.dependencyinjection.annotationhandler;

import com.senla.carservice.contex.Context;
import com.senla.carservice.exception.InitializationException;
import com.senla.carservice.objectadjuster.AnnotationHandler;
import com.senla.carservice.objectadjuster.dependencyinjection.annotation.Dependency;

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