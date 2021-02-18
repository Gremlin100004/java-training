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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbstractCriteriaApiNamedGraphDao<T extends AEntity, PK extends Serializable> implements GenericDao<T, PK> {
    protected static final String ATTRIBUTE_NAME = "javax.persistence.fetchgraph";
    private Class<T> type;
    @PersistenceContext
    protected EntityManager entityManager;

    public AbstractCriteriaApiNamedGraphDao() {
    }

    public void setType(final Class<T> typeClass) {
        type = typeClass;
    }

    @Override
    public T saveRecord(final T entity) {
        log.debug("[saveRecord]");
        log.debug("[entity: {}]", entity);
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public T findById(final PK id) {
        log.debug("[findById]");
        log.debug("[type: {}, id: {}]", type, id);
        Map<String, Object> hints = new HashMap<>();
        hints.put(ATTRIBUTE_NAME, entityManager.getEntityGraph(getGraphName()));
        return entityManager.find(type, id, hints);
    }

    @Override
    public List<T> getAllRecords(final int firstResult, final int maxResults) {
        log.debug("[getAllRecords]");
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
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
        log.debug("[updateRecord]");
        log.debug("[entity: {}]", entity);
        entityManager.merge(entity);
    }

    @Override
    public void deleteRecord(T entity) {
        log.debug("[deleteRecord]");
        log.debug("[entity: {}]", entity);
        entityManager.remove(entity);
    }

    protected abstract String getGraphName();

}
