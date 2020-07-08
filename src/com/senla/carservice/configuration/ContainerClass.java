package com.senla.carservice.configuration;

import java.util.List;

public interface ContainerClass {
    <T> Class<? extends T> getImplementClass(Class<? extends T> interfaceClass);

    <T> List<Class<? extends ConfigurableObject>> getConfigurableClass(Class<? extends T> interfaceConfigurableClass);
}