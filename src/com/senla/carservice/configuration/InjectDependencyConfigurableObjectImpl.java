package com.senla.carservice.configuration;

import com.senla.carservice.annotation.InjectObject;

import java.lang.reflect.Field;

public class InjectDependencyConfigurableObjectImpl implements ConfigurableObject {
    @Override
    public void configure(final Object o) {
        for (Field field : o.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectObject.class)) {
                field.setAccessible(true);
                Object object = Builder.getInstance().createObject(field.getType());
                try {
                    field.set(o, object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
