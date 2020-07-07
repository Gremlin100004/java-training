package com.senla.carservice.configuration;

import com.senla.carservice.annotation.InjectDependency;

import java.lang.reflect.Field;

public class InjectDependencyConfigurableObjectImpl implements ConfigurableObject {
    @Override
    public Object configure(Object inputObject) {
        for (Field field : inputObject.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectDependency.class)) {
                field.setAccessible(true);
                Object object = Builder.getInstance().createObject(field.getType());
                try {
                    field.set(inputObject, object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    //TODO : Add logging.
                }
            }
        }
        return inputObject;
    }
}
