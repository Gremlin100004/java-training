package com.senla.carservice.container.objectadjuster.dependencyinjection.annotationhandler;

import com.senla.carservice.container.context.Context;
import com.senla.carservice.container.exception.InitializationException;
import com.senla.carservice.container.objectadjuster.AnnotationHandler;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class DependencyInjectionAnnotationHandler implements AnnotationHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DependencyInjectionAnnotationHandler.class);

    public DependencyInjectionAnnotationHandler() {
    }

    @Override
    public void configure(Object classInstance, Context context) {
        LOGGER.debug("Method configure");
        LOGGER.trace("Parameter classInstance: {}", classInstance);
        LOGGER.trace("Parameter context: {}", context);
        for (Field field : classInstance.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Dependency.class)) {
                field.setAccessible(true);
                try {
                    field.set(classInstance, context.getObject(field.getType()));
                } catch (IllegalAccessException e) {
                    LOGGER.error("Error set value to a field", e);
                    throw new InitializationException("Error set value to a field");
                }
            }
        }
    }
}