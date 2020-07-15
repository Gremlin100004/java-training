package com.senla.carservice.container.configurator;

import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.container.annotation.Prototype;
import com.senla.carservice.container.annotation.Singleton;

import java.util.*;

public class Configurator {
    private final PackageScanner packageScanner;
    private final Map<String, Class<?>> prototypeClasses = new HashMap<>();
    private final List<Class<?>> singletonClasses = new ArrayList<>();

    public Configurator(String packageName) {
        packageScanner = new PackageScanner(packageName);
        initialize();
    }

    public Class<?> getPrototypeClasses(String className) {
        return prototypeClasses.get(className);
    }

    public List<Class<?>> getSingletonClasses() {
        return singletonClasses;
    }

    private void initialize(){
        List<Class<?>> classesPackage = packageScanner.getArrayClasses();
        if (classesPackage.isEmpty()){
            throw new BusinessException("there are no classes");
        }
        for (Class<?> classPackage: packageScanner.getArrayClasses()){
            if (Arrays.asList(classPackage.getInterfaces()).size() > 1){
                throw new BusinessException("class has more then one interface");
            }
            if (classPackage.isAnnotationPresent(Singleton.class)){
                singletonClasses.add(classPackage);
            }
            else if (classPackage.isAnnotationPresent(Prototype.class)){
                prototypeClasses.put(classPackage.getName(), classPackage);
            }
        }
    }
}