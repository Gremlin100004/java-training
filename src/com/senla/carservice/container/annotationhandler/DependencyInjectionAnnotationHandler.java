package com.senla.carservice.container.annotationhandler;

import com.senla.carservice.container.annotation.Dependency;
import com.senla.carservice.container.contex.Context;
import com.senla.carservice.exception.BusinessException;

import java.lang.reflect.Field;

public class DependencyInjectionAnnotationHandler {
    private final Context context;

    public DependencyInjectionAnnotationHandler(Context context) {
        this.context = context;
    }

    public <T> T configure(T inputObject) {
        for (Field field : inputObject.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Dependency.class)) {
                // считается, что нужно вернуть прежнее значение Accessible
                field.setAccessible(true);
                Object object = context.getObject(field.getType().getName());
                try {
                    field.set(inputObject, object);
                } catch (IllegalAccessException e) {
                    throw new BusinessException("Error set value to a field");
                }
            }
        }
        return inputObject;
    }
}