package com.senla.carservice.connection;

import java.sql.Connection;

public interface DatabaseConnection {
    Connection getConnection();

    void closeConnection();

    void disableAutoCommit();

    void enableAutoCommit();

    void commitTransaction();

    void rollBackTransaction();
}
