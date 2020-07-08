package com.senla.carservice;

import com.senla.carservice.configuration.Builder;
import com.senla.carservice.controller.CarOfficeController;
import com.senla.carservice.ui.menu.MenuController;

public class Main {
    public static void main(String[] args) {
//        MainService mainService = (MainService) Builder.getInstance().createObject(MainService.class);
//        mainService.run();

        MenuController menuController = Builder.getInstance().createObject(MenuController.class);
        menuController.configure();
        CarOfficeController carOfficeController = (CarOfficeController) Builder.getInstance().createObject(CarOfficeController.class);
        carOfficeController.deserializeEntities();
        menuController.run();
        carOfficeController.serializeEntities();
    }
}