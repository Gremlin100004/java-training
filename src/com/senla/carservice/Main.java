package com.senla.carservice;

import com.senla.carservice.container.Container;
import com.senla.carservice.controller.CarOfficeController;
import com.senla.carservice.ui.menu.MenuController;

public class Main {
    public static void main(String[] args) {
        Container container = new Container();
        MenuController menuController = container.getObject(MenuController.class.getName());
        menuController.configure();
        CarOfficeController carOfficeController = container.getObject(CarOfficeController.class.getName());
        carOfficeController.deserializeEntities();
        menuController.run();
        carOfficeController.serializeEntities();
    }
}