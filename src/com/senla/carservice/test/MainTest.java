package com.senla.carservice.test;

import com.senla.carservice.factory.Builder;

public class MainTest {
    public static void main(String[] args) {
        TestService testService = Builder.getInstance().createObject(TestService.class);
        testService.run();
    }
}