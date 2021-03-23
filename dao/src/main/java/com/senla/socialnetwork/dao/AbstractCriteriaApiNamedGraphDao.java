package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.model.AEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractCriteriaApiNamedGraphDao<T extends AEntity, PK extends Serializable> implements
                                                                                                   GenericDao<T, PK> {
    protected static final String ATTRIBUTE_NAME = "javax.persistence.fetchgraph";
    private Class<T> type;
    @PersistenceContext
    @SuppressWarnings("checkstyle:VisibilityModifier")
    protected EntityManager entityManager;

    public AbstractCriteriaApiNamedGraphDao() {
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
        Map<String, Object> hints = new HashMap<>();
        hints.put(ATTRIBUTE_NAME, entityManager.getEntityGraph(getGraphName()));
        return entityManager.find(type, id, hints);
    }

    @Override
    public List<T> getAllRecords(final int firstResult, final int maxResults) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery.select(root);
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        query.setHint(ATTRIBUTE_NAME, entityManager.getEntityGraph(getGraphName()));
        query.setFirstResult(firstResult);
        if (maxResults != 0) {
            query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    @Override
    public void updateRecord(final T entity) {
        entityManager.merge(entity);
    }

    @Override
    public void deleteRecord(final T entity) {
        entityManager.remove(entity);
    }

    protected abstract String getGraphName();
}
