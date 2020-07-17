package com.senla.carservice;

import com.senla.carservice.container.Container;
import com.senla.carservice.controller.CarOfficeController;
import com.senla.carservice.ui.menu.MenuController;

public class Main {
    public static void main(String[] args) {
        // создавать объект контейнера не очень удобно - можно создать случайно
        // несколько контейнеров, и объекты приложения перестанут быть синглтонами
        // лучше всего работу с контейнером построить через статик методы и позаботиться
        // об однократной его инициализации
        Container container = new Container();
        MenuController menuController = container.getObject(MenuController.class.getName());
        menuController.configure();
        CarOfficeController carOfficeController = container.getObject(CarOfficeController.class.getName());
        carOfficeController.deserializeEntities();
        menuController.run();
        carOfficeController.serializeEntities();
    }
}