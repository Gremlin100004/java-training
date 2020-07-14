package com.senla.carservice.factory;

import com.senla.carservice.factory.annotation.Prototype;
import com.senla.carservice.factory.configurator.Configurator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Context {
    private Builder builder;
    private final Map<Class<?>, Object> cacheSingleton = new HashMap<>();
    private Configurator configurator;

    public Context(final Configurator configurator) {
        this.configurator = configurator;
    }


    public <T> T getObjectSingleton(Class<? extends T> implementClass) {
        if (cacheSingleton.containsKey(implementClass)) {
            return (T) cacheSingleton.get(implementClass);
        }
        return null;
    }

    public <T> void addSingleton(Class<? extends T> implementClass, T singletonObject) {
        if (implementClass.isAnnotationPresent(Prototype.class)) {
            return;
        }
        cacheSingleton.put(implementClass, singletonObject);
    }

    public Configurator getConfigurator() {
        return configurator;
    }
}
