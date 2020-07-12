package com.senla.carservice.factory.container;

import com.senla.carservice.factory.configurator.PackageScanner;
import com.senla.carservice.factory.customizer.ObjectCustomizer;

import java.util.ArrayList;
import java.util.Arrays;
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
    // как ты переводишь фразу getImplementClass? я перевожу как "получить реализовать класс"
    public <T> Class<? extends T> getImplementClass(Class<? extends T> interfaceClass) {
        if (!interfaceClass.isInterface()) {
            return interfaceClass;
        }
        // вот тут у тебя будет NPE, так как packageClasses может быть нал из метода getPackageClass()
        // используй нормальное форматирование стримов, писал уже об этом, читать невозможно (я отформатирую)
        return (Class<? extends T>) packageClasses.stream()
                .filter(classProject -> Arrays.asList(classProject.getInterfaces()).contains(interfaceClass))
                .findFirst()
                .orElse(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<Class<? extends ObjectCustomizer>> getConfigurableClass(Class<? extends T> interfaceConfigurableClass) {
        List<Class<? extends ObjectCustomizer>> configurableClasses = new ArrayList<>();
        packageClasses.forEach(classProject -> Arrays.stream(classProject.getInterfaces())
                .filter(interfaceProjectClass -> interfaceProjectClass.equals(interfaceConfigurableClass))
                .map(interfaceProjectClass -> (Class<? extends ObjectCustomizer>) classProject)
                .forEach(configurableClasses::add));
        return configurableClasses;
    }
}