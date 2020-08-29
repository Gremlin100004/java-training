package com.senla.carservice;

import com.senla.carservice.Container;
import com.senla.carservice.dao.connection.DatabaseConnection;
import com.senla.carservice.ui.menu.MenuController;

public class Main {
    public static void main(String[] args) {
        MenuController menuController = Container.getObject(MenuController.class);
        menuController.run();
        DatabaseConnection databaseConnection = Container.getObject(DatabaseConnection.class);
        databaseConnection.closeConnection();
    }
}