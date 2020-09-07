package com.senla.carservice.container.context;

import com.senla.carservice.container.configurator.Configurator;
import com.senla.carservice.container.exception.InitializationException;
import com.senla.carservice.container.objectadjuster.AnnotationHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {

    private final Map<String, Object> singletons = new HashMap<>();
    private final List<AnnotationHandler> annotationHandlers = new ArrayList<>();
    private final Configurator configurator;
    private static final Logger LOGGER = LogManager.getLogger(Context.class);


    public Context(Configurator configurator) {
        this.configurator = configurator;
    }

    public void createAnnotationHandlers() {
        LOGGER.debug("Method createAnnotationHandlers");
        for (Class<?> classImplementation : configurator.getAnnotationHandlerClasses()) {
            AnnotationHandler annotationHandler = (AnnotationHandler) createRawObject(classImplementation);
            annotationHandlers.add(annotationHandler);
        }
    }

    public void createSingletons() {
        LOGGER.debug("Method createSingletons");
        for (Map.Entry<String, Class<?>> singletonEntry : configurator.getSingletonClasses().entrySet()) {
            singletons.put(singletonEntry.getKey(), createRawObject(singletonEntry.getValue()));
        }
    }

    public void configureSingletons() {
        LOGGER.debug("Method configureSingletons");
        for (Object classInstance : singletons.values()) {
            configureObject(classInstance);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(Class<? extends T> objectClass) {
        LOGGER.debug("Method getObject");
        LOGGER.trace("Parameter objectClass: " + objectClass);
        T classInstance = (T) singletons.get(objectClass.getName());
        if (classInstance != null) {
            return classInstance;
        }
        Class<?> classImplementation = configurator.getPrototypeClass(objectClass.getName());
        if (classImplementation == null) {
            throw new InitializationException("Error input class name");
        }
        classInstance = (T) createRawObject(classImplementation);
        configureObject(classInstance);
        return classInstance;
    }

    public <T> void setSingleton(T singleton) {
        LOGGER.debug("Method setSingleton");
        LOGGER.trace("Parameter singleton: " + singleton);
        for (Map.Entry<String, Class<?>> singletonEntry : configurator.getSingletonClasses().entrySet()) {
            if (singletonEntry.getValue().equals(singleton.getClass())) {
                singletons.put(singletonEntry.getKey(), singleton);
                break;
            }
        }
    }

    private <T> T createRawObject(Class<? extends T> classImplementation) {
        LOGGER.debug("Method createRawObject");
        LOGGER.trace("Parameter singleton: " + classImplementation);
        try {
            return classImplementation.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new InitializationException("Problem create object");
        }
    }

    private void configureObject(Object classInstance) {
        LOGGER.debug("Method configureObject");
        LOGGER.trace("Parameter classInstance: " + classInstance);
        for (AnnotationHandler annotationHandler : annotationHandlers) {
            annotationHandler.configure(classInstance, this);
        }
    }
}