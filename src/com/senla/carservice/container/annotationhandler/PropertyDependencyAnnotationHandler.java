package com.senla.carservice.container.annotationhandler;

import com.senla.carservice.container.annotation.Property;
import com.senla.carservice.enumeration.DefaultValue;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.util.PropertyLoader;

import java.lang.reflect.Field;

public class PropertyDependencyAnnotationHandler {

    public <T> T configure(T inputObject) {
        Class<?> implementClass = inputObject.getClass();
        String propertyFileName;
        String value;
        String propertyName;
        String fieldType;
        Property annotation;
        for (Field field : implementClass.getDeclaredFields()) {
            annotation = field.getAnnotation(Property.class);
            if (annotation == null) {
                continue;
            }
            System.out.println(field.getType().equals(Boolean.class));
            propertyFileName = getPropertyFileName(annotation);
            propertyName = getPropertyName(annotation, inputObject.getClass().getName() + "." + field.getName());
            fieldType = getTypeField(annotation, field.getType().getName().substring(field.getType().getName().lastIndexOf('.') + 1));
            value = PropertyLoader.getPropertyValue(propertyFileName, propertyName);
            field.setAccessible(true);

            injectValueInField(field, value, fieldType, inputObject);
        }
        return inputObject;
    }

    private String getPropertyFileName(Property annotation) {
        if (annotation.configName().isEmpty()) {
            return DefaultValue.PROPERTY_FILE_NAME.toString();
        } else {
            return annotation.propertyName();
        }
    }

    private String getPropertyName(Property annotation, String propertyName) {
        if (annotation.propertyName().isEmpty()) {
            return propertyName;
        } else {
            return annotation.propertyName();
        }
    }

    private String getTypeField(Property annotation, String typeInString) {
        if (annotation.type().isEmpty()) {
            return typeInString;
        } else {
            return annotation.type();
        }
    }

    private <T> void injectValueInField(Field field, String value, String fieldType, T inputObject) {
        try {
            if (fieldType.equals("Boolean")) {
                field.set(inputObject, Boolean.parseBoolean(value));
            } else if (fieldType.equals("Short")) {
                field.set(inputObject, Short.parseShort(value));
            } else if (fieldType.equals("Integer")) {
                field.set(inputObject, Integer.parseInt(value));
            } else if (fieldType.equals("Long")) {
                field.set(inputObject, Long.parseLong(value));
            } else if (fieldType.equals("Double")) {
                field.set(inputObject, Double.parseDouble(value));
            } else if (fieldType.equals("String")) {
                field.set(inputObject, value);
            }
        } catch (IllegalAccessException e) {
            throw new BusinessException("Error set value to a field");
        }
    }
}