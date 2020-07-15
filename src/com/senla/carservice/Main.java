package com.senla.carservice;

import com.senla.carservice.container.Container;
import com.senla.carservice.ui.menu.MenuController;

public class Main {
    public static void main(String[] args) {
        Container container = new Container();
        MenuController menuController = (MenuController) container.getObject(MenuController.class);
        menuController.configure();
//        CarOfficeController carOfficeController = (CarOfficeController) container.getObject(MenuController.class);
//        carOfficeController.deserializeEntities();
        menuController.run();
//        carOfficeController.serializeEntities();
    }
}