package com.senla.carservice.factory.container;

import com.senla.carservice.factory.customizer.ObjectCustomizer;
import com.senla.carservice.factory.configurator.PackageScanner;

import java.util.ArrayList;
import java.util.List;

public class ContainerClassImpl implements ContainerClass {
    private static ContainerClass instance;
    private final List<Class<?>> packageClasses;

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
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
    public <T> List<Class<? extends ObjectCustomizer>> getConfigurableClass(Class<? extends T> interfaceConfigurableClass) {
        List<Class<? extends ObjectCustomizer>> configurableClasses = new ArrayList<>();
        for (Class<?> classProject : packageClasses) {
            for (Class<?> interfaceProjectClass : classProject.getInterfaces()) {
                if (interfaceProjectClass.equals(interfaceConfigurableClass)) {
                    configurableClasses.add((Class<? extends ObjectCustomizer>) classProject);
                }
            }
        }
        return configurableClasses;
    }
}