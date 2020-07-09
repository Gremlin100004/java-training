package com.senla.carservice.test;

import com.senla.carservice.factory.annotation.Dependency;

public class RepositoryImpl implements Repository {
    @Dependency
    private TestObject testObject;

    @Override
    public String getValue() {
        return testObject.getValue();
    }
}