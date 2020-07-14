package com.senla.carservice;

import com.senla.carservice.controller.CarOfficeController;
import com.senla.carservice.factory.Builder;
import com.senla.carservice.factory.Context;
import com.senla.carservice.factory.BootApplication;
import com.senla.carservice.ui.menu.MenuController;

public class Main {
    public static void main(String[] args) {
        BootApplication springBootApplication = new BootApplication();
        Context context = springBootApplication.run();
        MenuController menuController = context.createObject(MenuController.class);
        menuController.configure();
        CarOfficeController carOfficeController = Builder.getInstance().createObject(CarOfficeController.class);
        carOfficeController.deserializeEntities();
        menuController.run();
        carOfficeController.serializeEntities();
    }
}