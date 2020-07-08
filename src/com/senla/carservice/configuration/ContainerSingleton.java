package com.senla.carservice.configuration;

public interface  ContainerSingleton  {

    <T> T getObjectSingleton(Class<? extends T> implementClass);

    <T> void addSingleton(Class<? extends T> implementClass, T singletonObject);
}