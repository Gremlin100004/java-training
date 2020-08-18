package com.senla.carservice.dao;

import com.senla.carservice.dao.connection.DatabaseConnection;
import com.senla.carservice.exception.BusinessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractDao implements GenericDao {
    protected DatabaseConnection databaseConnection;

    public AbstractDao(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public AbstractDao() {
    }

    @Override
    public void createRecord(Object object) {
        String request = getCreateRequest(object);
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(request)) {
            statement.execute();
        } catch (SQLException ex) {
            throw new BusinessException("Error request add record");
        }
    }

    @Override
    public List<Object> getAllRecords() {
        String request = getReadAllRequest();
        try (Statement statement = databaseConnection.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(request);
            return parseResultSet(resultSet);
        } catch (Exception e) {
            throw new BusinessException("Error request get all records");
        }
    }

    @Override
    public void updateRecord(Object object) {
        String request = getUpdateRequest(object);
        try (Statement statement = databaseConnection.getConnection().createStatement()) {
            statement.execute(request);
        } catch (SQLException ex) {
            throw new BusinessException("Error request update record");
        }
    }

    @Override
    public void updateAllRecords(List objects) {
        try (Statement statement = databaseConnection.getConnection().createStatement()) {
            for (Object object : objects){
                String request = getUpdateRequest(object);
                statement.execute(request);
            }
        } catch (SQLException ex) {
            throw new BusinessException("Error request update all records");
        }
    }

    @Override
    public void deleteRecord(Object object) {
        String request = getDeleteRequest(object);
        try (Statement statement = databaseConnection.getConnection().createStatement()) {
            statement.execute(request);
        } catch (SQLException ex) {
            throw new BusinessException("Error request delete record");
        }
    }

    protected abstract <T> List<T> parseResultSet(ResultSet resultSet);

    protected abstract <T> String getCreateRequest(T object);

    protected abstract String getReadAllRequest();

    protected abstract <T> String getUpdateRequest(T object);

    protected abstract <T> String getDeleteRequest(T object);
}