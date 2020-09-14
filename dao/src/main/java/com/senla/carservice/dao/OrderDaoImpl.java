package com.senla.carservice.dao;

import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Master_;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Order_;
import com.senla.carservice.domain.enumaration.StatusOrder;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

@Repository
public class OrderDaoImpl extends AbstractDao<Order, Long> implements OrderDao {

    public OrderDaoImpl() {
    }

    @Override
    public Order getLastOrder() {
        LOGGER.debug("Method getLastOrder");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot).distinct(true);
        criteriaQuery.orderBy(criteriaBuilder.desc(orderRoot.get(Master_.id)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setMaxResults(1);
        Order order = typedQuery.getSingleResult();
        if (order == null) {
            throw new DaoException("Error getting last order");
        }
        return order;
    }

    @Override
    public Long getNumberBusyMasters(Date startPeriod, Date endPeriod) {
        LOGGER.debug("Method getNumberBusyMasters");
        LOGGER.trace("Parameter startPeriod: {}", startPeriod);
        LOGGER.trace("Parameter endPeriod: {}", endPeriod);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Master> orderRoot = criteriaQuery.from(Master.class);
        Join<Master, Order> orderJoin = orderRoot.join(Master_.orders);
        criteriaQuery.distinct(true);
        criteriaQuery.select(criteriaBuilder.count(orderRoot.get(Master_.id)))
            .where(criteriaBuilder.between(orderJoin.get(Order_.leadTime), startPeriod, endPeriod));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public Long getNumberBusyPlaces(Date startPeriod, Date endPeriod) {
        LOGGER.debug("Method getNumberBusyPlaces");
        LOGGER.trace("Parameter startPeriod: {}", startPeriod);
        LOGGER.trace("Parameter endPeriod: {}", endPeriod);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.distinct(true);
        criteriaQuery.select(criteriaBuilder.count(orderRoot.get(Order_.place)))
            .where(criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriod, endPeriod));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public List<Order> getOrdersSortByFilingDate() {
        LOGGER.debug("Method getOrdersSortByFilingDate");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.creationTime)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders.isEmpty()) {
            throw new DaoException("Error getting sort orders by filing date");
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersSortByExecutionDate() {
        LOGGER.debug("Method getOrdersSortByExecutionDate");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.leadTime)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders.isEmpty()) {
            throw new DaoException("Error getting sort orders by executing date");
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersSortByPlannedStartDate() {
        LOGGER.debug("Method getOrdersSortByPlannedStartDate");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.executionStartTime)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders.isEmpty()) {
            throw new DaoException("Error getting sort orders by planned start date");
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersSortByPrice() {
        LOGGER.debug("Method getOrdersSortByPrice");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.price)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders.isEmpty()) {
            throw new DaoException("Error getting sort orders by price");
        }
        return orders;
    }

    @Override
    public List<Order> getExecuteOrderSortByFilingDate() {
        LOGGER.debug("Method getExecuteOrderSortByFilingDate");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        criteriaQuery.where(criteriaBuilder.equal(orderRoot.get(Order_.status), StatusOrder.PERFORM));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.creationTime)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders.isEmpty()) {
            throw new DaoException("Error getting execute orders sort by price");
        }
        return orders;
    }

    @Override
    public List<Order> getExecuteOrderSortExecutionDate() {
        LOGGER.debug("Method getExecuteOrderSortByFilingDate");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        criteriaQuery.where(criteriaBuilder.equal(orderRoot.get(Order_.status), StatusOrder.PERFORM));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.leadTime)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders.isEmpty()) {
            throw new DaoException("Error getting execute orders sort by execution date");
        }
        return orders;
    }

    @Override
    public List<Order> getCompletedOrdersSortByFilingDate(Date startPeriodDate, Date endPeriodDate) {
        LOGGER.debug("Method getCompletedOrdersSortByFilingDate");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        Predicate predicateTime =
            criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriodDate, endPeriodDate);
        Predicate predicateStatus = criteriaBuilder.equal(orderRoot.get(Order_.status), StatusOrder.COMPLETED);
        criteriaQuery.where(criteriaBuilder.and(predicateStatus, predicateTime));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.creationTime)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders.isEmpty()) {
            throw new DaoException("Error getting completed orders sort by creation date in period of time");
        }
        return orders;
    }

    @Override
    public List<Order> getCompletedOrdersSortByExecutionDate(Date startPeriodDate, Date endPeriodDate) {
        LOGGER.debug("Method getCompletedOrdersSortByExecutionDate");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        Predicate predicateTime =
            criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriodDate, endPeriodDate);
        Predicate predicateStatus = criteriaBuilder.equal(orderRoot.get(Order_.status), StatusOrder.COMPLETED);
        criteriaQuery.where(criteriaBuilder.and(predicateStatus, predicateTime));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.leadTime)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders.isEmpty()) {
            throw new DaoException("Error getting completed orders sort by execution date in period of time");
        }
        return orders;
    }

    @Override
    public List<Order> getCompletedOrdersSortByPrice(Date startPeriodDate, Date endPeriodDate) {
        LOGGER.debug("Method getCompletedOrdersSortByPrice");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        Predicate predicateTime =
            criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriodDate, endPeriodDate);
        Predicate predicateStatus = criteriaBuilder.equal(orderRoot.get(Order_.status), StatusOrder.COMPLETED);
        criteriaQuery.where(criteriaBuilder.and(predicateStatus, predicateTime));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.price)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders.isEmpty()) {
            throw new DaoException("Error getting sort orders by price");
        }
        return orders;
    }

    @Override
    public List<Order> getCanceledOrdersSortByFilingDate(Date startPeriodDate, Date endPeriodDate) {
        LOGGER.debug("Method getCanceledOrdersSortByFilingDate");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        Predicate predicateTime =
            criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriodDate, endPeriodDate);
        Predicate predicateStatus = criteriaBuilder.equal(orderRoot.get(Order_.status), StatusOrder.CANCELED);
        criteriaQuery.where(criteriaBuilder.and(predicateStatus, predicateTime));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.creationTime)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders.isEmpty()) {
            throw new DaoException("Error getting sort orders by price");
        }
        return orders;
    }

    @Override
    public List<Order> getCanceledOrdersSortByExecutionDate(Date startPeriodDate, Date endPeriodDate) {
        LOGGER.debug("Method getCanceledOrdersSortByExecutionDate");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        Predicate predicateTime =
            criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriodDate, endPeriodDate);
        Predicate predicateStatus = criteriaBuilder.equal(orderRoot.get(Order_.status), StatusOrder.CANCELED);
        criteriaQuery.where(criteriaBuilder.and(predicateStatus, predicateTime));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.leadTime)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders.isEmpty()) {
            throw new DaoException("Error getting sort orders by price");
        }
        return orders;
    }

    @Override
    public List<Order> getCanceledOrdersSortByPrice(Date startPeriodDate, Date endPeriodDate) {
        LOGGER.debug("Method getCanceledOrdersSortByPrice");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        Predicate predicateTime =
            criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriodDate, endPeriodDate);
        Predicate predicateStatus = criteriaBuilder.equal(orderRoot.get(Order_.status), StatusOrder.CANCELED);
        criteriaQuery.where(criteriaBuilder.and(predicateStatus, predicateTime));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.price)));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders.isEmpty()) {
            throw new DaoException("Error getting sort orders by price");
        }
        return orders;
    }

    @Override
    public List<Order> getDeletedOrdersSortByFilingDate(Date startPeriodDate, Date endPeriodDate) {
        LOGGER.debug("Method getDeletedOrdersSortByFilingDate");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
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
        List<Order> orders = typedQuery.getResultList();
        if (orders.isEmpty()) {
            throw new DaoException("Error getting sort orders by price");
        }
        return orders;
    }

    @Override
    public List<Order> getDeletedOrdersSortByExecutionDate(Date startPeriodDate, Date endPeriodDate) {
        LOGGER.debug("Method getDeletedOrdersSortByExecutionDate");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
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
        List<Order> orders = typedQuery.getResultList();
        if (orders.isEmpty()) {
            throw new DaoException("Error getting sort orders by price");
        }
        return orders;
    }

    @Override
    public List<Order> getDeletedOrdersSortByPrice(Date startPeriodDate, Date endPeriodDate) {
        LOGGER.debug("Method getDeletedOrdersSortByPrice");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
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
        List<Order> orders = typedQuery.getResultList();
        if (orders.isEmpty()) {
            throw new DaoException("Error getting sort orders by price");
        }
        return orders;
    }

    @Override
    public List<Order> getMasterOrders(Master master) {
        LOGGER.debug("Method getMasterOrders");
        LOGGER.trace("Parameter master: {}", master);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        Join<Order, Master> masterOrderJoin = orderRoot.join(Order_.masters);
        criteriaQuery.select(orderRoot).distinct(true);
        criteriaQuery.where(criteriaBuilder.equal(masterOrderJoin.get(Master_.id), master.getId()));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders.isEmpty()) {
            throw new DaoException("Error getting master orders");
        }
        return orders;
    }

    @Override
    public Long getNumberOrders() {
        LOGGER.debug("Method getNumberOrders");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(criteriaBuilder.count(orderRoot));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public List<Master> getOrderMasters(Order order) {
        LOGGER.debug("Method getOrderById");
        LOGGER.trace("Parameter order: {}", order);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Master> criteriaQuery = criteriaBuilder.createQuery(Master.class);
        Root<Master> masterRoot = criteriaQuery.from(Master.class);
        Join<Master, Order> masterOrderJoin = masterRoot.join(Master_.orders);
        criteriaQuery.select(masterRoot);
        criteriaQuery.where(criteriaBuilder.equal(masterOrderJoin.get(Order_.id), order.getId()));
        TypedQuery<Master> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Master> masters = typedQuery.getResultList();
        if (masters.isEmpty()) {
            throw new DaoException("Error getting master orders");
        }
        return masters;
    }
}