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
        String request = getCreateRequest();
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(request)) {
            fillStatementCreate(statement, object);
            statement.execute();
        } catch (SQLException ex) {
            throw new BusinessException("Error request add record");
        }
    }

    @Override
    public List<Object> getAllRecords() {
        String request = getReadAllRequest();
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(request)) {
            ResultSet resultSet = statement.executeQuery();
            return parseResultSet(resultSet);
        } catch (Exception e) {
            throw new BusinessException("Error request get all records");
        }
    }

    @Override
    public void updateRecord(Object object) {
        String request = getUpdateRequest();
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(request)) {
            fillStatementUpdate(statement, object);
            statement.execute();
        } catch (SQLException ex) {
            throw new BusinessException("Error request update record");
        }
    }

    @Override
    public void updateAllRecords(List objects) {
        for (Object object : objects) {
            String request = getUpdateRequest();
            try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(request)) {
                fillStatementUpdate(statement, object);
                statement.execute();
            } catch (SQLException ex) {
                throw new BusinessException("Error request update all records");
            }
        }
    }

    @Override
    public void deleteRecord(Object object) {
        String request = getDeleteRequest();
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(request)) {
            fillStatementDelete(statement, object);
            statement.execute();
        } catch (SQLException ex) {
            throw new BusinessException("Error request delete record");
        }
    }

    protected abstract <T> void fillStatementCreate(PreparedStatement statement, T object);

    protected abstract <T> void fillStatementUpdate(PreparedStatement statement, T object);

    protected abstract <T> void fillStatementDelete(PreparedStatement statement, T object);

    protected abstract <T> List<T> parseResultSet(ResultSet resultSet);

    protected abstract String getCreateRequest();

    protected abstract String getUpdateRequest();

    protected abstract String getReadAllRequest();

    protected abstract String getDeleteRequest();
}