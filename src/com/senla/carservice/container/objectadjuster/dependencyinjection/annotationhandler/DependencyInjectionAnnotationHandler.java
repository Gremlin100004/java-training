package com.senla.carservice.container.objectadjuster.dependencyinjection.annotationhandler;

import com.senla.carservice.container.contex.Context;
import com.senla.carservice.container.objectadjuster.AnnotationHandler;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.exception.BusinessException;

import java.lang.reflect.Field;

public class DependencyInjectionAnnotationHandler implements AnnotationHandler {
    private Context context;

    public DependencyInjectionAnnotationHandler() {
    }

    @Override
    public void configure(Object classObject) {
        for (Field field : classObject.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Dependency.class)) {
                field.setAccessible(true);
                try {
                    field.set(classObject, context.getObject(field.getType()));
                } catch (IllegalAccessException e) {
                    // это не похоже на бизнес исключение
                    // не забываем про независимость от основного проекта
                    throw new BusinessException("Error set value to a field");
                }
            }
        }
    }
}