package com.senla.carservice.testframework;

public class ServiceImpl implements Service {
    @Override
    public void doAction(String value) {
        System.out.println(value);
    }
}
