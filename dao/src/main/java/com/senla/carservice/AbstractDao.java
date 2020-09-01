package com.senla.carservice;

import com.senla.carservice.connection.DatabaseConnection;
import com.senla.carservice.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDao<T> implements GenericDao<T> {


    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public AbstractDao() {
    }

    @Override
    public void createRecord(T object, DatabaseConnection databaseConnection) {
        LOGGER.debug("Method createRecord");
        LOGGER.debug("Parameter object: {}", object.toString());
        LOGGER.debug("Parameter databaseConnection: {}", databaseConnection);
        String request = getCreateRequest();
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(request)) {
            fillStatementCreate(statement, object);
            statement.execute();
        } catch (SQLException ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request add record");
        }
    }

    @Override
    public List<T> getAllRecords(DatabaseConnection databaseConnection) {
        LOGGER.debug("Method getAllRecords");
        LOGGER.debug("Parameter databaseConnection: {}", databaseConnection);
        String request = getReadAllRequest();
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(request)) {
            ResultSet resultSet = statement.executeQuery();
            return parseResultSet(resultSet);
        } catch (Exception ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request get all records");
        }
    }

    @Override
    public void updateRecord(T object, DatabaseConnection databaseConnection) {
        LOGGER.debug("Method updateRecord");
        LOGGER.debug("Parameter object: {}", object.toString());
        LOGGER.debug("Parameter databaseConnection: {}", databaseConnection);
        String request = getUpdateRequest();
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(request)) {
            fillStatementUpdate(statement, object);
            statement.execute();
        } catch (SQLException ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request update record");
        }
    }

    @Override
    public void updateAllRecords(List<T> objects, DatabaseConnection databaseConnection) {
        LOGGER.debug("Method updateAllRecords");
        LOGGER.debug("Parameter objects: {}", objects);
        LOGGER.debug("Parameter databaseConnection: {}", databaseConnection);
        for (T object : objects) {
            String request = getUpdateAllRecordsRequest();
            try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(request)) {
                fillStatementUpdateAll(statement, object);
                statement.execute();
            } catch (SQLException ex) {
                LOGGER.error(String.valueOf(ex));
                throw new DaoException("Error request update all records");
            }
        }
    }

    @Override
    public void deleteRecord(T object, DatabaseConnection databaseConnection) {
        LOGGER.debug("Method deleteRecord");
        LOGGER.debug("Parameter object: {}", object.toString());
        LOGGER.debug("Parameter databaseConnection: {}", databaseConnection);
        String request = getDeleteRequest();
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(request)) {
            fillStatementDelete(statement, object);
            statement.execute();
        } catch (SQLException ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request delete record");
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