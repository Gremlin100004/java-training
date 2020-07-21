package com.senla.carservice;

import com.senla.carservice.container.Container;
import com.senla.carservice.controller.CarOfficeController;
import com.senla.carservice.ui.menu.MenuController;

public class Main {
    public static void main(String[] args) {
        // было бы лучше, если бы работа с объектами не разрывалась по строчкам
        // получение и настройка MenuController в самом начале, а запуск ближе к концу
        // а если десериализация завалится, получается, зря настраивали MenuController
        // да и при чтении не очень понятно
        MenuController menuController = Container.getObject(MenuController.class);
        menuController.configure();
        CarOfficeController carOfficeController = Container.getObject(CarOfficeController.class);
        carOfficeController.deserializeEntities();
        menuController.run();
        carOfficeController.serializeEntities();
    }
}