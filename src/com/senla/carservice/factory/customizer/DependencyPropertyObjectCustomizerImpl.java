package com.senla.carservice.factory.customizer;

import com.senla.carservice.factory.annotation.Property;
import com.senla.carservice.util.PropertyLoader;

import java.lang.reflect.Field;

public class DependencyPropertyObjectCustomizerImpl implements ObjectCustomizer {
    @Override
    public <O> O configure(O inputObject) {
        Class<?> implementClass = inputObject.getClass();
        for (Field field : implementClass.getDeclaredFields()) {
            Property annotation = field.getAnnotation(Property.class);
            String value;
            if (annotation != null) {
                if (annotation.value().isEmpty()) {
                    value = PropertyLoader.getPropertyValue(inputObject.getClass().getName() + "." + field.getName());
                } else {
                    value = PropertyLoader.getPropertyValue(annotation.value());
                }
                field.setAccessible(true);
                try {
                    // так типы не определяют
                    if (field.getType().getName().contains("Boolean")) {
                        field.set(inputObject, Boolean.parseBoolean(value));
                    } else if (field.getType().getName().contains("Short")) {
                        field.set(inputObject, Short.parseShort(value));
                    } else if (field.getType().getName().contains("Integer")) {
                        field.set(inputObject, Integer.parseInt(value));
                    } else if (field.getType().getName().contains("Long")) {
                        field.set(inputObject, Long.parseLong(value));
                    } else if (field.getType().getName().contains("Double")) {
                        field.set(inputObject, Double.parseDouble(value));
                    } else if (field.getType().getName().contains("String")) {
                        field.set(inputObject, value);
                    }
                } catch (IllegalAccessException e) {
                    // так исключения не обрабатывают
                    e.printStackTrace();
                    //TODO : Add logging.
                }
            }
        }
        return inputObject;
    }
}