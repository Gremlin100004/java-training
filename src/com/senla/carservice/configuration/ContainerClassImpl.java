package com.senla.carservice.configuration;

import java.util.ArrayList;
import java.util.List;

public class ContainerClassImpl implements ContainerClass {
    private static ContainerClass instance;
    private List<Class> packageClasses;

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
    public Class getImplementClass(Class interfaceClass) {
        if (!interfaceClass.isInterface()) {
            return interfaceClass;
        }
        for (Class classProject : packageClasses) {
            for (Class interfaceProjectClass : classProject.getInterfaces()) {
                if (interfaceProjectClass.equals(interfaceClass)) {
                    return classProject;
                }
            }
        }
        return null;
    }

    @Override
    public List<Class> getConfigurableClass(Class interfaceConfigurableClass) {
        List<Class> configurableClasses = new ArrayList<>();
        for (Class classProject : packageClasses) {
            for (Class interfaceProjectClass : classProject.getInterfaces()) {
                if (interfaceProjectClass.equals(interfaceConfigurableClass)) {
                    configurableClasses.add(classProject);
                }
            }
        }
        return configurableClasses;
    }
}
