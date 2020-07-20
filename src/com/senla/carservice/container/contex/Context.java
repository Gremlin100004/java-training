package com.senla.carservice.container.contex;

import com.senla.carservice.container.configurator.Configurator;
import com.senla.carservice.container.dependencyinjection.annotationhandler.DependencyInjectionAnnotationHandler;
import com.senla.carservice.container.propertyinjection.annotationhandler.PropertyDependencyAnnotationHandler;
import com.senla.carservice.exception.BusinessException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Context {
    private final Map<String, Object> singletons = new HashMap<>();
    private DependencyInjectionAnnotationHandler dependencyInjection;
    private PropertyDependencyAnnotationHandler propertyDependency;
    private final Configurator configurator;

    public Context(final Configurator configurator) {
        this.configurator = configurator;
    }

    public void setDependencyInjection(DependencyInjectionAnnotationHandler dependencyInjection) {
        this.dependencyInjection = dependencyInjection;
    }

    public void setPropertyDependency(PropertyDependencyAnnotationHandler propertyDependency) {
        this.propertyDependency = propertyDependency;
    }

    public void createSingletons() {
        for (Map.Entry<String, Class<?>> singletonEntry : configurator.getSingletonClasses().entrySet()) {
            singletons.put(singletonEntry.getKey(), createRawObject(singletonEntry.getValue()));
        }
    }

    public void configureSingletons() {
        for (Object classObject : singletons.values()) {
            configureObject(classObject);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(Class<T> objectClass) {
        T classObject = (T) singletons.get(objectClass.getName());
        if (classObject != null) {
            return classObject;
        }
        Class<?> classImplementation = configurator.getPrototypeClass(objectClass.getName());
        if (classImplementation == null) {
            throw new BusinessException("Error input class name");
        }
        classObject = createRawObject(classImplementation);
        configureObject(classObject);
        return classObject;
    }

    @SuppressWarnings("unchecked")
    private <T> T createRawObject(Class<?> classImplementation) {
        try {
            return (T) classImplementation.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new BusinessException("Problem create object");
        }
    }

    private void configureObject(Object classObject) {
        propertyDependency.configure(classObject);
        dependencyInjection.configure(classObject);
    }
}