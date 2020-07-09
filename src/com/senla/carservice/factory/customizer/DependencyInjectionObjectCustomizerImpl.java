package com.senla.carservice.factory.customizer;

import com.senla.carservice.factory.Builder;
import com.senla.carservice.factory.annotation.Dependency;

import java.lang.reflect.Field;

public class DependencyInjectionObjectCustomizerImpl implements ObjectCustomizer {
    @Override
    public <O> O configure(O inputObject) {
        for (Field field : inputObject.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Dependency.class)) {
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