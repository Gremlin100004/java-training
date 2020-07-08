package com.senla.carservice.configuration;

public interface Configurator {

    ContainerClass getConfigureContainerClass();

    <O> O configureObject(O rawObject);
}