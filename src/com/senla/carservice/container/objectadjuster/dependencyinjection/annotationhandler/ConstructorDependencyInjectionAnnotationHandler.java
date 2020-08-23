package com.senla.carservice.container.objectadjuster.dependencyinjection.annotationhandler;

import com.senla.carservice.container.contex.Context;
import com.senla.carservice.container.exception.InitializationException;
import com.senla.carservice.container.objectadjuster.AnnotationHandler;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.ConstructorDependency;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ConstructorDependencyInjectionAnnotationHandler implements AnnotationHandler {

    public ConstructorDependencyInjectionAnnotationHandler() {
    }

    @Override
    public void configure(Object classInstance, Context context) {
        for (Constructor constructor : classInstance.getClass().getConstructors()) {
            if (constructor.isAnnotationPresent(ConstructorDependency.class)) {
                Class[] classTypes = constructor.getParameterTypes();
                Object[] objects = new Object[classTypes.length];
                for (int i = 0; i < classTypes.length; i++) {
                    objects[i] = context.getObject(classTypes[i]);
                }
                try {
                    context.setSingleton(constructor.newInstance(objects));
                } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                    throw new InitializationException("Error set value to a constructor");
                }
            }
        }
    }
}