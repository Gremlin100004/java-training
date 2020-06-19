package com.senla.carservice;

import com.senla.carservice.api.menu.MenuController;

public class Main {
    public static void main(String[] args){
        MenuController menuController = new MenuController();
        menuController.run();
    }
}