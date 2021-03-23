package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.model.AEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractJpqlDao<T extends AEntity, PK extends Serializable> implements GenericDao<T, PK> {
    private static final String ID_PARAMETER = "ids";
    @PersistenceContext
    @SuppressWarnings("checkstyle:VisibilityModifier")
    protected EntityManager entityManager;
    private Class<T> type;

    public AbstractJpqlDao() {
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
    public T findById(final PK id) {
        return entityManager
            .createQuery(getFindByIdRequest(), type)
            .setParameter(1, id)
            .getSingleResult();
    }

    @Override
    public List<T> getAllRecords(final int firstResult, final int maxResults) {
        return getEntityWithPagination(firstResult, maxResults, entityManager.createQuery(
            getReadAllRequest(), Long.class).getResultList(), getRequestEntitiesWithPagination());
    }

    @Override
    public void updateRecord(final T entity) {
        entityManager
            .createQuery(getUpdateRequest(entity))
            .executeUpdate();
    }

    @Override
    public void deleteRecord(final T entity) {
        Query query = entityManager.createQuery(getDeleteRequest(), type);
        query.setParameter(1, entity.getId());
        query.executeUpdate();
    }

    protected List<T> getEntityWithPagination(final int firstResult,
                                              final int maxResults,
                                              final List<Long> idList,
                                              final String sqlRequest) {
        TypedQuery<T> query = entityManager.createQuery(sqlRequest, type);
        int maxResult = idList.size();
        if (maxResults != 0 && maxResults < maxResult) {
            maxResult = maxResults;
        }
        query.setParameter(ID_PARAMETER, idList.subList(firstResult, maxResult));
        return query.getResultList();
    }

    protected abstract String getFindByIdRequest();

    protected abstract String getUpdateRequest(T entity);

    protected abstract String getReadAllRequest();

    protected abstract String getRequestEntitiesWithPagination();

    protected abstract String getDeleteRequest();

}
