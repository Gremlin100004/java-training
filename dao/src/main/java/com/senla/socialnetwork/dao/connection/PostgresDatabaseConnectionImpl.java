package com.senla.socialnetwork.dao.connection;

import com.senla.socialnetwork.dao.exception.DaoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@Slf4j
public final class PostgresDatabaseConnectionImpl implements DatabaseConnection {
    @Value("${hibernate.connection.username:Test}")
    private String userName;
    @Value("${hibernate.connection.password:Test}")
    private String password;
    @Value("${hibernate.connection.url:Test}")
    private String connectionUrl;
    @Value("${hibernate.connection.driver_class:Test}")
    private String driverDatabase;
    private Connection connection;

    private PostgresDatabaseConnectionImpl() {
    }

    @Override
    public Connection getConnection() {
        if (connection == null) {
            connectToDatabase();
        }
        try {
            if (connection.isClosed()) {
                connectToDatabase();
            }
        } catch (SQLException exception) {
            log.error("[{}]", exception.getMessage());
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
        } catch (SQLException exception) {
            log.error("[{}]", exception.getMessage());
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
        } catch (SQLException exception) {
            log.error("[{}]", exception.getMessage());
            throw new DaoException("Error disable AutoCommit");
        }
    }

    @Override
    public void enableAutoCommit() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.setAutoCommit(true);
            }
        } catch (SQLException exception) {
            log.error("[{}]", exception.getMessage());
            throw new DaoException("Error enable AutoCommit");
        }
    }

    @Override
    public void commitTransaction() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.commit();
            }
        } catch (SQLException exception) {
            log.error("[{}]", exception.getMessage());
            throw new DaoException("Error commit transaction");
        }
    }

    @Override
    public void rollBackTransaction() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.rollback();
            }
        } catch (SQLException exception) {
            log.error("[{}]", exception.getMessage());
            throw new DaoException("Error rollback transaction");
        }
    }

    private void connectToDatabase() {
        try {
            Class.forName(driverDatabase);
            this.connection = DriverManager.getConnection(connectionUrl, userName, password);
        } catch (SQLException | ClassNotFoundException exception) {
            log.error("[{}]", exception.getMessage());
            throw new DaoException("Wrong connection to database");
        }
    }

}
