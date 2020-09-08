package com.senla.carservice.hibernatedao;

import com.senla.carservice.hibernatedao.exception.DaoException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractDao<T, PK extends Serializable> implements GenericDao<T, PK> {

    protected final Logger LOGGER = LogManager.getLogger(this.getClass());

    public AbstractDao() {
    }

    @Override
    public void saveRecord(T object, Session session) {
        LOGGER.debug("Method saveRecord");
        LOGGER.trace("Parameter object: " + object);
        LOGGER.trace("Parameter session: " + session);
        session.save(object);
    }

    @Override
    public List<T> getAllRecords(Session session, Class<T> type) {
        LOGGER.debug("Method getAllRecords");
        LOGGER.trace("Parameter session: " + session);
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
    public void updateRecord(T object, Session session) {
        LOGGER.debug("Method updateRecord");
        LOGGER.trace("Parameter object: " + object);
        LOGGER.trace("Parameter session: " + session);
        session.update(object);
    }

    @Override
    public void updateAllRecords(List<T> objects, Session session) {
        LOGGER.debug("Method updateAllRecords");
        LOGGER.trace("Parameter objects: " + objects);
        LOGGER.trace("Parameter session: " + session);
        for (T object : objects) {
            session.update(object);
        }
    }

    @Override
    public void deleteRecord(PK id, Session session) {
        LOGGER.debug("Method deleteRecord");
        LOGGER.trace("Parameter id: " + id);
        LOGGER.trace("Parameter session: " + session);
        session.delete(id);
    }
}