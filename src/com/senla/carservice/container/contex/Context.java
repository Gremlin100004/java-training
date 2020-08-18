package com.senla.carservice.container.contex;

import com.senla.carservice.container.configurator.Configurator;
import com.senla.carservice.container.objectadjuster.AnnotationHandler;
import com.senla.carservice.exception.BusinessException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {
    private final Map<String, Object> singletons = new HashMap<>();
    private final List<AnnotationHandler> annotationHandlers = new ArrayList<>();
    private final Configurator configurator;

    public Context(Configurator configurator) {
        this.configurator = configurator;
    }

    public void createAnnotationHandlers() {
        for (Class<?> classImplementation : configurator.getAnnotationHandlerClasses()) {
            AnnotationHandler annotationHandler = (AnnotationHandler) createRawObject(classImplementation);
            annotationHandlers.add(annotationHandler);
        }
    }

    public void createSingletons() {
        for (Map.Entry<String, Class<?>> singletonEntry : configurator.getSingletonClasses().entrySet()) {
            singletons.put(singletonEntry.getKey(), createRawObject(singletonEntry.getValue()));
        }
    }

    public void configureSingletons() {
        for (Object classInstance : singletons.values()) {
            configureObject(classInstance);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(Class<? extends T> objectClass) {
        T classInstance = (T) singletons.get(objectClass.getName());
        if (classInstance != null) {
            return classInstance;
        }
        Class<?> classImplementation = configurator.getPrototypeClass(objectClass.getName());
        if (classImplementation == null) {
            throw new BusinessException("Error input class name");
        }
        classInstance = (T) createRawObject(classImplementation);
        configureObject(classInstance);
        return classInstance;
    }

    public <T> void setSingleton(T singleton) {
        for (Map.Entry<String, Class<?>> singletonEntry : configurator.getSingletonClasses().entrySet()) {
            if (singletonEntry.getValue().equals(singleton.getClass())){
                singletons.put(singletonEntry.getKey(), singleton);
                break;
            }
        }
    }

    private <T> T createRawObject(Class<? extends T> classImplementation) {
        try {
            return classImplementation.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new BusinessException("Problem create object");
        }
    }

    private void configureObject(Object classInstance) {
        for (AnnotationHandler annotationHandler : annotationHandlers) {
            annotationHandler.configure(classInstance, this);
        }
    }
}