package com.senla.carservice.container.contex;

import com.senla.carservice.container.objectadjuster.AnnotationHandler;
import com.senla.carservice.container.configurator.Configurator;
import com.senla.carservice.exception.BusinessException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {
    private final Map<String, Object> singletons = new HashMap<>();
    private final List<AnnotationHandler> annotationHandlers = new ArrayList<>();
    private final Configurator configurator;

    public Context(final Configurator configurator) {
        this.configurator = configurator;
    }

    public void createAnnotationHandlers(Context context) {
        for (Class<?> classImplementation : configurator.getAnnotationHandlerClasses()) {
            AnnotationHandler annotationHandler = createRawObject(classImplementation);
            for (Field field : annotationHandler.getClass().getDeclaredFields()) {
                if (field.getType().equals(Context.class)) {
                    field.setAccessible(true);
                    try {
                        field.set(annotationHandler, context);
                        break;
                    } catch (IllegalAccessException e) {
                        throw new BusinessException("Error set value to a field");
                    }
                }
            }
            annotationHandlers.add(annotationHandler);
        }
    }

    public void createSingletons() {
        for (Map.Entry<String, Class<?>> singletonEntry : configurator.getSingletonClasses().entrySet()) {
            singletons.put(singletonEntry.getKey(), createRawObject(singletonEntry.getValue()));
        }
    }

    public void configureSingletons() {
        for (Object classObject : singletons.values()) {
            configureObject(classObject);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(Class<T> objectClass) {
        T classObject = (T) singletons.get(objectClass.getName());
        if (classObject != null) {
            return classObject;
        }
        Class<?> classImplementation = configurator.getPrototypeClass(objectClass.getName());
        if (classImplementation == null) {
            throw new BusinessException("Error input class name");
        }
        classObject = createRawObject(classImplementation);
        configureObject(classObject);
        return classObject;
    }

    @SuppressWarnings("unchecked")
    private <T> T createRawObject(Class<?> classImplementation) {
        try {
            return (T) classImplementation.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new BusinessException("Problem create object");
        }
    }

    private void configureObject(Object classObject) {
        for (AnnotationHandler annotationHandler : annotationHandlers) {
            annotationHandler.configure(classObject);
        }
    }
}