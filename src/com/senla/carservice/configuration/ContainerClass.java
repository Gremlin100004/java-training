package com.senla.carservice.configuration;

import java.util.List;

public interface ContainerClass {
    Class getImplementClass(Class interfaceClass);

    List<Class> getConfigurableClass(Class interfaceConfigurableClass);
}
