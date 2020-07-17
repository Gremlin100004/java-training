package com.senla.carservice.container.contex;

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
    }

    public void setDependencyInjection(DependencyInjectionAnnotationHandler dependencyInjection) {
        this.dependencyInjection = dependencyInjection;
    }

    public void setPropertyDependency(PropertyDependencyAnnotationHandler propertyDependency) {
        this.propertyDependency = propertyDependency;
    }

    // а зачем void методу дженерик тип?
    public <T> void createSingletons() {
        for (Class<?> classSingleton : configurator.getSingletonClasses()) {
            T rawObject = createRawObject(classSingleton);
            if (classSingleton.getInterfaces().length == 0) {
                singletons.put(classSingleton.getName(), rawObject);
            } else {
                // работает только с первым интерфейсом? не очень удобно
                singletons.put(classSingleton.getInterfaces()[0].getName(), rawObject);
            }
        }
    }

    public void configureSingletons() {
        singletons.values().forEach(this::configureObject);
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(String stringNameClass) {
        T customizedObject = (T) singletons.get(stringNameClass);
        if (customizedObject != null) {
            return customizedObject;
        }
        Class<?> classImplementation = configurator.getPrototypeClasses(stringNameClass);
        if (classImplementation == null) {
            throw new BusinessException("Error input class name");
        }
        return configureObject(createRawObject(classImplementation));
    }

    @SuppressWarnings("unchecked")
    private <T> T createRawObject(Class<?> classImplementation) {
        try {
            return (T) classImplementation.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new BusinessException("Problem create object");
        }
    }

    // лучше пусть все три метода будут воид: configureObject, configure, configure
    // иначе жуткая путаница с хранением объектов, создается впечатление, что возвращается
    // какой-то другой объект
    private <T> T configureObject(T rawObject) {
        T configureObject = propertyDependency.configure(rawObject);
        configureObject = dependencyInjection.configure(configureObject);
        return configureObject;
    }
}