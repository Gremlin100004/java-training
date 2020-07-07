package com.senla.carservice.testframework;

import com.senla.carservice.annotation.InjectDependency;

public class MainService {
    @InjectDependency
    private Repository repository;
    @InjectDependency
    private Service service;

    public MainService() {
    }

    public void run(){
        String value = repository.getValue();
        service.doAction(value);
    }
}
