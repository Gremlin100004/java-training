package com.senla.carservice.test;

import com.senla.carservice.annotation.InjectProperty;

public class RepositoryImpl implements Repository {
    @InjectProperty("carservice.test.repository.value")
    private String value = "default value";

    @Override
    public String getValue() {
        return value;
    }
}
