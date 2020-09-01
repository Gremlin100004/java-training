package com.senla.carservice.connection;

import com.senla.carservice.annotation.Singleton;
import com.senla.carservice.objectadjuster.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.exception.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class MySqlDatabaseConnectionImpl implements DatabaseConnection {

    @ConfigProperty(configName = "db.properties")
    private String userName;
    @ConfigProperty(configName = "db.properties")
    private String password;
    @ConfigProperty(configName = "db.properties")
    private String connectionUrl;
    @ConfigProperty(configName = "db.properties")
    private String driverDatabase;
    private Connection connection;
    private static final Logger LOGGER = LoggerFactory.getLogger(MySqlDatabaseConnectionImpl.class);

    @Override
    public Connection getConnection() {
        if (connection == null) {
            connectToDatabase();
        }
        try {
            if (connection.isClosed()) {
                connectToDatabase();
            }
        } catch (SQLException e) {
            throw new DaoException("Error connect to database");
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
            throw new DaoException("Error close connection");
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
            throw new DaoException("Error disable AutoCommit");
        }
    }

    @Override
    public void enableAutoCommit() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DaoException("Error enable AutoCommit");
        }
    }

    @Override
    public void commitTransaction() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.commit();
            }
        } catch (SQLException e) {
            throw new DaoException("Error commit transaction");
        }
    }

    @Override
    public void rollBackTransaction() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new DaoException("Error rollback transaction");
        }
    }

    private void connectToDatabase() {
        LOGGER.info("try connect to database");
        try {
            Class.forName(driverDatabase);
            this.connection = DriverManager.getConnection(connectionUrl, userName, password);
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.error(String.valueOf(e));
            throw new DaoException("Wrong connection to database");
        }
    }
}