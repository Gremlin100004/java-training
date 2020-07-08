package com.senla.carservice.test;

import com.senla.carservice.annotation.InjectDependency;

public class ServiceImpl implements Service {
    @InjectDependency
    private Repository repository;
    @Override
    public void doAction() {
        String value = repository.getValue();
        System.out.println(value);
    }
}
