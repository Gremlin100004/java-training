package com.senla.carservice.test;

import com.senla.carservice.configuration.Builder;

public class Main {
    public static void main(String[] args) {
        TestService testService = Builder.getInstance().createObject(TestService.class);
        testService.run();
    }
}