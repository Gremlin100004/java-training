package com.senla.carservice.factory;

import com.senla.carservice.factory.configurator.Configurator;
import com.senla.carservice.factory.container.ContainerClass;
import com.senla.carservice.factory.creator.Creator;
import com.senla.carservice.factory.creator.CreatorImpl;

import java.util.List;

public class Builder {
    private Context context;

    private final Creator creator;
    private final Configurator configurator;
    private final ContainerClass containerClass;

    public Builder(List<Object> ) {
        this.configurator = new Configurator();
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