package com.senla.carservice.factory.customizer;

import com.senla.carservice.factory.Builder;
import com.senla.carservice.factory.Context;
import com.senla.carservice.factory.annotation.Dependency;

import java.util.Arrays;

public class DependencyInjectionBeanPostProcessorImpl implements BeanPostProcessor {
    @Override
    public <O> O configure(O inputObject, Context context) {
        Arrays.stream(inputObject.getClass().getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(Dependency.class)).forEach(field -> {
                field.setAccessible(true);
                Object object = context.getObjectSingleton(field.getType());
                try {
                    field.set(inputObject, object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    //TODO : Add logging.
                }
            });
        return inputObject;
    }
}