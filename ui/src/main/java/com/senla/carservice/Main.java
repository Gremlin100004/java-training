package com.senla.carservice;

import com.senla.carservice.connection.DatabaseConnection;
import com.senla.carservice.menu.MenuController;

public class Main {
    public static void main(String[] args) {
        MenuController menuController = Container.getObject(MenuController.class);
        menuController.run();
        DatabaseConnection databaseConnection = Container.getObject(DatabaseConnection.class);
        databaseConnection.closeConnection();
    }
}
