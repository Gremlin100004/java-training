package com.senla.carservice.factory.configurator;

import com.senla.carservice.factory.container.ContainerClass;

public interface Configurator {

    ContainerClass getConfigureContainerClass();

    <O> O configureObject(O rawObject);
}