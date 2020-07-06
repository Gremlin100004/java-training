package com.senla.carservice.configuration;

public interface Configurator {

    ContainerClass getConfigureContainerClass();

    Object configureObject(Object rawObject);
}
