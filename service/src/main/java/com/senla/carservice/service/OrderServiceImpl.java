package com.senla.carservice.service;

import com.senla.carservice.util.DateUtil;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.container.objectadjuster.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.service.enumaration.SortParameter;
import com.senla.carservice.domain.enumaration.StatusOrder;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.hibernatedao.MasterDao;
import com.senla.carservice.hibernatedao.OrderDao;
import com.senla.carservice.hibernatedao.PlaceDao;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Singleton
public class OrderServiceImpl implements OrderService {
    @Dependency
    private OrderDao orderDao;
    @Dependency
    private PlaceDao placeDao;
    @Dependency
    private MasterDao masterDao;
    @ConfigProperty
    private boolean isBlockShiftTime;
    @ConfigProperty
    private boolean isBlockDeleteOrder;

    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class);

    public OrderServiceImpl() {
    }

    @Override
    public List<Order> getOrders() {
        LOGGER.debug("Method getOrders");
        Session session = orderDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<Order> orders = orderDao.getAllRecords(Order.class);
            if (orders.isEmpty()) {
                throw new BusinessException("There are no orders");
            }
            transaction.commit();
            return orders;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction get orders");
        }
    }

    @Override
    public void addOrder(String automaker, String model, String registrationNumber) {
        LOGGER.debug("Method addOrder");
        LOGGER.debug("Parameter automaker: " + automaker);
        LOGGER.debug("Parameter model: " + model);
        LOGGER.debug("Parameter registrationNumber: " + registrationNumber);
        Session session = orderDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            checkMasters();
            checkPlaces();
            Order order = new Order(automaker, model, registrationNumber);
            Place place = placeDao.getPlaceById((long) 1);
            order.setPlace(place);
            orderDao.saveRecord(order);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction add order");
        }
    }

    @Override
    public void addOrderDeadlines(Date executionStartTime, Date leadTime) {
        LOGGER.debug("Method addOrderDeadlines");
        LOGGER.debug("Parameter executionStartTime: " + executionStartTime);
        LOGGER.debug("Parameter leadTime: " + leadTime);
        Session session = orderDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            DateUtil.checkDateTime(executionStartTime, leadTime, false);
            Order currentOrder = orderDao.getLastOrder();
            if (currentOrder == null) {
                throw new BusinessException("There are no orders");
            }
            long numberFreeMasters = masterDao.getNumberMasters() - orderDao
                .getNumberBusyMasters(executionStartTime, leadTime);
            long numberFreePlace = placeDao.getNumberPlaces() - orderDao
                .getNumberBusyPlaces(executionStartTime, leadTime);
            if (numberFreeMasters == 0) {
                throw new BusinessException("The number of masters is zero");
            }
            if (numberFreePlace == 0) {
                throw new BusinessException("The number of places is zero");
            }
            currentOrder.setExecutionStartTime(executionStartTime);
            currentOrder.setLeadTime(leadTime);
            orderDao.updateRecord(currentOrder);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction add dead line to order");
        }
    }

    @Override
    public void addOrderMasters(int index) {
        LOGGER.debug("Method addOrderMasters");
        LOGGER.debug("Parameter index: " + index);
        Session session = orderDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Order currentOrder = orderDao.getLastOrder();
            Master master = masterDao.getAllRecords(Master.class).get(index);
            master.setNumberOrders(master.getNumberOrders() + 1);
            if (currentOrder == null) {
                throw new BusinessException("There are no orders");
            }
            if (master.getDeleteStatus()) {
                throw new BusinessException("Master has been deleted");
            }
            for (Master orderMaster : currentOrder.getMasters()) {
                if (orderMaster.equals(master)) {
                    throw new BusinessException("This master already exists");
                }
            }
            currentOrder.getMasters().add(master);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction add masters to order");
        }
    }

    @Override
    public void addOrderPlace(Place place) {
        LOGGER.debug("Method addOrderPlace");
        LOGGER.debug("Parameter place: " + place);
        Session session = orderDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Order currentOrder = orderDao.getLastOrder();
            if (currentOrder == null) {
                throw new BusinessException("There are no orders");
            }
            currentOrder.setPlace(place);
            orderDao.updateRecord(currentOrder);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction add place to order");
        }
    }

    @Override
    public void addOrderPrice(BigDecimal price) {
        LOGGER.debug("Method addOrderPrice");
        LOGGER.debug("Parameter price: " + price);
        Session session = orderDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Order currentOrder = orderDao.getLastOrder();
            if (currentOrder == null) {
                throw new BusinessException("There are no orders");
            }
            currentOrder.setPrice(price);
            orderDao.updateRecord(currentOrder);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction add price to order");
        }
    }

    @Override
    public void completeOrder(Order order) {
        LOGGER.debug("Method completeOrder");
        LOGGER.debug("Parameter order: " + order);
        Session session = orderDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            checkStatusOrder(order);
            order.setStatus(StatusOrder.PERFORM);
            order.setExecutionStartTime(new Date());
            order.getPlace().setBusy(true);
            orderDao.updateRecord(order);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction transfer order to execution status");
        }
    }

    @Override
    public void cancelOrder(Order order) {
        LOGGER.debug("Method cancelOrder");
        LOGGER.debug("Parameter order: " + order);
        Session session = orderDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            checkStatusOrder(order);
            order.setLeadTime(new Date());
            order.setStatus(StatusOrder.CANCELED);
            List<Master> masters = orderDao.getOrderMasters(order);
            masters.forEach(master -> master.setNumberOrders(master.getNumberOrders() - 1));
            masterDao.updateAllRecords(masters);
            orderDao.updateRecord(order);
            Place place = order.getPlace();
            place.setBusy(false);
            placeDao.updateRecord(place);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction cancel order");
        }
    }

    @Override
    public void closeOrder(Order order) {
        LOGGER.debug("Method closeOrder");
        LOGGER.debug("Parameter order: " + order);
        Session session = orderDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            checkStatusOrder(order);
            order.setLeadTime(new Date());
            order.setStatus(StatusOrder.COMPLETED);
            orderDao.updateRecord(order);
            Place place = order.getPlace();
            place.setBusy(false);
            placeDao.updateRecord(place);
            List<Master> masters = orderDao.getOrderMasters(order);
            masters.forEach(master -> master.setNumberOrders(master.getNumberOrders() - 1));
            masterDao.updateAllRecords(masters);
            orderDao.updateRecord(order);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction close order");
        }
    }

    @Override
    public void deleteOrder(Order order) {
        LOGGER.debug("Method deleteOrder");
        LOGGER.debug("Parameter order: " + order);
        if (isBlockDeleteOrder) {
            throw new BusinessException("Permission denied");
        }
        Session session = orderDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            orderDao.updateRecord(order);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction get masters");
        }
    }

    @Override
    public void shiftLeadTime(Order order, Date executionStartTime, Date leadTime) {
        LOGGER.debug("Method shiftLeadTime");
        LOGGER.debug("Parameter order: " + order);
        LOGGER.debug("Parameter executionStartTime: " + executionStartTime);
        LOGGER.debug("Parameter leadTime: " + leadTime);
        if (isBlockShiftTime) {
            throw new BusinessException("Permission denied");
        }
        Session session = orderDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            DateUtil.checkDateTime(executionStartTime, leadTime, false);
            checkStatusOrderShiftTime(order);
            order.setLeadTime(leadTime);
            order.setExecutionStartTime(executionStartTime);
            orderDao.updateRecord(order);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction shift lead time");
        }
    }

    @Override
    public List<Order> getSortOrders(SortParameter sortParameter) {
        LOGGER.debug("Method getSortOrders");
        LOGGER.debug("Parameter sortParameter: " + sortParameter);
        Session session = orderDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<Order> orders = new ArrayList<>();
            if (sortParameter.equals(SortParameter.SORT_BY_FILING_DATE)) {
                orders = orderDao.getOrdersSortByFilingDate();
            } else if (sortParameter.equals(SortParameter.SORT_BY_EXECUTION_DATE)) {
                orders = orderDao.getOrdersSortByExecutionDate();
            } else if (sortParameter.equals(SortParameter.BY_PLANNED_START_DATE)) {
                orders = orderDao.getOrdersSortByPlannedStartDate();
            } else if (sortParameter.equals(SortParameter.SORT_BY_PRICE)) {
                orders = orderDao.getOrdersSortByPrice();
            } else if (sortParameter.equals(SortParameter.EXECUTE_ORDER_SORT_BY_FILING_DATE)) {
                orders = orderDao.getExecuteOrderSortByFilingDate();
            } else if (sortParameter.equals(SortParameter.EXECUTE_ORDER_SORT_BY_EXECUTION_DATE)) {
                orders = orderDao.getExecuteOrderSortExecutionDate();
            }
            if (orders.isEmpty()) {
                throw new BusinessException("There are no orders");
            }
            transaction.commit();
            return orders;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction get sort orders");
        }
    }

    @Override
    public List<Order> getSortOrdersByPeriod(Date startPeriodDate, Date endPeriodDate, SortParameter sortParameter) {
        LOGGER.debug("Method getSortOrdersByPeriod");
        LOGGER.debug("Parameter startPeriodDate: " + startPeriodDate);
        LOGGER.debug("Parameter endPeriodDate: " + endPeriodDate);
        LOGGER.debug("Parameter sortParameter: " + sortParameter);
        Session session = orderDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<Order> orders = new ArrayList<>();
            DateUtil.checkDateTime(startPeriodDate, endPeriodDate, true);
            if (sortParameter.equals(SortParameter.COMPLETED_ORDERS_SORT_BY_FILING_DATE)) {
                orders = orderDao.getCompletedOrdersSortByFilingDate(startPeriodDate, endPeriodDate);
            } else if (sortParameter.equals(SortParameter.COMPLETED_ORDERS_SORT_BY_EXECUTION_DATE)) {
                orders = orderDao.getCompletedOrdersSortByExecutionDate(startPeriodDate, endPeriodDate);
            } else if (sortParameter.equals(SortParameter.COMPLETED_ORDERS_SORT_BY_PRICE)) {
                orders = orderDao
                    .getCompletedOrdersSortByPrice(startPeriodDate, endPeriodDate);
            } else if (sortParameter.equals(SortParameter.CANCELED_ORDERS_SORT_BY_FILING_DATE)) {
                orders = orderDao.getCanceledOrdersSortByFilingDate(startPeriodDate, endPeriodDate);
            } else if (sortParameter.equals(SortParameter.CANCELED_ORDERS_SORT_BY_EXECUTION_DATE)) {
                orders = orderDao.getCanceledOrdersSortByExecutionDate(startPeriodDate, endPeriodDate);
            } else if (sortParameter.equals(SortParameter.CANCELED_ORDERS_SORT_BY_PRICE)) {
                orders = orderDao
                    .getCanceledOrdersSortByPrice(startPeriodDate, endPeriodDate);
            } else if (sortParameter.equals(SortParameter.DELETED_ORDERS_SORT_BY_FILING_DATE)) {
                orders = orderDao
                    .getDeletedOrdersSortByFilingDate(startPeriodDate, endPeriodDate);
            } else if (sortParameter.equals(SortParameter.DELETED_ORDERS_SORT_BY_EXECUTION_DATE)) {
                orders = orderDao.getDeletedOrdersSortByExecutionDate(startPeriodDate, endPeriodDate);
            } else if (sortParameter.equals(SortParameter.DELETED_ORDERS_SORT_BY_PRICE)) {
                orders = orderDao
                    .getDeletedOrdersSortByPrice(startPeriodDate, endPeriodDate);
            }
            if (orders.isEmpty()) {
                throw new BusinessException("There are no orders");
            }
            transaction.commit();
            return orders;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction get sort orders by date");
        }
    }

    @Override
    public List<Order> getMasterOrders(Master master) {
        LOGGER.debug("Method getMasterOrders");
        LOGGER.debug("Parameter master: " + master);
        Session session = orderDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<Order> orders = orderDao.getMasterOrders(master);
            if (orders.isEmpty()) {
                throw new BusinessException("Master doesn't have any orders");
            }
            transaction.commit();
            return orders;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction get master orders");
        }
    }

    @Override
    public List<Master> getOrderMasters(Order order) {
        LOGGER.debug("Method getOrderMasters");
        LOGGER.debug("Parameter order: " + order);
        Session session = orderDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<Master> masters = orderDao.getOrderMasters(order);
            if (masters.isEmpty()) {
                throw new BusinessException("There are no masters in order");
            }
            transaction.commit();
            return masters;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction order masters");
        }
    }

    @Override
    public Long getNumberOrders() {
        LOGGER.debug("Method getNumberOrders");
        Session session = orderDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Long numberOrders = orderDao.getNumberOrders();
            transaction.commit();
            return numberOrders;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction get number orders");
        }
    }

    private void checkMasters() {
        LOGGER.debug("Method checkMasters");
        if (masterDao.getNumberMasters() == 0) {
            throw new BusinessException("There are no masters");
        }
    }

    private void checkPlaces() {
        LOGGER.debug("Method checkPlaces");
        if (placeDao.getNumberPlaces() == 0) {
            throw new BusinessException("There are no places");
        }
    }

    private void checkStatusOrder(Order order) {
        LOGGER.debug("Method checkStatusOrder");
        LOGGER.debug("Parameter order: " + order);
        if (order.isDeleteStatus()) {
            throw new BusinessException("The order has been deleted");
        }
        if (order.getStatus() == StatusOrder.COMPLETED) {
            throw new BusinessException("The order has been completed");
        }
        if (order.getStatus() == StatusOrder.PERFORM) {
            throw new BusinessException("Order is being executed");
        }
        if (order.getStatus() == StatusOrder.CANCELED) {
            throw new BusinessException("The order has been canceled");
        }
    }

    private void checkStatusOrderShiftTime(Order order) {
        LOGGER.debug("Method checkStatusOrderShiftTime");
        LOGGER.debug("Parameter order: " + order);
        if (order.isDeleteStatus()) {
            throw new BusinessException("The order has been deleted");
        }
        if (order.getStatus() == StatusOrder.COMPLETED) {
            throw new BusinessException("The order has been completed");
        }
        if (order.getStatus() == StatusOrder.CANCELED) {
            throw new BusinessException("The order has been canceled");
        }
    }
}