package com.senla.carservice.configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ConfiguratorImpl implements Configurator {
    private final List<ConfigurableObject> configurationSet;
    private final ContainerClass containerClass;

    public ConfiguratorImpl(String packageProject, String sourceFolder) {
        this.containerClass = ContainerClassImpl.getInstance(new PackageScanner(packageProject, sourceFolder));
        this.configurationSet = getConfigurationSet();
    }

    private List<ConfigurableObject> getConfigurationSet(){
        List<Class<? extends ConfigurableObject>> configurableClasses= containerClass.getConfigurableClass(ConfigurableObject.class);
        List<ConfigurableObject> configurableObjects = new ArrayList<>();
        for (Class<?> configurableClass : configurableClasses){
            try {
                configurableObjects.add((ConfigurableObject) configurableClass.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
                //TODO : Add logging.
            }
        }
        return configurableObjects;
    }

    @Override
    public <O> O configureObject(O rawObject) {
        for (ConfigurableObject configurator: configurationSet){
            rawObject = configurator.configure(rawObject);
        }
        return rawObject;
    }

    @Override
    public ContainerClass getConfigureContainerClass() {
        return containerClass;
    }
}