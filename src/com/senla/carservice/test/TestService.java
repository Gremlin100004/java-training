package com.senla.carservice.test;

import com.senla.carservice.annotation.InjectDependency;

public class TestService {
    @InjectDependency
    private Service service;

    public TestService() {
    }

    public void run(){
        service.doAction();
    }
}
