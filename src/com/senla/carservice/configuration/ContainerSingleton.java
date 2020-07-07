package com.senla.carservice.configuration;

public interface ContainerSingleton {
    Object getObjectSingleton(Class implementClass);

    void addSingleton(Class implementClass, Object singletonObject);
}
