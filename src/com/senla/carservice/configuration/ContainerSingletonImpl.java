package com.senla.carservice.configuration;

import com.senla.carservice.annotation.Prototype;

import java.util.HashMap;
import java.util.Map;

public class ContainerSingletonImpl implements ContainerSingleton {
    private static ContainerSingleton instance;
    private final Map<Class<?>, Object> cacheSingleton = new HashMap<>();

    private ContainerSingletonImpl() {
    }

    public static ContainerSingleton getInstance() {
        if (instance == null) {
            instance = new ContainerSingletonImpl();
        }
        return instance;
    }

    @Override
    public <T> void addSingleton(Class<? extends T> implementClass, T singletonObject) {
        if (implementClass.isAnnotationPresent(Prototype.class)) {
            return;
        }
        cacheSingleton.put(implementClass, singletonObject);
    }

    @Override
    public <T> T getObjectSingleton(Class<? extends T> implementClass) {
        if (cacheSingleton.containsKey(implementClass)) {
            return (T) cacheSingleton.get(implementClass);
        }
        return null;
    }
}