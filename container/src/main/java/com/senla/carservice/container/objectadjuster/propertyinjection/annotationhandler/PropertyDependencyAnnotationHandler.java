package com.senla.carservice.container.objectadjuster.propertyinjection.annotationhandler;

import com.senla.carservice.container.context.Context;
import com.senla.carservice.container.objectadjuster.AnnotationHandler;
import com.senla.carservice.container.objectadjuster.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.container.objectadjuster.propertyinjection.enumeration.DefaultValue;
import com.senla.carservice.container.objectadjuster.propertyinjection.enumeration.TypeField;
import com.senla.carservice.container.property.PropertyLoader;
import com.senla.carservice.container.exception.InitializationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;

public class PropertyDependencyAnnotationHandler implements AnnotationHandler {

    private static final String CLASS_NAME_SEPARATOR = ".";
    private static final Logger LOGGER = LogManager.getLogger(PropertyDependencyAnnotationHandler.class);

    @Override
    public void configure(Object classInstance, Context context) {
        LOGGER.debug("Method configure");
        LOGGER.trace("Parameter classInstance: " + classInstance);
        LOGGER.trace("Parameter context: " + context);
        Class<?> implementClass = classInstance.getClass();
        for (Field field : implementClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(ConfigProperty.class)) {
                continue;
            }
            ConfigProperty annotation = field.getAnnotation(ConfigProperty.class);
            String propertyFileName = getPropertyFileName(annotation);
            String propertyName = getPropertyName(annotation,
                                                  classInstance.getClass().getName() + CLASS_NAME_SEPARATOR +
                                                  field.getName());
            Class<?> fieldType = getFieldType(annotation, field.getType());
            String value = PropertyLoader.getPropertyValue(propertyFileName, propertyName);
            injectValueInField(field, value, fieldType, classInstance);
        }
    }

    private String getPropertyFileName(ConfigProperty annotation) {
        LOGGER.debug("Method getPropertyFileName");
        LOGGER.trace("Parameter annotation: " + annotation);
        if (annotation.configName().isEmpty()) {
            return DefaultValue.PROPERTY_FILE_NAME.getValue();
        } else {
            return annotation.configName();
        }
    }

    private String getPropertyName(ConfigProperty annotation, String defaultPropertyName) {
        LOGGER.debug("Method getPropertyName");
        LOGGER.trace("Parameter annotation: " + annotation);
        LOGGER.trace("Parameter defaultPropertyName: " + defaultPropertyName);
        if (annotation.propertyName().isEmpty()) {
            return defaultPropertyName;
        } else {
            return annotation.propertyName();
        }
    }

    private Class<?> getFieldType(ConfigProperty annotation, Class<?> defaultType) {
        LOGGER.debug("Method getFieldType");
        LOGGER.trace("Parameter annotation: " + annotation);
        LOGGER.trace("Parameter defaultType: " + defaultType);
        if (annotation.type() == TypeField.DEFAULT) {
            return defaultType;
        } else {
            return annotation.type().getReferenceDataTypeClass();
        }
    }

    private void injectValueInField(Field field, String value, Class<?> fieldType, Object inputObject) {
        LOGGER.debug("Method injectValueInField");
        LOGGER.trace("Parameter field: " + field);
        LOGGER.trace("Parameter inputObject: " + inputObject);
        field.setAccessible(true);
        try {
            if (fieldType.equals(Boolean.class)) {
                field.set(inputObject, Boolean.parseBoolean(value));
            } else if (fieldType.equals(Short.class)) {
                field.set(inputObject, Short.parseShort(value));
            } else if (fieldType.equals(Integer.class)) {
                field.set(inputObject, Integer.parseInt(value));
            } else if (fieldType.equals(Long.class)) {
                field.set(inputObject, Long.parseLong(value));
            } else if (fieldType.equals(Double.class)) {
                field.set(inputObject, Double.parseDouble(value));
            } else if (fieldType.equals(String.class)) {
                field.set(inputObject, value);
            }
        } catch (IllegalAccessException e) {
            LOGGER.error("Error set value to a field", e);
            throw new InitializationException("Error set value to a field");
        } catch (NumberFormatException e) {
            LOGGER.error("Error parse string value", e);
            throw new InitializationException("Error parse string value");
        }
    }
}