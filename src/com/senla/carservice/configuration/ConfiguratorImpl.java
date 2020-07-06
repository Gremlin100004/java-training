package com.senla.carservice.configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ConfiguratorImpl implements Configurator {
    private List<ConfigurableObject> configurationSet;
    private ContainerClass containerClass;

    public ConfiguratorImpl(String packageProject, String sourceFolder) {
        this.containerClass = ContainerClassImpl.getInstance(new PackageScanner(packageProject, sourceFolder));
        this.configurationSet = getConfigurationSet();
    }

    @Override
    public ContainerClass getConfigureContainerClass() {
        return containerClass;
    }

    private List<ConfigurableObject> getConfigurationSet(){
        List<Class> configurableClasses= containerClass.getConfigurableClass(ConfigurableObject.class);
        List<ConfigurableObject> configurableObjects = new ArrayList<>();
        for (Class configurableClass : configurableClasses){
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
    public Object configureObject(Object rawObject) {
        for (ConfigurableObject configurator: configurationSet){
            rawObject = configurator.configure(rawObject);
        }
        return rawObject;
    }
}
