package com.senla.carservice.ui;

import com.senla.carservice.ui.menu.MenuController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

// я бы не рекомендовал делать мейн класс и мейном, и конфигом - принцип единственной ответственности
// тем более что у тебя есть пакет конфиг - не вижу проблем сделать там класс-конфиг
@ComponentScan("com.senla.carservice.ui")
@PropertySource("classpath:application.properties")
public class CarServiceApp {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CarServiceApp.class);
        MenuController menuController = applicationContext.getBean(MenuController.class);
        menuController.run();
    }

}
