package com.senla.carservice.ui;

import com.senla.carservice.ui.config.RestClientConfig;
import com.senla.carservice.ui.menu.MenuController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CarServiceApp {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(RestClientConfig.class);
        MenuController menuController = applicationContext.getBean(MenuController.class);
        menuController.run();
    }

}
