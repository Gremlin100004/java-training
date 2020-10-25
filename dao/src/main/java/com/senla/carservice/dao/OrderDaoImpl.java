package com.senla.carservice.dao;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Master_;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Order_;
import com.senla.carservice.domain.enumaration.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Repository
@Slf4j
public class OrderDaoImpl extends AbstractDao<Order, Long> implements OrderDao {

    public OrderDaoImpl() {
        setType(Order.class);
    }

    @Override
    public Order getLastOrder() {
        log.debug("[getLastOrder]");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot).distinct(true);
        criteriaQuery.orderBy(criteriaBuilder.desc(orderRoot.get(Master_.id)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setMaxResults(1);
        List<Order> order = typedQuery.getResultList();
        if (order.isEmpty()) {
            return null;
        }
        return order.get(0);
    }

    @Override
    public List<Order> getOrdersSortByFilingDate() {
        log.debug("[getOrdersSortByFilingDate]");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.creationTime)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<Order> getOrdersSortByExecutionDate() {
        log.debug("[getOrdersSortByExecutionDate]");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.leadTime)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<Order> getOrdersSortByPlannedStartDate() {
        log.debug("[getOrdersSortByPlannedStartDate]");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.executionStartTime)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<Order> getOrdersSortByPrice() {
        log.debug("[getOrdersSortByPrice]");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.price)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<Order> getExecuteOrderSortByFilingDate() {
        log.debug("[getExecuteOrderSortByFilingDate]");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        criteriaQuery.where(criteriaBuilder.equal(orderRoot.get(Order_.status), OrderStatus.PERFORM));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.creationTime)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<Order> getExecuteOrderSortByExecutionDate() {
        log.debug("[getExecuteOrderSortByFilingDate]");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        criteriaQuery.where(criteriaBuilder.equal(orderRoot.get(Order_.status), OrderStatus.PERFORM));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.leadTime)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<Order> getExecuteOrderSortByPrice() {
        log.debug("[getExecuteOrderSortByPrice]");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        criteriaQuery.where(criteriaBuilder.equal(orderRoot.get(Order_.status), OrderStatus.PERFORM));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.price)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<Order> getCompletedOrdersSortByFilingDate(Date startPeriodDate, Date endPeriodDate) {
        log.debug("[getCompletedOrdersSortByFilingDate]");
        log.trace("[startPeriodDate: {}] [endPeriodDate: {}]", startPeriodDate, endPeriodDate);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        Predicate predicateTime =
            criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriodDate, endPeriodDate);
        Predicate predicateStatus = criteriaBuilder.equal(orderRoot.get(Order_.status), OrderStatus.COMPLETED);
        criteriaQuery.where(criteriaBuilder.and(predicateStatus, predicateTime));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.creationTime)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<Order> getCompletedOrdersSortByExecutionDate(Date startPeriodDate, Date endPeriodDate) {
        log.debug("[getCompletedOrdersSortByExecutionDate]");
        log.trace("[startPeriodDate: {}] [endPeriodDate: {}]", startPeriodDate, endPeriodDate);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        Predicate predicateTime =
            criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriodDate, endPeriodDate);
        Predicate predicateStatus = criteriaBuilder.equal(orderRoot.get(Order_.status), OrderStatus.COMPLETED);
        criteriaQuery.where(criteriaBuilder.and(predicateStatus, predicateTime));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.leadTime)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<Order> getCompletedOrdersSortByPrice(Date startPeriodDate, Date endPeriodDate) {
        log.debug("[getCompletedOrdersSortByPrice]");
        log.trace("[startPeriodDate: {}] [endPeriodDate: {}]", startPeriodDate, endPeriodDate);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        Predicate predicateTime =
            criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriodDate, endPeriodDate);
        Predicate predicateStatus = criteriaBuilder.equal(orderRoot.get(Order_.status), OrderStatus.COMPLETED);
        criteriaQuery.where(criteriaBuilder.and(predicateStatus, predicateTime));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.price)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<Order> getCanceledOrdersSortByFilingDate(Date startPeriodDate, Date endPeriodDate) {
        log.debug("[getCanceledOrdersSortByFilingDate]");
        log.trace("[startPeriodDate: {}] [endPeriodDate: {}]", startPeriodDate, endPeriodDate);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        Predicate predicateTime =
            criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriodDate, endPeriodDate);
        Predicate predicateStatus = criteriaBuilder.equal(orderRoot.get(Order_.status), OrderStatus.CANCELED);
        criteriaQuery.where(criteriaBuilder.and(predicateStatus, predicateTime));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.creationTime)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<Order> getCanceledOrdersSortByExecutionDate(Date startPeriodDate, Date endPeriodDate) {
        log.debug("[getCanceledOrdersSortByExecutionDate]");
        log.trace("[startPeriodDate: {}] [endPeriodDate: {}]", startPeriodDate, endPeriodDate);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        Predicate predicateTime =
            criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriodDate, endPeriodDate);
        Predicate predicateStatus = criteriaBuilder.equal(orderRoot.get(Order_.status), OrderStatus.CANCELED);
        criteriaQuery.where(criteriaBuilder.and(predicateStatus, predicateTime));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.leadTime)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<Order> getCanceledOrdersSortByPrice(Date startPeriodDate, Date endPeriodDate) {
        log.debug("[getCanceledOrdersSortByPrice]");
        log.trace("[startPeriodDate: {}] [endPeriodDate: {}]", startPeriodDate, endPeriodDate);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        Predicate predicateTime =
            criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriodDate, endPeriodDate);
        Predicate predicateStatus = criteriaBuilder.equal(orderRoot.get(Order_.status), OrderStatus.CANCELED);
        criteriaQuery.where(criteriaBuilder.and(predicateStatus, predicateTime));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.price)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<Order> getDeletedOrdersSortByFilingDate(Date startPeriodDate, Date endPeriodDate) {
        log.debug("[getDeletedOrdersSortByFilingDate]");
        log.trace("[startPeriodDate: {}] [endPeriodDate: {}]", startPeriodDate, endPeriodDate);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        Predicate predicateTime =
            criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriodDate, endPeriodDate);
        Predicate predicateStatus = criteriaBuilder.equal(orderRoot.get(Order_.deleteStatus), true);
        criteriaQuery.where(criteriaBuilder.and(predicateStatus, predicateTime));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.creationTime)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<Order> getDeletedOrdersSortByExecutionDate(Date startPeriodDate, Date endPeriodDate) {
        log.debug("[getDeletedOrdersSortByExecutionDate]");
        log.trace("[startPeriodDate: {}] [endPeriodDate: {}]", startPeriodDate, endPeriodDate);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        Predicate predicateTime =
            criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriodDate, endPeriodDate);
        Predicate predicateStatus = criteriaBuilder.equal(orderRoot.get(Order_.deleteStatus), true);
        criteriaQuery.where(criteriaBuilder.and(predicateStatus, predicateTime));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.leadTime)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<Order> getDeletedOrdersSortByPrice(Date startPeriodDate, Date endPeriodDate) {
        log.debug("[getDeletedOrdersSortByPrice]");
        log.trace("[startPeriodDate: {}] [endPeriodDate: {}]", startPeriodDate, endPeriodDate);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        Predicate predicateTime =
            criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriodDate, endPeriodDate);
        Predicate predicateStatus = criteriaBuilder.equal(orderRoot.get(Order_.deleteStatus), true);
        criteriaQuery.where(criteriaBuilder.and(predicateStatus, predicateTime));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.price)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public Long getNumberOrders() {
        log.debug("[getNumberOrders]");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(criteriaBuilder.count(orderRoot));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public List<Master> getOrderMasters(Order order) {
        log.debug("[getOrderById]");
        log.trace("[order: {}]", order);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Master> criteriaQuery = criteriaBuilder.createQuery(Master.class);
        Root<Master> masterRoot = criteriaQuery.from(Master.class);
        Join<Master, Order> masterOrderJoin = masterRoot.join(Master_.orders);
        criteriaQuery.select(masterRoot);
        criteriaQuery.where(criteriaBuilder.equal(masterOrderJoin.get(Order_.id), order.getId()));
        TypedQuery<Master> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

}
