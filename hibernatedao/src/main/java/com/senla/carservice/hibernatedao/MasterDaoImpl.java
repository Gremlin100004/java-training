package com.senla.carservice.hibernatedao;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Master_;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Order_;
import com.senla.carservice.hibernatedao.exception.DaoException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

@Singleton
public class MasterDaoImpl extends AbstractDao<Master, Long> implements MasterDao {

    public MasterDaoImpl() {
    }

    @Override
    public List<Master> getBusyMasters(Date executeDate, Session session) {
        LOGGER.debug("Method getFreeMasters");
        LOGGER.trace("Parameter date: " + executeDate);
        LOGGER.trace("Parameter session: " + session);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Master> criteriaQuery = criteriaBuilder.createQuery(Master.class);
        Root<Master> masterRoot = criteriaQuery.from(Master.class);
        Join<Master, Order> masterOrderJoin = masterRoot.join(Master_.orders);
        criteriaQuery.select(masterRoot).distinct(true);
        criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(masterOrderJoin.get(Order_.leadTime), executeDate));
        Query<Master> query = session.createQuery(criteriaQuery);
        List<Master> masters = query.getResultList();
        if (masters == null) {
            throw new DaoException("Error getting busy masters");
        }
        return masters;
    }

    @Override
    public Long getNumberBusyMasters(Date executeDate, Session session) {
        LOGGER.debug("Method getNumberBusyMasters");
        LOGGER.trace("Parameter executeDate: " + executeDate);
        LOGGER.trace("Parameter session: " +  session);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Master> orderRoot = criteriaQuery.from(Master.class);
        Join<Master, Order> masterOrderJoin = orderRoot.join(Master_.orders);
        criteriaQuery.select(criteriaBuilder.count(orderRoot.get(Master_.id))).distinct(true);
        criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(masterOrderJoin.get(Order_.leadTime), executeDate));
        return session.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public List<Master> getMasterSortByAlphabet(Session session) {
        LOGGER.debug("Method getMasterSortByAlphabet");
        LOGGER.trace("Parameter session: " + session);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Master> criteriaQuery = criteriaBuilder.createQuery(Master.class);
        Root<Master> masterRoot = criteriaQuery.from(Master.class);
        criteriaQuery.select(masterRoot).distinct(true);
        criteriaQuery.orderBy(criteriaBuilder.asc(masterRoot.get(Master_.name)));
        TypedQuery<Master> typedQuery = session.createQuery(criteriaQuery);
        List<Master> masters = typedQuery.getResultList();
        if (masters == null) {
            throw new DaoException("Error getting objects");
        }
        return masters;
    }

    @Override
    public List<Master> getMasterSortByBusy(Session session) {
        LOGGER.debug("Method getMasterSortByBusy");
        LOGGER.trace("Parameter session: " + session);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Master> criteriaQuery = criteriaBuilder.createQuery(Master.class);
        Root<Master> masterRoot = criteriaQuery.from(Master.class);
        criteriaQuery.select(masterRoot).distinct(true);
        criteriaQuery.orderBy(criteriaBuilder.desc(masterRoot.get(Master_.numberOrders)));
        TypedQuery<Master> typedQuery = session.createQuery(criteriaQuery);
        List<Master> masters = typedQuery.getResultList();
        if (masters == null) {
            throw new DaoException("Error getting objects");
        }
        return masters;
    }

    @Override
    public Long getNumberMasters(Session session) {
        LOGGER.debug("Method getNumberMasters");
        LOGGER.trace("Parameter session: " + session);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Master> masterRoot = criteriaQuery.from(Master.class);
        criteriaQuery.select(criteriaBuilder.count(masterRoot));
        return session.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public Master getMasterById(Long id, Session session) {
        LOGGER.debug("Method getMasterById");
        LOGGER.trace("Parameter index: " + id);
        LOGGER.trace("Parameter session: " + session);
        return session.get(Master.class, id);
    }
}