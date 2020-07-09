package com.senla.carservice.factory.container;

public interface  ContainerSingleton  {

    <T> T getObjectSingleton(Class<? extends T> implementClass);

    <T> void addSingleton(Class<? extends T> implementClass, T singletonObject);
}