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
        LOGGER.trace("Parameter object: {}", entity);
        entityManager.persist(entity);
    }

    @Override
    public T findById(PK id) {
        LOGGER.debug("Method findById");
        LOGGER.trace("Parameter type: {}", type);
        LOGGER.trace("Parameter id: {}", id);
        T object = entityManager.find(type, id);
        if (object == null) {
            throw new DaoException("Error get record by id");
        }
        return object;
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
        List<T> objects = query.getResultList();
        if (objects == null) {
            throw new DaoException("Error getting objects");
        }
        return objects;
    }

    @Override
    public void updateRecord(T object) {
        LOGGER.debug("Method updateRecord");
        LOGGER.trace("Parameter object: {}", object);
        entityManager.merge(object);
    }

    @Override
    public void updateAllRecords(List<T> objects) {
        LOGGER.debug("Method updateAllRecords");
        LOGGER.trace("Parameter objects: {}", objects);
        for (T object : objects) {
            entityManager.merge(object);
        }
    }

    @Override
    public void deleteRecord(PK id) {
        LOGGER.debug("Method deleteRecord");
        LOGGER.trace("Parameter id: {}", id);
        entityManager.remove(id);
    }
}