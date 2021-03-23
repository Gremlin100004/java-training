package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.model.AEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractCriteriaApiJoinFetchDao<T extends AEntity, PK extends Serializable> implements
                                                                                                  GenericDao<T, PK> {
    private Class<T> type;
    @PersistenceContext
    @SuppressWarnings("checkstyle:VisibilityModifier")
    protected EntityManager entityManager;

    public AbstractCriteriaApiJoinFetchDao() {
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
        return entityManager.find(type, id);
    }

    @Override
    public List<T> getAllRecords(final int firstResult, final int maxResults) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        joinFetch(root);
        criteriaQuery.select(root);
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
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

    protected abstract void joinFetch(Root<T> communityRoot);

}
