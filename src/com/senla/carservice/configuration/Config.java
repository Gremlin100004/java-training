package com.senla.carservice.configuration;

public interface Config {
    <T> Class<? extends T> getImplementClass(Class<T> classInterface);
}
