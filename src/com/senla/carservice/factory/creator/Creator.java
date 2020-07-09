package com.senla.carservice.factory.creator;

public interface Creator {
    <T> T createRawObject(Class<? extends T> implementClass);
}