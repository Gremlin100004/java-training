package com.senla.carservice.configuration;

import com.senla.carservice.annotation.InjectProperty;
import com.senla.carservice.util.PropertyLoader;

import java.lang.reflect.Field;

public class InjectPropertyConfigurableObjectImpl implements ConfigurableObject {
    @Override
    public void configure(final Object o) {
        Class<?> implementClass = o.getClass();
        for (Field field : implementClass.getDeclaredFields()) {
            InjectProperty annotation = field.getAnnotation(InjectProperty.class);
            String value;
            if (annotation != null) {
                if (annotation.value().isEmpty()) {
                    value = PropertyLoader
                        .getPropertyValue(String.format("%s.%s", o.getClass().getName(), field.getName()));
                } else {
                    value = PropertyLoader.getPropertyValue(annotation.value());
                }
                field.setAccessible(true);
                try {
                    field.getAnnotatedType().getType();
                    field.set(o, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
