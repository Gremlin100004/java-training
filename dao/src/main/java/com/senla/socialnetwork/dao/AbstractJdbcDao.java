package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.dao.connection.DatabaseConnection;
import com.senla.socialnetwork.dao.exception.DaoException;
import com.senla.socialnetwork.model.AEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractJdbcDao<T extends AEntity, PK extends Serializable> implements GenericDao<T, PK> {
    protected static final int STATEMENT_WILDCARD_FIRST_INDEX = 1;
    protected static final int ARRAY_FIRST_INDEX = 0;
    @Autowired
    @SuppressWarnings("checkstyle:VisibilityModifier")
    protected DatabaseConnection databaseConnection;

    @Override
    public T save(final T entity) {
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(getCreateRequest())) {
            fillStatementCreate(statement, entity);
            entity.setId((long) statement.executeUpdate());
            return entity;
        } catch (SQLException exception) {
            throw new DaoException("Error request add record");
        }
    }

    @Override
    public T findById(final PK id) {
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(getFindByIdRequest())) {
            statement.setObject(STATEMENT_WILDCARD_FIRST_INDEX, id);
            return parseResultSet(statement.executeQuery()).get(ARRAY_FIRST_INDEX);
        } catch (SQLException exception) {
            return null;
        }
    }

    @Override
    public List<T> getAllRecords(final int firstResult, final int maxResults) {
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(getReadAllRequest())) {
            return parseResultSet(statement.executeQuery());
        } catch (SQLException exception) {
            return new ArrayList<>();
        }
    }

    @Override
    public void updateRecord(final T entity) {
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(getUpdateRequest())) {
            fillStatementUpdate(statement, entity);
            statement.execute();
        } catch (SQLException exception) {
            throw new DaoException("Error request update record");
        }
    }

    @Override
    public void deleteRecord(final T entity) {
        try (PreparedStatement statement = databaseConnection.getConnection().prepareStatement(getDeleteRequest())) {
            statement.setLong(STATEMENT_WILDCARD_FIRST_INDEX, entity.getId());
            statement.execute();
        } catch (SQLException exception) {
            throw new DaoException("Error request delete record");
        }
    }

    protected abstract void fillStatementCreate(PreparedStatement statement, T object);

    protected abstract void fillStatementUpdate(PreparedStatement statement, T object);

    protected abstract List<T> parseResultSet(ResultSet resultSet) throws SQLException;

    protected abstract String getCreateRequest();

    protected abstract String getUpdateRequest();

    protected abstract String getFindByIdRequest();

    protected abstract String getReadAllRequest();

    protected abstract String getDeleteRequest();

}
