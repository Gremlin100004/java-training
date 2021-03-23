package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.model.AEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractNativeQueryDao<T extends AEntity, PK extends Serializable> implements GenericDao<T, PK> {
    private static final int FIRST_PARAMETER_INDEX = 1;
    private static final int SECOND_PARAMETER_INDEX = 2;
    @PersistenceContext
    @SuppressWarnings("checkstyle:VisibilityModifier")
    protected EntityManager entityManager;
    private Class<T> type;

    public AbstractNativeQueryDao() {
    }

    public void setType(final Class<T> typeClass) {
        type = typeClass;
    }

    @Override
    public T save(final T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T findById(final PK id) {
        return (T) entityManager
            .createNativeQuery(getFindByIdRequest(), type)
            .setParameter(FIRST_PARAMETER_INDEX, id)
            .getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getAllRecords(final int firstResult, final int maxResults) {
        return entityManager
            .createNativeQuery(getReadAllRequest(), type)
            .setParameter(FIRST_PARAMETER_INDEX, firstResult)
            .setParameter(SECOND_PARAMETER_INDEX, maxResults)
            .getResultList();
    }

    @Override
    public void updateRecord(final T entity) {
        entityManager.createNativeQuery(getUpdateRequest(entity), type);
    }

    @Override
    public void deleteRecord(final T entity) {
        entityManager.createNativeQuery(getDeleteRequest(), type);
    }

    protected abstract String getFindByIdRequest();

    protected abstract String getUpdateRequest(T entity);

    protected abstract String getReadAllRequest();

    protected abstract String getDeleteRequest();

}
