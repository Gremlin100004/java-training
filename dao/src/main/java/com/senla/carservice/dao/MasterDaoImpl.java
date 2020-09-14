package com.senla.carservice.dao;

import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Master_;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Order_;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.Date;
import java.util.List;

@Repository
public class MasterDaoImpl extends AbstractDao<Master, Long> implements MasterDao {

    public MasterDaoImpl() {
        setType(Master.class);
    }

    @Override
    public List<Master> getFreeMasters(Date executeDate) {
        LOGGER.debug("Method getFreeMasters");
        LOGGER.trace("Parameter date: {}", executeDate);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Master> criteriaQuery = criteriaBuilder.createQuery(Master.class);
        Root<Master> masterRoot = criteriaQuery.from(Master.class);
        criteriaQuery.select(masterRoot);
        Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
        Root<Master> subMasterRoot = subquery.from(Master.class);
        Join<Master, Order> masterOrderJoin = subMasterRoot.join(Master_.orders);
        subquery.select(subMasterRoot.get(Master_.id)).distinct(true);
        subquery.where(criteriaBuilder.greaterThanOrEqualTo(masterOrderJoin.get(Order_.leadTime), executeDate));
        criteriaQuery.where(masterRoot.get(Master_.id).in(subquery).not());
        TypedQuery<Master> query = entityManager.createQuery(criteriaQuery);
        List<Master> masters = query.getResultList();
        if (masters == null) {
            throw new DaoException("Error getting free masters");
        }
        return masters;
    }

    @Override
    public Long getNumberFreeMasters(Date executeDate) {
        LOGGER.debug("Method getNumberBusyMasters");
        LOGGER.trace("Parameter executeDate: {}", executeDate);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Master> masterRoot = criteriaQuery.from(Master.class);
        criteriaQuery.select(criteriaBuilder.count(masterRoot.get(Master_.id)));
        Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
        Root<Master> subMasterRoot = subquery.from(Master.class);
        Join<Master, Order> masterOrderJoin = subMasterRoot.join(Master_.orders);
        subquery.select(subMasterRoot.get(Master_.id)).distinct(true);
        subquery.where(criteriaBuilder.greaterThanOrEqualTo(masterOrderJoin.get(Order_.leadTime), executeDate));
        criteriaQuery.where(masterRoot.get(Master_.id).in(subquery).not());
        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    @Override
    public List<Master> getMasterSortByAlphabet() {
        LOGGER.debug("Method SQL SORT BY ALPHABET:");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Master> criteriaQuery = criteriaBuilder.createQuery(Master.class);
        Root<Master> masterRoot = criteriaQuery.from(Master.class);
        criteriaQuery.select(masterRoot).distinct(true);
        criteriaQuery.orderBy(criteriaBuilder.asc(masterRoot.get(Master_.name)));
        TypedQuery<Master> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Master> masters = typedQuery.getResultList();
        if (masters.isEmpty()) {
            throw new DaoException("Error getting objects");
        }
        return masters;
    }

    @Override
    public List<Master> getMasterSortByBusy() {
        LOGGER.debug("Method getMasterSortByBusy");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Master> criteriaQuery = criteriaBuilder.createQuery(Master.class);
        Root<Master> masterRoot = criteriaQuery.from(Master.class);
        criteriaQuery.select(masterRoot).distinct(true);
        criteriaQuery.orderBy(criteriaBuilder.desc(masterRoot.get(Master_.numberOrders)));
        TypedQuery<Master> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Master> masters = typedQuery.getResultList();
        if (masters.isEmpty()) {
            throw new DaoException("Error getting objects");
        }
        return masters;
    }

    @Override
    public Long getNumberMasters() {
        LOGGER.debug("Method getNumberMasters");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Master> masterRoot = criteriaQuery.from(Master.class);
        criteriaQuery.select(criteriaBuilder.count(masterRoot));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }
}