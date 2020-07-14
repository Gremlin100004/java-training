package com.senla.carservice.factory.container;

import com.senla.carservice.factory.customizer.BeanPostProcessor;

import java.util.List;

public interface ContainerClass {
    <T> Class<? extends T> getImplementClass(Class<? extends T> interfaceClass);

    <T> List<Class<? extends BeanPostProcessor>> getConfigurableClass(Class<? extends T> interfaceConfigurableClass);
}