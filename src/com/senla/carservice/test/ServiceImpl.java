package com.senla.carservice.test;

import com.senla.carservice.factory.annotation.Dependency;

public class ServiceImpl implements Service {
    @Dependency
    private Repository repository;
    @Override
    public void doAction() {
        String value = repository.getValue();
        System.out.println(value);
    }
}