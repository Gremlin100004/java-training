package com.senla.carservice;

import com.senla.carservice.configuration.PackageScanner;
import com.senla.carservice.ui.menu.MenuController;
import com.senla.carservice.util.PropertyLoader;

public class Main {
    public static void main(String[] args) {
        PackageScanner packageScanner = new PackageScanner(PropertyLoader.getPropertyValue("packageProject"));
        packageScanner.getImplementedClass();

//        MenuController menuController = MenuController.getInstance();
//        menuController.run();
    }
}