package com.senla.carservice.container.annotationhandler;

import com.senla.carservice.container.Context;
import com.senla.carservice.container.annotation.Dependency;

import java.lang.reflect.Field;

public class DependencyInjectionAnnotationHandler {
    private final Context context;

    public DependencyInjectionAnnotationHandler(Context context) {
        this.context = context;
    }

    public  Object configure(Object inputObject) {
        for (Field field : inputObject.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Dependency.class)) {
                field.setAccessible(true);
                Object object = context.getObject(field.getType().getName());
                try {
                    field.set(inputObject, object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    //TODO : Add logging.
                }
            }
        }
        return inputObject;
    }
}