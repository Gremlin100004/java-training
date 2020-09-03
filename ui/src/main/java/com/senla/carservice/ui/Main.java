package com.senla.carservice.ui;

import com.senla.carservice.connection.DatabaseConnection;
import com.senla.carservice.container.Container;
import com.senla.carservice.ui.menu.MenuController;

public class Main {

    public static void main(String[] args) {
        MenuController menuController = Container.getObject(MenuController.class);
        menuController.run();
        HibernateSessionFactory hibernateSessionFactory = Container.getObject(DatabaseConnection.class);
        databaseConnection.closeConnection();
    }
}