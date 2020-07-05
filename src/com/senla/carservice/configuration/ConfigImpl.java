package com.senla.carservice.configuration;

import java.util.List;
import java.util.Map;

public class ConfigImpl implements Config {
    private PackageScanner packageScanner;
    private Map<Class, Class> interfaceImplementation;

    private ConfigImpl(Map<Class, Class> interfaceImplementation, String packageProject) {
        this.packageScanner = new PackageScanner(packageProject);
        this.interfaceImplementation = interfaceImplementation;
    }

    @Override
    public <T> Class<? extends T> getImplementClass(Class<T> classInterface) {
        return interfaceImplementation.computeIfAbsent(classInterface, aClass -> {
            List<Class<? extends T>> classes = PackageScanner.getImplementedClass(Class <T> classInterface);
            if (classes.size() != 1){
                throw new RuntimeException(classInterface + "has more than 2 implementations!");
            }
            return classes.iterator().next();
        });
    }
}
