package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.AEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.util.List;

@Slf4j
public abstract class AbstractJdbcTemplateDao<T extends AEntity, PK extends Serializable> implements GenericDao<T, PK> {
    protected Class<? extends RowMapper<T>> mapperType;
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public AbstractJdbcTemplateDao() {
    }

    public void setMapperType(final Class<? extends RowMapper<T>> typeClass) {
        mapperType = typeClass;
    }

    @Override
    public T saveRecord(T entity) {
        log.debug("[entity: {}]", entity);
        System.out.println(getSaveRequest(entity));
        entity.setId((long) jdbcTemplate.update(getSaveRequest(entity)));
        return entity;
    }

    @Override
    public T findById(PK id) {
        log.debug("[id: {}]", id);
        try {
            return jdbcTemplate.queryForObject(getFindByIdRequest(), new Object[]{id}, mapperType.newInstance());
        } catch (InstantiationException | IllegalAccessException | DataAccessException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public List<T> getAllRecords(int firstResult, int maxResults) {
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        try {
            return jdbcTemplate.query(
                    getReadAllRequest(), new Object[]{firstResult, maxResults}, mapperType.newInstance());
        } catch (InstantiationException | IllegalAccessException | DataAccessException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public void updateRecord(T entity) {
        log.debug("[entity: {}]", entity);
        jdbcTemplate.update(getUpdateRequest(entity));
    }

    @Override
    public void deleteRecord(T entity) {
        log.debug("[entity: {}]", entity);
        jdbcTemplate.update(getDeleteRequest(), entity.getId());
    }

    protected abstract String getSaveRequest( T entity);

    protected abstract String getFindByIdRequest();

    protected abstract String getUpdateRequest(T entity);

    protected abstract String getReadAllRequest();

    protected abstract String getDeleteRequest();

}
