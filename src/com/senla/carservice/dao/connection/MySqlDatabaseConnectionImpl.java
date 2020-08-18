package com.senla.carservice.dao.connection;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.exception.BusinessException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class MySqlDatabaseConnectionImpl implements DatabaseConnection {
    @ConfigProperty
    private String userName;
    @ConfigProperty
    private String password;
    @ConfigProperty
    private String connectionUrl;
    private Connection connection;

    @Override
    public Connection getConnection() {
        if (connection == null) {
            connectToDatabase();
        }
        return connection;
    }
    @Override
    public void closeConnection(){
        try {
            if (connection != null && !connection.isClosed())
            connection.close();
        } catch (SQLException e) {
            throw new BusinessException("Error close connection");
        }
    }

    private void connectToDatabase() {
        try {
            this.connection = DriverManager.getConnection(connectionUrl, userName, password);
        } catch (SQLException e) {
            throw new BusinessException("Wrong connection to database");
        }
    }
}