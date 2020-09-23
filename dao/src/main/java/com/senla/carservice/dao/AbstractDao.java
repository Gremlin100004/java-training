package com.senla.carservice.dao;

import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.domain.AEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractDao<T extends AEntity, PK extends Serializable> implements GenericDao<T, PK> {

    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
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
    public void saveRecord(T entity) {
        LOGGER.debug("Method saveRecord");
        LOGGER.trace("Parameter entity: {}", entity);
        entityManager.persist(entity);
    }

    @Override
    public T findById(PK id) {
        LOGGER.debug("Method findById");
        LOGGER.trace("Parameter type: {}", type);
        LOGGER.trace("Parameter id: {}", id);
        T entity = entityManager.find(type, id);
        if (entity == null) {
            throw new DaoException("Error get record by id");
        }
        return entity;
    }

    @Override
    public List<T> getAllRecords() {
        LOGGER.debug("Method getAllRecords");
        LOGGER.trace("Parameter type: {}", type);
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
        LOGGER.debug("Method updateRecord");
        LOGGER.trace("Parameter entity: {}", entity);
        entityManager.merge(entity);
    }

    @Override
    public void updateAllRecords(List<T> entities) {
        LOGGER.debug("Method updateAllRecords");
        LOGGER.trace("Parameter entities: {}", entities);
        for (T entity : entities) {
            entityManager.merge(entity);
        }
    }

    @Override
    public void deleteRecord(PK id) {
        LOGGER.debug("Method deleteRecord");
        LOGGER.trace("Parameter id: {}", id);
        entityManager.remove(id);
    }
}