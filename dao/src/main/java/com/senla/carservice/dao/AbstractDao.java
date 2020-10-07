package com.senla.carservice.dao;

import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.domain.AEntity;
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

    @Override
    public void setType(final Class<T> type) {
        this.type = type;
    }

    @Override
    public T saveRecord(T entity) {
        log.debug("Method saveRecord");
        log.trace("Parameter entity: {}", entity);
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public T findById(PK id) {
        log.debug("Method findById");
        log.trace("Parameter type: {}, id: {}", type, id);
        T entity = entityManager.find(type, id);
        if (entity == null) {
            throw new DaoException("Error get record by id");
        }
        return entity;
    }

    @Override
    public List<T> getAllRecords() {
        log.debug("Method getAllRecords");
        log.trace("Parameter type: {}", type);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery.select(root);
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        List<T> entities = query.getResultList();
        if (entities.isEmpty()) {
            throw new DaoException("Error getting entities");
        }
        return entities;
    }

    @Override
    public void updateRecord(T entity) {
        log.debug("Method updateRecord");
        log.trace("Parameter entity: {}", entity);
        entityManager.merge(entity);
    }

    @Override
    public void updateAllRecords(List<T> entities) {
        log.debug("Method updateAllRecords");
        log.trace("Parameter entities: {}", entities);
        for (T entity : entities) {
            entityManager.merge(entity);
        }
    }

    @Override
    public void deleteRecord(PK id) {
        log.debug("Method deleteRecord");
        log.trace("Parameter id: {}", id);
        entityManager.remove(id);
    }

}
