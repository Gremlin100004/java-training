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
                    field.getAnnotatedType().getType();
                    field.set(inputObject, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    //TODO : Add logging.
                }
            }
        }
        return inputObject;
    }
}