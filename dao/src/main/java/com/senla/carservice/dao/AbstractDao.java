package com.senla.carservice.dao;

import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.dao.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractDao<T, PK extends Serializable> implements GenericDao<T, PK> {

    @Autowired
    protected SessionUtil sessionUtil;
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    public AbstractDao() {
    }

    @Override
    public Serializable saveRecord(T object) {
        LOGGER.debug("Method saveRecord");
        LOGGER.trace("Parameter object: {}", object);
        Session session = sessionUtil.getSession();
        return session.save(object);
    }

    @Override
    public T getRecordById(Class<T> type, PK id) {
        LOGGER.debug("Method getRecordById");
        LOGGER.trace("Parameter type: {}", type);
        LOGGER.trace("Parameter id: {}", id);
        Session session = sessionUtil.getSession();
        T object = session.get(type, id);
        if (object == null) {
            throw new DaoException("Error get record by id");
        }
        return object;
    }

    @Override
    public List<T> getAllRecords(Class<T> type) {
        LOGGER.debug("Method getAllRecords");
        LOGGER.trace("Parameter type: {}", type);
        Session session = sessionUtil.getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery.select(root);
        Query<T> query = session.createQuery(criteriaQuery);
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
        Session session = sessionUtil.getSession();
        session.update(object);
    }

    @Override
    public void updateAllRecords(List<T> objects) {
        LOGGER.debug("Method updateAllRecords");
        LOGGER.trace("Parameter objects: {}", objects);
        Session session = sessionUtil.getSession();
        for (T object : objects) {
            session.merge(object);
        }
    }

    @Override
    public void deleteRecord(PK id) {
        LOGGER.debug("Method deleteRecord");
        LOGGER.trace("Parameter id: {}", id);
        Session session = sessionUtil.getSession();
        session.delete(id);
    }

    @Override
    public Session getSession() {
        return sessionUtil.getSession();
    }
}