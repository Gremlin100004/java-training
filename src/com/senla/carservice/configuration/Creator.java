package com.senla.carservice.configuration;

public interface Creator {
    <T> T createRawObject(Class<? extends T> implementClass);
}