package com.senla.carservice;

import com.senla.carservice.ui.menu.MenuController;

public class Main {
    public static void main(String[] args) {
        MenuController menuController = MenuController.getInstance();
        menuController.run();
    }
}