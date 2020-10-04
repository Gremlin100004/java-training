package com.senla.carservice.ui;

import com.senla.carservice.ui.menu.MenuController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("com.senla.carservice.ui")
@PropertySource("classpath:application.properties")
public class CarServiceClient {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CarServiceClient.class);
        MenuController menuController = applicationContext.getBean(MenuController.class);
        menuController.run();
    }
}