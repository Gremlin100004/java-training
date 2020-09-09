package com.senla.carservice.hibernatedao;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Master_;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Order_;
import com.senla.carservice.domain.enumaration.StatusOrder;
import com.senla.carservice.hibernatedao.exception.DaoException;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Singleton
public class OrderDaoImpl extends AbstractDao<Order, Long> implements OrderDao {

    public OrderDaoImpl() {
    }

    @Override
    public Order getLastOrder() {
        LOGGER.debug("Method getLastOrder");
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot).distinct(true);
        criteriaQuery.orderBy(criteriaBuilder.desc(orderRoot.get(Master_.id)));
        TypedQuery<Order> typedQuery = session.createQuery(criteriaQuery);
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
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Master> orderRoot = criteriaQuery.from(Master.class);
        Join<Master, Order> orderJoin = orderRoot.join(Master_.orders);
        criteriaQuery.distinct(true);
        criteriaQuery.select(criteriaBuilder.count(orderRoot.get(Master_.id)))
                .where(criteriaBuilder.between(orderJoin.get(Order_.leadTime), startPeriod, endPeriod));
        return session.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public Long getNumberBusyPlaces(Date startPeriod, Date endPeriod) {
        LOGGER.debug("Method getNumberBusyPlaces");
        LOGGER.trace("Parameter startPeriod: {}", startPeriod);
        LOGGER.trace("Parameter endPeriod: {}", endPeriod);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.distinct(true);
        criteriaQuery.select(criteriaBuilder.count(orderRoot.get(Order_.place)))
                .where(criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriod, endPeriod));
        return session.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public List<Order> getOrdersSortByFilingDate() {
        LOGGER.debug("Method getOrdersSortByFilingDate");
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.creationTime)));
        TypedQuery<Order> typedQuery = session.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders == null) {
            throw new DaoException("Error getting sort orders by filing date");
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersSortByExecutionDate() {
        LOGGER.debug("Method getOrdersSortByExecutionDate");
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.leadTime)));
        TypedQuery<Order> typedQuery = session.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders == null) {
            throw new DaoException("Error getting sort orders by executing date");
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersSortByPlannedStartDate() {
        LOGGER.debug("Method getOrdersSortByPlannedStartDate");
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.executionStartTime)));
        TypedQuery<Order> typedQuery = session.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders == null) {
            throw new DaoException("Error getting sort orders by planned start date");
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersSortByPrice() {
        LOGGER.debug("Method getOrdersSortByPrice");
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.price)));
        TypedQuery<Order> typedQuery = session.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders == null) {
            throw new DaoException("Error getting sort orders by price");
        }
        return orders;
    }

    @Override
    public List<Order> getExecuteOrderSortByFilingDate() {
        LOGGER.debug("Method getExecuteOrderSortByFilingDate");
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        criteriaQuery.where(criteriaBuilder.equal(orderRoot.get(Order_.status), StatusOrder.PERFORM));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.creationTime)));
        TypedQuery<Order> typedQuery = session.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders == null) {
            throw new DaoException("Error getting execute orders sort by price");
        }
        return orders;
    }

    @Override
    public List<Order> getExecuteOrderSortExecutionDate() {
        LOGGER.debug("Method getExecuteOrderSortByFilingDate");
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        criteriaQuery.where(criteriaBuilder.equal(orderRoot.get(Order_.status), StatusOrder.PERFORM));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.leadTime)));
        TypedQuery<Order> typedQuery = session.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders == null) {
            throw new DaoException("Error getting execute orders sort by execution date");
        }
        return orders;
    }

    @Override
    public List<Order> getCompletedOrdersSortByFilingDate(Date startPeriodDate, Date endPeriodDate) {
        LOGGER.debug("Method getCompletedOrdersSortByFilingDate");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        Predicate predicateTime = criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriodDate, endPeriodDate);
        Predicate predicateStatus = criteriaBuilder.equal(orderRoot.get(Order_.status), StatusOrder.COMPLETED);
        criteriaQuery.where(criteriaBuilder.and(predicateStatus, predicateTime));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.creationTime)));
        TypedQuery<Order> typedQuery = session.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders == null) {
            throw new DaoException("Error getting completed orders sort by creation date in period of time");
        }
        return orders;
    }

    @Override
    public List<Order> getCompletedOrdersSortByExecutionDate(Date startPeriodDate, Date endPeriodDate) {
        LOGGER.debug("Method getCompletedOrdersSortByExecutionDate");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        Predicate predicateTime = criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriodDate, endPeriodDate);
        Predicate predicateStatus = criteriaBuilder.equal(orderRoot.get(Order_.status), StatusOrder.COMPLETED);
        criteriaQuery.where(criteriaBuilder.and(predicateStatus, predicateTime));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.leadTime)));
        TypedQuery<Order> typedQuery = session.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders == null) {
            throw new DaoException("Error getting completed orders sort by execution date in period of time");
        }
        return orders;
    }

    @Override
    public List<Order> getCompletedOrdersSortByPrice(Date startPeriodDate, Date endPeriodDate) {
        LOGGER.debug("Method getCompletedOrdersSortByPrice");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        Predicate predicateTime = criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriodDate, endPeriodDate);
        Predicate predicateStatus = criteriaBuilder.equal(orderRoot.get(Order_.status), StatusOrder.COMPLETED);
        criteriaQuery.where(criteriaBuilder.and(predicateStatus, predicateTime));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.price)));
        TypedQuery<Order> typedQuery = session.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders == null) {
            throw new DaoException("Error getting sort orders by price");
        }
        return orders;
    }

    @Override
    public List<Order> getCanceledOrdersSortByFilingDate(Date startPeriodDate, Date endPeriodDate) {
        LOGGER.debug("Method getCanceledOrdersSortByFilingDate");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        Predicate predicateTime = criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriodDate, endPeriodDate);
        Predicate predicateStatus = criteriaBuilder.equal(orderRoot.get(Order_.status), StatusOrder.CANCELED);
        criteriaQuery.where(criteriaBuilder.and(predicateStatus, predicateTime));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.creationTime)));
        TypedQuery<Order> typedQuery = session.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders == null) {
            throw new DaoException("Error getting sort orders by price");
        }
        return orders;
    }

    @Override
    public List<Order> getCanceledOrdersSortByExecutionDate(Date startPeriodDate, Date endPeriodDate) {
        LOGGER.debug("Method getCanceledOrdersSortByExecutionDate");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        Predicate predicateTime = criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriodDate, endPeriodDate);
        Predicate predicateStatus = criteriaBuilder.equal(orderRoot.get(Order_.status), StatusOrder.CANCELED);
        criteriaQuery.where(criteriaBuilder.and(predicateStatus, predicateTime));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.leadTime)));
        TypedQuery<Order> typedQuery = session.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders == null) {
            throw new DaoException("Error getting sort orders by price");
        }
        return orders;
    }

    @Override
    public List<Order> getCanceledOrdersSortByPrice(Date startPeriodDate, Date endPeriodDate) {
        LOGGER.debug("Method getCanceledOrdersSortByPrice");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        Predicate predicateTime = criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriodDate, endPeriodDate);
        Predicate predicateStatus = criteriaBuilder.equal(orderRoot.get(Order_.status), StatusOrder.CANCELED);
        criteriaQuery.where(criteriaBuilder.and(predicateStatus, predicateTime));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.price)));
        TypedQuery<Order> typedQuery = session.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders == null) {
            throw new DaoException("Error getting sort orders by price");
        }
        return orders;
    }

    @Override
    public List<Order> getDeletedOrdersSortByFilingDate(Date startPeriodDate, Date endPeriodDate) {
        LOGGER.debug("Method getDeletedOrdersSortByFilingDate");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        Predicate predicateTime = criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriodDate, endPeriodDate);
        Predicate predicateStatus = criteriaBuilder.equal(orderRoot.get(Order_.deleteStatus), true);
        criteriaQuery.where(criteriaBuilder.and(predicateStatus, predicateTime));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.creationTime)));
        TypedQuery<Order> typedQuery = session.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders == null) {
            throw new DaoException("Error getting sort orders by price");
        }
        return orders;
    }

    @Override
    public List<Order> getDeletedOrdersSortByExecutionDate(Date startPeriodDate, Date endPeriodDate) {
        LOGGER.debug("Method getDeletedOrdersSortByExecutionDate");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        Predicate predicateTime = criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriodDate, endPeriodDate);
        Predicate predicateStatus = criteriaBuilder.equal(orderRoot.get(Order_.deleteStatus), true);
        criteriaQuery.where(criteriaBuilder.and(predicateStatus, predicateTime));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.leadTime)));
        TypedQuery<Order> typedQuery = session.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders == null) {
            throw new DaoException("Error getting sort orders by price");
        }
        return orders;
    }

    @Override
    public List<Order> getDeletedOrdersSortByPrice(Date startPeriodDate, Date endPeriodDate) {
        LOGGER.debug("Method getDeletedOrdersSortByPrice");
        LOGGER.trace("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.trace("Parameter endPeriodDate: {}", endPeriodDate);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(orderRoot);
        Predicate predicateTime = criteriaBuilder.between(orderRoot.get(Order_.leadTime), startPeriodDate, endPeriodDate);
        Predicate predicateStatus = criteriaBuilder.equal(orderRoot.get(Order_.deleteStatus), true);
        criteriaQuery.where(criteriaBuilder.and(predicateStatus, predicateTime));
        criteriaQuery.orderBy(criteriaBuilder.asc(orderRoot.get(Order_.price)));
        TypedQuery<Order> typedQuery = session.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders == null) {
            throw new DaoException("Error getting sort orders by price");
        }
        return orders;
    }

    @Override
    public List<Order> getMasterOrders(Master master) {
        LOGGER.debug("Method getMasterOrders");
        LOGGER.trace("Parameter master: {}", master);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        Join<Order, Master> masterOrderJoin = orderRoot.join(Order_.masters); 
        criteriaQuery.select(orderRoot).distinct(true);
        criteriaQuery.where(criteriaBuilder.equal(masterOrderJoin.get(Master_.id), master.getId()));
        TypedQuery<Order> typedQuery = session.createQuery(criteriaQuery);
        List<Order> orders = typedQuery.getResultList();
        if (orders == null) {
            throw new DaoException("Error getting master orders");
        }
        return orders;
    }

    @Override
    public Long getNumberOrders() {
        LOGGER.debug("Method getNumberOrders");
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(criteriaBuilder.count(orderRoot));
        return session.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public Order getOrderById(Long id) {
        LOGGER.debug("Method getOrderById");
        LOGGER.trace("Parameter index: {}", id);
        Session session = sessionFactory.getCurrentSession();
        return session.get(Order.class, id);
    }

    @Override
    public List<Master> getOrderMasters(Order order) {
        LOGGER.debug("Method getOrderById");
        LOGGER.trace("Parameter order: {}", order);
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Master> criteriaQuery = criteriaBuilder.createQuery(Master.class);
        Root<Master> masterRoot = criteriaQuery.from(Master.class);
        Join<Master, Order> masterOrderJoin = masterRoot.join(Master_.orders);
        criteriaQuery.select(masterRoot);
        criteriaQuery.where(criteriaBuilder.equal(masterOrderJoin.get(Order_.id), order.getId()));
        TypedQuery<Master> typedQuery = session.createQuery(criteriaQuery);
        List<Master> masters = typedQuery.getResultList();
        if (masters == null) {
            throw new DaoException("Error getting master orders");
        }
        return masters;
    }
}