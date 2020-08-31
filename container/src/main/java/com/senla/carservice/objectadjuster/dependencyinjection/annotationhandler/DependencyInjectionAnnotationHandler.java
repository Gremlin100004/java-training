package com.senla.carservice.objectadjuster.dependencyinjection.annotationhandler;

import com.senla.carservice.context.Context;
import com.senla.carservice.exception.InitializationException;
import com.senla.carservice.objectadjuster.AnnotationHandler;
import com.senla.carservice.objectadjuster.dependencyinjection.annotation.Dependency;
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
        LOGGER.debug("Parameter classInstance: {}", classInstance.toString());
        LOGGER.debug("Parameter context: {}", context.toString());
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