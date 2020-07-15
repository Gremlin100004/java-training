package com.senla.carservice.container;

import com.senla.carservice.container.annotationhandler.DependencyInjectionAnnotationHandler;
import com.senla.carservice.container.annotationhandler.PropertyDependencyAnnotationHandler;
import com.senla.carservice.container.configurator.Configurator;
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
        createAllSingletons();
    }

    public void setDependencyInjection(DependencyInjectionAnnotationHandler dependencyInjection) {
        this.dependencyInjection = dependencyInjection;
    }

    public void setPropertyDependency(PropertyDependencyAnnotationHandler propertyDependency) {
        this.propertyDependency = propertyDependency;
    }

    private void createAllSingletons() {
        for (Class<?> classSingleton : configurator.getSingletonClasses()) {
            if (classSingleton.getInterfaces().length == 0){
                singletons.put(classSingleton.getName(), createRawObject(classSingleton));
            } else {
                singletons.put(classSingleton.getInterfaces()[0].getName(), createRawObject(classSingleton));
            }
        }
    }

    public Object getObject(String stringNameClass) {
        Object rawObject = singletons.get(stringNameClass);
        if (rawObject == null) {
            Class<?> classImplementation = configurator.getPrototypeClasses(stringNameClass);
            if (classImplementation == null) {
                throw new BusinessException("Error input class name");
            }
            rawObject = createRawObject(classImplementation);
        }
        return configureObject(rawObject);
    }

    private Object createRawObject(Class<?> classImplementation) {
        try {
            return classImplementation.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new BusinessException("Problem create object");
        }
    }

    private Object configureObject(Object rawObject) {
        Object configureObject = propertyDependency.configure(rawObject);
        configureObject = dependencyInjection.configure(configureObject);
        return configureObject;
    }
}