package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.AEntity;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

@Slf4j
public abstract class AbstractDao<T extends AEntity, PK extends Serializable> implements GenericDao<T, PK> {
    private Class<T> type;
    @PersistenceContext
    protected EntityManager entityManager;

    public AbstractDao() {
    }

    public void setType(final Class<T> typeClass) {
        type = typeClass;
    }

    @Override
    public T saveRecord(final T entity) {
        log.debug("[entity: {}]", entity);
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public T findById(final PK id) {
        log.debug("[type: {}, id: {}]", type, id);
        return entityManager.find(type, id);
    }

    @Override
    public List<T> getAllRecords(final int firstResult, final int maxResults) {
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
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
        log.debug("[entity: {}]", entity);
        entityManager.merge(entity);
    }

    @Override
    public void deleteRecord(T entity) {
        log.debug("[entity: {}]", entity);
        entityManager.remove(entity);
    }

}
