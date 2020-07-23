package com.senla.carservice.container.configurator;

import com.senla.carservice.container.annotation.Prototype;
import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.AnnotationHandler;
import com.senla.carservice.exception.BusinessException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configurator {
    private final PackageScanner packageScanner;
    private final Map<String, Class<?>> prototypeClasses = new HashMap<>();
    private final Map<String, Class<?>> singletonClasses = new HashMap<>();
    private final List<Class<?>> annotationHandlerClasses = new ArrayList<>();

    public Configurator(String packageName) {
        packageScanner = new PackageScanner(packageName);
        initialize();
    }

    public List<Class<?>> getAnnotationHandlerClasses() {
        return annotationHandlerClasses;
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
            } else {
                for (Class<?> classInterface : classPackage.getInterfaces()) {
                    if (classInterface.equals(AnnotationHandler.class)) {
                        annotationHandlerClasses.add(classPackage);
                        break;
                    }
                }
            }
        }
    }

    private String getKeyName(Class<?> classPackage) {
        if (classPackage.getInterfaces().length == 0) {
            return classPackage.getName();
        } else {
            for (Class<?> classInterface : classPackage.getInterfaces()) {
                if (classInterface.getName().contains(packageScanner.getPackageProject())) {
                    return classInterface.getName();
                }
            }
        }
        return classPackage.getName();
    }
}