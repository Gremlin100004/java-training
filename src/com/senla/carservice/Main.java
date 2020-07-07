package com.senla.carservice;

import com.senla.carservice.configuration.Builder;
import com.senla.carservice.testframework.MainService;

public class Main {
    public static void main(String[] args) {
        MainService mainService = (MainService) Builder.getInstance().createObject(MainService.class);
        mainService.run();

//        MenuController menuController = MenuController.getInstance();
//        menuController.run();
    }
}