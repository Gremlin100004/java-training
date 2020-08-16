package com.senla.carservice.dao.connection;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.exception.BusinessException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class MySqlConnection {
    @ConfigProperty
    private String userName;
    @ConfigProperty
    private String password;
    @ConfigProperty
    private String connectionUrl;
    private Connection connection;

    public void connectToDatabase (){
        if (connection != null){
            return;
        }
        try (Connection connection = DriverManager.getConnection(connectionUrl, userName, password)) {
            this.connection = connection;
        } catch (SQLException e) {
            throw new BusinessException("Wrong connection to database");
        }
    }

    public Connection getConnection() {
        return connection;
    }
}