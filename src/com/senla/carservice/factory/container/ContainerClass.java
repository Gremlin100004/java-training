package com.senla.carservice.factory.container;

import com.senla.carservice.factory.customizer.ObjectCustomizer;

import java.util.List;

public interface ContainerClass {
    <T> Class<? extends T> getImplementClass(Class<? extends T> interfaceClass);

    <T> List<Class<? extends ObjectCustomizer>> getConfigurableClass(Class<? extends T> interfaceConfigurableClass);
}