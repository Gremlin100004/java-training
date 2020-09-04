package com.senla.carservice.hibernatedao;

import com.senla.carservice.hibernatedao.exception.DaoException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class AbstractDao<T> implements GenericDao<T> {

    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public AbstractDao() {
    }

    @Override
    public void saveRecord(T object, Session session) {
        LOGGER.debug("Method createRecord");
        LOGGER.trace("Parameter object: {}", object);
        LOGGER.trace("Parameter databaseConnection: {}", session);
        try {
            session.save(object);
        } catch (Exception ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request add record");
        }
    }

    @Override
    public List<T> getAllRecords(Session session) {
        LOGGER.debug("Method getAllRecords");
        LOGGER.trace("Parameter databaseConnection: {}", session);
        try {
//            ResultSet resultSet = statement.executeQuery();
//            return parseResultSet(resultSet);
            return null;
        } catch (Exception ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request get all records");
        }
    }

    @Override
    public void updateRecord(T object, Session session) {
        LOGGER.debug("Method updateRecord");
        LOGGER.trace("Parameter object: {}", object);
        LOGGER.trace("Parameter databaseConnection: {}", session);
        try {
//            fillStatementUpdate(statement, object);
//            statement.execute();
        } catch (Exception ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request update record");
        }
    }

    @Override
    public void updateAllRecords(List<T> objects, Session session) {
        LOGGER.debug("Method updateAllRecords");
        LOGGER.trace("Parameter objects: {}", objects);
        LOGGER.trace("Parameter databaseConnection: {}", session);
        for (T object : objects) {
            try {
//                fillStatementUpdateAll(statement, object);
//                statement.execute();
            } catch (Exception ex) {
                LOGGER.error(String.valueOf(ex));
                throw new DaoException("Error request update all records");
            }
        }
    }

    @Override
    public void deleteRecord(T object, Session session) {
        LOGGER.debug("Method deleteRecord");
        LOGGER.trace("Parameter object: {}", object);
        LOGGER.trace("Parameter databaseConnection: {}", session);
        try  {
//            fillStatementDelete(statement, object);
//            statement.execute();
        } catch (Exception ex) {
            LOGGER.error(String.valueOf(ex));
            throw new DaoException("Error request delete record");
        }
    }

//    protected abstract void fillStatementCreate(PreparedStatement statement, T object);
//
//    protected abstract void fillStatementUpdate(PreparedStatement statement, T object);
//
//    protected abstract void fillStatementUpdateAll(PreparedStatement statement, T object);
//
//    protected abstract void fillStatementDelete(PreparedStatement statement, T object);
//
//    protected abstract List<T> parseResultSet(ResultSet resultSet);
//
//    protected abstract String getCreateRequest();
//
//    protected abstract String getUpdateRequest();
//
//    protected abstract String getUpdateAllRecordsRequest();
//
//    protected abstract String getReadAllRequest();
//
//    protected abstract String getDeleteRequest();
}