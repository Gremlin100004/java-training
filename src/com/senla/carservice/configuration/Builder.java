package com.senla.carservice.configuration;

import com.senla.carservice.util.PropertyLoader;

public class Builder {
    private static final Builder INSTANCE = new Builder();
    private static final String SOURCE_FOLDER = "sourceFolder";
    private static final String PACKAGE_PROJECT = "packageProject";
    private Creator creator;
    private Configurator configurator;
    private ContainerClass containerClass;

    private Builder() {
        this.configurator = new ConfiguratorImpl(PropertyLoader.getPropertyValue(PACKAGE_PROJECT), PropertyLoader.getPropertyValue(SOURCE_FOLDER));
        this.creator = new CreatorImpl();
        this.containerClass = configurator.getConfigureContainerClass();
    }

    public static Builder getInstance() {
        return INSTANCE;
    }

    public Object createObject(Class rawClass) {
        Class implementClass = rawClass;
        if (rawClass.isInterface()){
            implementClass = containerClass.getImplementClass(rawClass);
        }
        Object rawObject = creator.createRawObject(implementClass);
        Object customizedObject = configurator.configureObject(rawObject);

        return customizedObject;
    }
}
