package com.senla.carservice.test;

import com.senla.carservice.factory.annotation.Dependency;

public class TestService {
    @Dependency
    private Service service;

    public TestService() {
    }

    public void run() {
        service.doAction();
    }
}