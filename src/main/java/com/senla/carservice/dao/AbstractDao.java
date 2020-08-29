package com.senla.carservice.dao;

import com.senla.carservice.dao.connection.DatabaseConnection;
import com.senla.carservice.exception.BusinessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDao <T> implements GenericDao<T> {

    public AbstractDao() {
    }

    @Override
    public void createRecord(T object, DatabaseConnection databaseConnection) {
        String request = getCreateRequest();
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(request)) {
            fillStatementCreate(statement, object);
            statement.execute();
        } catch (SQLException ex) {
            throw new BusinessException("Error request add record");
        }
    }

    @Override
    public List<T> getAllRecords(DatabaseConnection databaseConnection) {
        String request = getReadAllRequest();
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(request)) {
            ResultSet resultSet = statement.executeQuery();
            return parseResultSet(resultSet);
        } catch (Exception e) {
            throw new BusinessException("Error request get all records");
        }
    }

    @Override
    public void updateRecord(T object, DatabaseConnection databaseConnection) {
        String request = getUpdateRequest();
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(request)) {
            fillStatementUpdate(statement, object);
            statement.execute();
        } catch (SQLException ex) {
            throw new BusinessException("Error request update record");
        }
    }

    @Override
    public void updateAllRecords(List<T> objects, DatabaseConnection databaseConnection) {
        for (T object : objects) {
            String request = getUpdateAllRecordsRequest();
            try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(request)) {
                fillStatementUpdateAll(statement, object);
                statement.execute();
            } catch (SQLException ex) {
                throw new BusinessException("Error request update all records");
            }
        }
    }

    @Override
    public void deleteRecord(T object, DatabaseConnection databaseConnection) {
        String request = getDeleteRequest();
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(request)) {
            fillStatementDelete(statement, object);
            statement.execute();
        } catch (SQLException ex) {
            throw new BusinessException("Error request delete record");
        }
    }

    protected abstract void fillStatementCreate(PreparedStatement statement, T object);

    protected abstract void fillStatementUpdate(PreparedStatement statement, T object);

    protected abstract void fillStatementUpdateAll(PreparedStatement statement, T object);

    protected abstract void fillStatementDelete(PreparedStatement statement, T object);

    protected abstract List<T> parseResultSet(ResultSet resultSet);

    protected abstract String getCreateRequest();

    protected abstract String getUpdateRequest();

    protected abstract String getUpdateAllRecordsRequest();

    protected abstract String getReadAllRequest();

    protected abstract String getDeleteRequest();
}