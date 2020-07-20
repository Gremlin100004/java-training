package com.senla.carservice.container.configurator;

import com.senla.carservice.container.annotation.Prototype;
import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.exception.BusinessException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configurator {
    private final PackageScanner packageScanner;
    private final Map<String, Class<?>> prototypeClasses = new HashMap<>();
    private final Map<String, Class<?>> singletonClasses = new HashMap<>();
    private static final String CLASS_IMPLEMENTATION_ATTRIBUTE = "Impl";
    private static final String EMPTY_LITERAL = "";

    public Configurator(String packageName) {
        packageScanner = new PackageScanner(packageName);
        initialize();
    }

    public Class<?> getPrototypeClass(String className) {
        return prototypeClasses.get(className);
    }

    public Map<String, Class<?>> getSingletonClasses() {
        return singletonClasses;
    }

    private void initialize() {
        List<Class<?>> classesOfPackage = packageScanner.getArrayClasses();
        if (classesOfPackage.isEmpty()) {
            throw new BusinessException("there are no classes");
        }
        for (Class<?> classPackage : classesOfPackage) {
            if (classPackage.isAnnotationPresent(Singleton.class)) {
                singletonClasses.put(getKeyName(classPackage), classPackage);
            } else if (classPackage.isAnnotationPresent(Prototype.class)) {
                prototypeClasses.put(getKeyName(classPackage), classPackage);
            }
        }
    }

    private String getKeyName(Class<?> classPackage) {
        if (classPackage.getSimpleName().contains(CLASS_IMPLEMENTATION_ATTRIBUTE)) {
            for (Class<?> classInterface : classPackage.getInterfaces()) {
                if (classPackage.getSimpleName().replace(CLASS_IMPLEMENTATION_ATTRIBUTE, EMPTY_LITERAL)
                    .equals(classInterface.getSimpleName())) {
                    return classInterface.getName();
                }
            }
            throw new BusinessException("Error use interface with implemented class");
        } else {
            return classPackage.getName();
        }
    }
}