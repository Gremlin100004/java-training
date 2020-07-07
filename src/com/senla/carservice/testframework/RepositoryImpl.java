package com.senla.carservice.testframework;

import com.senla.carservice.annotation.InjectProperty;

public class RepositoryImpl implements Repository {
    @InjectProperty("csvPathMaster")
    private String value = "Hello Pediki!!!";

    @Override
    public String getValue() {
        return value;
    }
}
