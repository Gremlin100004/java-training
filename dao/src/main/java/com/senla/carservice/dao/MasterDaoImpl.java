package com.senla.carservice.dao;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Master_;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Order_;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MasterDaoImpl extends AbstractDao<Master, Long> implements MasterDao {

    public MasterDaoImpl() {
        setType(Master.class);
    }

    @Override
    public List<Master> getFreeMasters(Date executeDate) {
        log.debug("[getFreeMasters]");
        log.trace("[date: {}]", executeDate);
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
        return query.getResultList();
    }

    @Override
    public Long getNumberFreeMasters(Date executeDate) {
        log.debug("[getNumberBusyMasters]");
        log.trace("[executeDate: {}]", executeDate);
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
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public List<Master> getMasterSortByAlphabet() {
        log.debug("[getMasterSortByAlphabet]");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Master> criteriaQuery = criteriaBuilder.createQuery(Master.class);
        Root<Master> masterRoot = criteriaQuery.from(Master.class);
        criteriaQuery.select(masterRoot).distinct(true);
        criteriaQuery.orderBy(criteriaBuilder.asc(masterRoot.get(Master_.name)));
        TypedQuery<Master> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<Master> getMasterSortByBusy() {
        log.debug("[getMasterSortByBusy]");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Master> criteriaQuery = criteriaBuilder.createQuery(Master.class);
        Root<Master> masterRoot = criteriaQuery.from(Master.class);
        criteriaQuery.select(masterRoot).distinct(true);
        criteriaQuery.orderBy(criteriaBuilder.desc(masterRoot.get(Master_.numberOrders)));
        TypedQuery<Master> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public Long getNumberMasters() {
        log.debug("[getNumberMasters]");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Master> masterRoot = criteriaQuery.from(Master.class);
        criteriaQuery.select(criteriaBuilder.count(masterRoot));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public List<Order> getMasterOrders(Master master) {
        log.debug("[getMasterOrders]");
        log.trace("[master: {}]", master);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        Join<Order, Master> masterOrderJoin = orderRoot.join(Order_.masters);
        criteriaQuery.select(orderRoot).distinct(true);
        criteriaQuery.where(criteriaBuilder.equal(masterOrderJoin.get(Master_.id), master.getId()));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

}
