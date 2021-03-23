package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.model.AEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractJdbcTemplateDao<T extends AEntity, PK extends Serializable> implements GenericDao<T, PK> {
    @Autowired
    @SuppressWarnings("checkstyle:VisibilityModifier")
    protected JdbcTemplate jdbcTemplate;

    public AbstractJdbcTemplateDao() {
    }

    @Override
    public T save(final T entity) {
        entity.setId((long) jdbcTemplate.update(getSaveRequest(entity)));
        return entity;
    }

    @Override
    public T findById(final PK id) {
        try {
            return jdbcTemplate.queryForObject(getFindByIdRequest(), new Object[]{id}, getMapper());
        } catch (DataAccessException exception) {
            return null;
        }
    }

    @Override
    public List<T> getAllRecords(final int firstResult, final int maxResults) {
        try {
            return jdbcTemplate.query(getReadAllRequest(), new Object[]{firstResult, maxResults}, getMapper());
        } catch (DataAccessException exception) {
            return null;
        }
    }

    @Override
    public void updateRecord(final T entity) {
        jdbcTemplate.update(getUpdateRequest(entity));
    }

    @Override
    public void deleteRecord(final T entity) {
        jdbcTemplate.update(getDeleteRequest(), entity.getId());
    }

    protected abstract RowMapper<T> getMapper();

    protected abstract String getSaveRequest(T entity);

    protected abstract String getFindByIdRequest();

    protected abstract String getUpdateRequest(T entity);

    protected abstract String getReadAllRequest();

    protected abstract String getDeleteRequest();

}
