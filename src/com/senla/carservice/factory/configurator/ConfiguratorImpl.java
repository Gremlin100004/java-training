package com.senla.carservice.factory.configurator;

import com.senla.carservice.factory.container.ContainerClass;
import com.senla.carservice.factory.container.ContainerClassImpl;
import com.senla.carservice.factory.customizer.BeanPostProcessor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfiguratorImpl implements Configurator {
    private final List<BeanPostProcessor> configurationSet;
    private final ContainerClass containerClass;

    public ConfiguratorImpl(String packageProject) {
        this.containerClass = ContainerClassImpl.getInstance(new PackageScanner(packageProject));
        this.configurationSet = getConfigurationSet();
    }

    private List<BeanPostProcessor> getConfigurationSet() {
        List<Class<? extends BeanPostProcessor>> configurableClasses = containerClass.getConfigurableClass(
            BeanPostProcessor.class);
        List<BeanPostProcessor> configurableObjects = new ArrayList<>();
        configurableClasses.forEach(configurableClass -> {
            try {
                configurableObjects.add(configurableClass.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
                //TODO : Add logging.
            }
        });
        return configurableObjects;
    }

    @SuppressWarnings("unchecked")
    public <T> Class<? extends T> getImplementClass(Class<? extends T> interfaceClass) {
        if (!interfaceClass.isInterface()) {
            return interfaceClass;
        }
        return (Class<? extends T>) packageClasses.stream()
                .filter(classProject -> Arrays.asList(classProject.getInterfaces())
                        .contains(interfaceClass)).findFirst().orElse(null);
    }

    @Override
    public <O> O configureObject(O rawObject) {
        for (BeanPostProcessor configurator : configurationSet) {
            rawObject = configurator.configure(rawObject);
        }
        return rawObject;
    }

    @Override
    public ContainerClass getConfigureContainerClass() {
        return containerClass;
    }
}