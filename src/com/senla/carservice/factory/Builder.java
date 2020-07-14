package com.senla.carservice.factory;

import com.senla.carservice.factory.configurator.Configurator;
import com.senla.carservice.factory.configurator.ConfiguratorImpl;
import com.senla.carservice.factory.container.ContainerClass;
import com.senla.carservice.factory.container.ContainerSingleton;
import com.senla.carservice.factory.container.ContainerSingletonImpl;
import com.senla.carservice.factory.creator.Creator;
import com.senla.carservice.factory.creator.CreatorImpl;
import com.senla.carservice.util.PropertyLoader;

public class Builder {
    private static final String PACKAGE_PROJECT = "carservice.source.package";
    private Context context;

    private final Creator creator;
    private final Configurator configurator;
    private final ContainerClass containerClass;

    public Builder() {
        this.configurator = new ConfiguratorImpl();
        this.creator = new CreatorImpl();
        this.containerClass = configurator.getConfigureContainerClass();
    }

    public <T> T createObject(Class<? extends T> rawClass) {

        Class<? extends T> implementClass = rawClass;
        T customizedObject = containerSingleton.getObjectSingleton(implementClass);
        if (customizedObject != null) {
            return customizedObject;
        }
        if (rawClass.isInterface()) {
            implementClass = containerClass.getImplementClass(rawClass);
        }
        T rawObject = creator.createRawObject(implementClass);
        customizedObject = configurator.configureObject(rawObject);
        containerSingleton.addSingleton(rawClass, customizedObject);
        return customizedObject;
    }
}