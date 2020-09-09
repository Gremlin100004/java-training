package com.senla.carservice.hibernatedao;

import com.senla.carservice.hibernatedao.exception.DaoException;
import com.senla.carservice.hibernatedao.util.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractDao<T, PK extends Serializable> implements GenericDao<T, PK> {

    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    protected final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public AbstractDao() {
    }

    @Override
    public void saveRecord(T object) {
        LOGGER.debug("Method saveRecord");
        LOGGER.trace("Parameter object: {}", object);
        Session session = sessionFactory.getCurrentSession();
        session.save(object);
    }

    @Override
    public List<T> getAllRecords(Class<T> type) {
        LOGGER.debug("Method getAllRecords");
        LOGGER.trace("Parameter type: {}", type);
        Session session = sessionFactory.getCurrentSession();
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
        Session session = sessionFactory.getCurrentSession();
        session.update(object);
    }

    @Override
    public void updateAllRecords(List<T> objects) {
        LOGGER.debug("Method updateAllRecords");
        LOGGER.trace("Parameter objects: {}", objects);
        Session session = sessionFactory.getCurrentSession();
        for (T object : objects) {
            session.update(object);
        }
    }

    @Override
    public void deleteRecord(PK id) {
        LOGGER.debug("Method deleteRecord");
        LOGGER.trace("Parameter id: {}", id);
        Session session = sessionFactory.getCurrentSession();
        session.delete(id);
    }

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}