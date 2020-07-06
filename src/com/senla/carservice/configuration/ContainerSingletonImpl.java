package com.senla.carservice.configuration;

import java.util.HashMap;
import java.util.Map;

public class ContainerSingletonImpl implements ContainerSingleton {
    private static ContainerSingleton instance;
    private Map<Class, Object> cacheSingleton = new HashMap<>();

    private ContainerSingletonImpl() {
    }

    public static ContainerSingleton getInstance(PackageScanner packageScanner) {
        if (instance == null) {
            instance = new ContainerSingletonImpl();
        }
        return instance;
    }

    @Override
    public Object getObjectSingleton(Class implementClass) {
        if (cacheSingleton.containsKey(implementClass)) {
            return cacheSingleton.get(implementClass);
        }
        return null;
    }

    @Override
    public void addSingleton(Class implementClass, Object singletonObject) {
        cacheSingleton.put(implementClass, singletonObject);
    }
}
