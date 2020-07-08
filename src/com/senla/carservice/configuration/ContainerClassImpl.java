package com.senla.carservice.configuration;

import java.util.ArrayList;
import java.util.List;

public class ContainerClassImpl<T> implements ContainerClass {
    private static ContainerClass instance;
    private final List<Class<? extends T>> packageClasses;

    private ContainerClassImpl(PackageScanner packageScanner) {
        packageClasses = packageScanner.getPackageClass();
    }

    public static ContainerClass getInstance(PackageScanner packageScanner) {
        if (instance == null) {
            instance = new ContainerClassImpl(packageScanner);
        }
        return instance;
    }

    @Override
    public <T> Class<? extends T> getImplementClass(Class<? extends T> interfaceClass) {
        if (!interfaceClass.isInterface()) {
            return interfaceClass;
        }
        for (Class<?> classProject : packageClasses) {
            for (Class<?> interfaceProjectClass : classProject.getInterfaces()) {
                if (interfaceProjectClass.equals(interfaceClass)) {
                    return (Class<? extends T>) classProject;
                }
            }
        }
        return null;
    }

    @Override
    public <T> List<Class<? extends ConfigurableObject>> getConfigurableClass(Class<? extends T> interfaceConfigurableClass) {
        List<Class<? extends ConfigurableObject>> configurableClasses = new ArrayList<>();
        for (Class<?> classProject : packageClasses) {
            for (Class<?> interfaceProjectClass : classProject.getInterfaces()) {
                if (interfaceProjectClass.equals(interfaceConfigurableClass)) {
                    configurableClasses.add((Class<? extends ConfigurableObject>) classProject);
                }
            }
        }
        return configurableClasses;
    }
}