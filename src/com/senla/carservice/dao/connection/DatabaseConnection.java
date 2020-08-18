package com.senla.carservice.dao.connection;

import java.sql.Connection;

public interface DatabaseConnection {
    Connection getConnection();

    void closeConnection();
}
