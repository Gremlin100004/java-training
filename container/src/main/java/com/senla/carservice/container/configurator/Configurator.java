package com.senla.carservice.container.configurator;

import com.senla.carservice.container.annotation.Prototype;
import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.exception.InitializationException;
import com.senla.carservice.container.objectadjuster.AnnotationHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configurator {

    private final PackageScanner packageScanner;
    private final Map<String, Class<?>> prototypeClasses = new HashMap<>();
    private final Map<String, Class<?>> singletonClasses = new HashMap<>();
    private final List<Class<?>> annotationHandlerClasses = new ArrayList<>();
    private static final Logger LOGGER = LogManager.getLogger(Configurator.class);

    public Configurator(String packageName) {
        LOGGER.trace("Class get parameter packageName: " + packageName);
        packageScanner = new PackageScanner(packageName);
        initialize();
    }

    public List<Class<?>> getAnnotationHandlerClasses() {
        LOGGER.debug("Method getAnnotationHandlerClasses");
        return annotationHandlerClasses;
    }

    public Class<?> getPrototypeClass(String className) {
        LOGGER.debug("Method getPrototypeClass");
        LOGGER.trace("Parameter className: " + className);
        return prototypeClasses.get(className);
    }

    public Map<String, Class<?>> getSingletonClasses() {
        LOGGER.debug("Method     public Map<String, Class<?>> getSingletonClasses() {\n");
        return singletonClasses;
    }

    private void initialize() {
        LOGGER.debug("Method getPropertyValue");
        List<Class<?>> classesOfPackage = packageScanner.getClasses();
        if (classesOfPackage.isEmpty()) {
            throw new InitializationException("there are no classes");
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
        LOGGER.debug("Method getKeyName");
        LOGGER.trace("Parameter classPackage: " + classPackage);
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