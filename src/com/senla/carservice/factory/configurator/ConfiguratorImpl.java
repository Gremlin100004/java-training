package com.senla.carservice.factory.configurator;

import com.senla.carservice.factory.container.ContainerClass;
import com.senla.carservice.factory.container.ContainerClassImpl;
import com.senla.carservice.factory.customizer.ObjectCustomizer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ConfiguratorImpl implements Configurator {
    private final List<ObjectCustomizer> configurationSet;
    private final ContainerClass containerClass;

    public ConfiguratorImpl(String packageProject, String sourceFolder) {
        this.containerClass = ContainerClassImpl.getInstance(new PackageScanner(packageProject, sourceFolder));
        this.configurationSet = getConfigurationSet();
    }

    private List<ObjectCustomizer> getConfigurationSet() {
        List<Class<? extends ObjectCustomizer>> configurableClasses = containerClass.getConfigurableClass(
            ObjectCustomizer.class);
        List<ObjectCustomizer> configurableObjects = new ArrayList<>();
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

    @Override
    public <O> O configureObject(O rawObject) {
        for (ObjectCustomizer configurator : configurationSet) {
            rawObject = configurator.configure(rawObject);
        }
        return rawObject;
    }

    @Override
    public ContainerClass getConfigureContainerClass() {
        return containerClass;
    }
}