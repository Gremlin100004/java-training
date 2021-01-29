package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.AEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Slf4j
public abstract class AbstractNativeQueryDao<T extends AEntity, PK extends Serializable> implements GenericDao<T, PK> {
    private static final int FIRST_PARAMETER_INDEX = 1;
    private static final int SECOND_PARAMETER_INDEX = 2;
    @PersistenceContext
    protected EntityManager entityManager;
    private Class<T> type;

    public AbstractNativeQueryDao() {
    }

    public void setType(final Class<T> typeClass) {
        type = typeClass;
    }

    @Override
    public T saveRecord(T entity) {
        log.debug("[entity: {}]", entity);
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T findById(PK id) {
        log.debug("[id: {}]", id);
        try {
            return (T) entityManager
                .createNativeQuery(getFindByIdRequest(), type)
                .setParameter(FIRST_PARAMETER_INDEX, id)
                .getSingleResult();
        } catch (NoResultException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getAllRecords(int firstResult, int maxResults) {
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        try {
            return entityManager
                .createNativeQuery(getReadAllRequest(), type)
                .setParameter(FIRST_PARAMETER_INDEX, firstResult)
                .setParameter(SECOND_PARAMETER_INDEX, maxResults)
                .getResultList();
        } catch (DataAccessException exception) {
            log.error("[{}]", exception.getMessage());
            return null;
        }
    }

    @Override
    public void updateRecord(T entity) {
        log.debug("[entity: {}]", entity);
        entityManager.createNativeQuery(getUpdateRequest(entity), type);
    }

    @Override
    public void deleteRecord(T entity) {
        log.debug("[entity: {}]", entity);
        entityManager.createNativeQuery(getDeleteRequest(), type);
    }

    protected abstract String getFindByIdRequest();

    protected abstract String getUpdateRequest(T entity);

    protected abstract String getReadAllRequest();

    protected abstract String getDeleteRequest();

}
