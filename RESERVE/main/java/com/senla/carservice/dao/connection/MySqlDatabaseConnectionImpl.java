package com.senla.carservice.dao.connection;

import com.senla.carservice.annotation.Singleton;
import com.senla.carservice.objectadjuster.propertyinjection.annotation.ConfigProperty;
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
        try {
            if (connection.isClosed()){
                connectToDatabase();
            }
        } catch (SQLException e) {
            throw new BusinessException("Error connect to database");
        }
        return connection;
    }

    @Override
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new BusinessException("Error close connection");
        }
    }

    @Override
    public void disableAutoCommit() {
        try {
            if (connection == null) {
                connectToDatabase();
            }
            if (!connection.isClosed()) {
                connection.setAutoCommit(false);
            }
        } catch (SQLException e) {
            throw new BusinessException("Error disable AutoCommit");
        }
    }

    @Override
    public void enableAutoCommit() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new BusinessException("Error enable AutoCommit");
        }
    }

    @Override
    public void commitTransaction() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.commit();
            }
        } catch (SQLException e) {
            throw new BusinessException("Error commit transaction");
        }
    }

    @Override
    public void rollBackTransaction() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new BusinessException("Error rollback transaction");
        }
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(connectionUrl, userName, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new BusinessException("Wrong connection to database");
        }
    }
}