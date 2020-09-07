package com.senla.carservice.service;

import com.senla.carservice.DateUtil;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.container.objectadjuster.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.domain.enumaration.SortParameter;
import com.senla.carservice.domain.enumaration.StatusOrder;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.hibernatedao.MasterDao;
import com.senla.carservice.hibernatedao.OrderDao;
import com.senla.carservice.hibernatedao.PlaceDao;
import com.senla.carservice.hibernatedao.session.HibernateSessionFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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
    @Dependency
    private HibernateSessionFactory hibernateSessionFactory;
    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class);

    public OrderServiceImpl() {
    }

    @Override
    public List<Order> getOrders() {
        LOGGER.debug("Method getOrders");
        List<Order> orders = orderDao.getAllRecords(hibernateSessionFactory.getSession(), Order.class);
        if (orders.isEmpty()) {
            hibernateSessionFactory.closeSession();
            throw new BusinessException("There are no orders");
        }
        hibernateSessionFactory.closeSession();
        return orders;
    }

    @Override
    public void addOrder(String automaker, String model, String registrationNumber) {
        LOGGER.debug("Method addOrder");
        LOGGER.debug("Parameter automaker: " + automaker);
        LOGGER.debug("Parameter model: " + model);
        LOGGER.debug("Parameter registrationNumber: " + registrationNumber);
        try {
            hibernateSessionFactory.openTransaction();
            checkMasters();
            checkPlaces();
            Order order = new Order(automaker, model, registrationNumber);
            Place place = placeDao.getPlaceById((long) 1, hibernateSessionFactory.getSession());
            order.setPlace(place);
            orderDao.saveRecord(order, hibernateSessionFactory.getSession());
            hibernateSessionFactory.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            hibernateSessionFactory.rollBackTransaction();
            throw new BusinessException("Error transaction add order");
        } finally {
            hibernateSessionFactory.closeSession();
        }
    }

    @Override
    public void addOrderDeadlines(Date executionStartTime, Date leadTime) {
        LOGGER.debug("Method addOrderDeadlines");
        LOGGER.debug("Parameter executionStartTime: " + executionStartTime);
        LOGGER.debug("Parameter leadTime: " + leadTime);
        try {
            hibernateSessionFactory.openTransaction();
            DateUtil.checkDateTime(executionStartTime, leadTime, false);
            Order currentOrder = orderDao.getLastOrder(hibernateSessionFactory.getSession());
            if (currentOrder == null) {
                throw new BusinessException("There are no orders");
            }
            long numberFreeMasters = masterDao.getNumberMasters(hibernateSessionFactory.getSession()) - orderDao
                .getNumberBusyMasters(executionStartTime, leadTime, hibernateSessionFactory.getSession());
            long numberFreePlace = placeDao.getNumberPlaces(hibernateSessionFactory.getSession()) - orderDao
                .getNumberBusyPlaces(executionStartTime, leadTime, hibernateSessionFactory.getSession());
            if (numberFreeMasters == 0) {
                throw new BusinessException("The number of masters is zero");
            }
            if (numberFreePlace == 0) {
                throw new BusinessException("The number of places is zero");
            }
            currentOrder.setExecutionStartTime(executionStartTime);
            currentOrder.setLeadTime(leadTime);
            orderDao.updateRecord(currentOrder, hibernateSessionFactory.getSession());
            hibernateSessionFactory.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            hibernateSessionFactory.rollBackTransaction();
            throw new BusinessException("Error transaction add dead line to order");
        } finally {
            hibernateSessionFactory.closeSession();
        }
    }

    @Override
    public void addOrderMasters(int index) {
        LOGGER.debug("Method addOrderMasters");
        LOGGER.debug("Parameter index: " + index);
        try {
            hibernateSessionFactory.openTransaction();
            Order currentOrder = orderDao.getLastOrder(hibernateSessionFactory.getSession());
            Master master = masterDao.getAllRecords(hibernateSessionFactory.getSession(), Master.class).get(index);
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
            hibernateSessionFactory.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            hibernateSessionFactory.rollBackTransaction();
            throw new BusinessException("Error transaction add masters to order");
        } finally {
            hibernateSessionFactory.closeSession();
        }
    }

    @Override
    public void addOrderPlace(Place place) {
        LOGGER.debug("Method addOrderPlace");
        LOGGER.debug("Parameter place: " + place);
        try {
            hibernateSessionFactory.openTransaction();
            Order currentOrder = orderDao.getLastOrder(hibernateSessionFactory.getSession());
            if (currentOrder == null) {
                throw new BusinessException("There are no orders");
            }
            currentOrder.setPlace(place);
            orderDao.updateRecord(currentOrder, hibernateSessionFactory.getSession());
            hibernateSessionFactory.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            hibernateSessionFactory.rollBackTransaction();
            throw new BusinessException("Error transaction add place to order");
        } finally {
            hibernateSessionFactory.closeSession();
        }
    }

    @Override
    public void addOrderPrice(BigDecimal price) {
        LOGGER.debug("Method addOrderPrice");
        LOGGER.debug("Parameter price: " + price);
        try {
            hibernateSessionFactory.openTransaction();
            Order currentOrder = orderDao.getLastOrder(hibernateSessionFactory.getSession());
            if (currentOrder == null) {
                throw new BusinessException("There are no orders");
            }
            currentOrder.setPrice(price);
            orderDao.updateRecord(currentOrder, hibernateSessionFactory.getSession());
            hibernateSessionFactory.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            hibernateSessionFactory.rollBackTransaction();
            throw new BusinessException("Error transaction add price to order");
        } finally {
            hibernateSessionFactory.closeSession();
        }
    }

    @Override
    public void completeOrder(Order order) {
        LOGGER.debug("Method completeOrder");
        LOGGER.debug("Parameter order: " + order);
        try {
            hibernateSessionFactory.openTransaction();
            checkStatusOrder(order);
            order.setStatus(StatusOrder.PERFORM);
            order.setExecutionStartTime(new Date());
            order.getPlace().setBusy(true);
            orderDao.updateRecord(order, hibernateSessionFactory.getSession());
            hibernateSessionFactory.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            hibernateSessionFactory.rollBackTransaction();
            throw new BusinessException("Error transaction transfer order to execution status");
        } finally {
            hibernateSessionFactory.closeSession();
        }
    }

    @Override
    public void cancelOrder(Order order) {
        LOGGER.debug("Method cancelOrder");
        LOGGER.debug("Parameter order: " + order);
        try {
            hibernateSessionFactory.openTransaction();
            checkStatusOrder(order);
            order.setLeadTime(new Date());
            order.setStatus(StatusOrder.CANCELED);
            orderDao.updateRecord(order, hibernateSessionFactory.getSession());
            Place place = order.getPlace();
            place.setBusy(false);
            placeDao.updateRecord(place, hibernateSessionFactory.getSession());
            hibernateSessionFactory.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            hibernateSessionFactory.rollBackTransaction();
            throw new BusinessException("Error transaction cancel order");
        } finally {
            hibernateSessionFactory.closeSession();
        }
    }

    @Override
    public void closeOrder(Order order) {
        LOGGER.debug("Method closeOrder");
        LOGGER.debug("Parameter order: " + order);
        try {
            hibernateSessionFactory.openTransaction();
            checkStatusOrder(order);
            order.setLeadTime(new Date());
            order.setStatus(StatusOrder.COMPLETED);
            orderDao.updateRecord(order, hibernateSessionFactory.getSession());
            Place place = order.getPlace();
            place.setBusy(false);
            placeDao.updateRecord(place, hibernateSessionFactory.getSession());
            hibernateSessionFactory.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            hibernateSessionFactory.rollBackTransaction();
            throw new BusinessException("Error transaction close order");
        } finally {
            hibernateSessionFactory.closeSession();
        }
    }

    @Override
    public void deleteOrder(Order order) {
        LOGGER.debug("Method deleteOrder");
        LOGGER.debug("Parameter order: " + order);
        if (isBlockDeleteOrder) {
            throw new BusinessException("Permission denied");
        }
        try {
            hibernateSessionFactory.openTransaction();
            orderDao.updateRecord(order, hibernateSessionFactory.getSession());
            hibernateSessionFactory.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            hibernateSessionFactory.rollBackTransaction();
            throw new BusinessException("Error transaction get masters");
        } finally {
            hibernateSessionFactory.closeSession();
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
        try {
            hibernateSessionFactory.openTransaction();
            DateUtil.checkDateTime(executionStartTime, leadTime, false);
            checkStatusOrderShiftTime(order);
            order.setLeadTime(leadTime);
            order.setExecutionStartTime(executionStartTime);
            orderDao.updateRecord(order, hibernateSessionFactory.getSession());
            hibernateSessionFactory.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            hibernateSessionFactory.rollBackTransaction();
            throw new BusinessException("Error transaction shift lead time");
        } finally {
            hibernateSessionFactory.closeSession();
        }
    }

    @Override
    public List<Order> getSortOrders(SortParameter sortParameter) {
        LOGGER.debug("Method getSortOrders");
        LOGGER.debug("Parameter sortParameter: " + sortParameter);
        List<Order> orders = new ArrayList<>();
        if (sortParameter.equals(SortParameter.SORT_BY_FILING_DATE)) {
            orders = orderDao.getOrdersSortByFilingDate(hibernateSessionFactory.getSession());
        } else if (sortParameter.equals(SortParameter.SORT_BY_EXECUTION_DATE)) {
            orders = orderDao.getOrdersSortByExecutionDate(hibernateSessionFactory.getSession());
        } else if (sortParameter.equals(SortParameter.BY_PLANNED_START_DATE)) {
            orders = orderDao.getOrdersSortByPlannedStartDate(hibernateSessionFactory.getSession());
        } else if (sortParameter.equals(SortParameter.SORT_BY_PRICE)) {
            orders = orderDao.getOrdersSortByPrice(hibernateSessionFactory.getSession());
        } else if (sortParameter.equals(SortParameter.EXECUTE_ORDER_SORT_BY_FILING_DATE)) {
            orders = orderDao.getExecuteOrderSortByFilingDate(hibernateSessionFactory.getSession());
        } else if (sortParameter.equals(SortParameter.EXECUTE_ORDER_SORT_BY_EXECUTION_DATE)) {
            orders = orderDao.getExecuteOrderSortExecutionDate(hibernateSessionFactory.getSession());
        }
        if (orders.isEmpty()) {
            throw new BusinessException("There are no orders");
        }
        return orders;
    }

    @Override
    public List<Order> getSortOrdersByPeriod(Date startPeriodDate, Date endPeriodDate, SortParameter sortParameter) {
        LOGGER.debug("Method getSortOrdersByPeriod");
        LOGGER.debug("Parameter startPeriodDate: " + startPeriodDate);
        LOGGER.debug("Parameter endPeriodDate: " + endPeriodDate);
        LOGGER.debug("Parameter sortParameter: " + sortParameter);
        List<Order> orders = new ArrayList<>();
        DateUtil.checkDateTime(startPeriodDate, endPeriodDate, true);
        if (sortParameter.equals(SortParameter.COMPLETED_ORDERS_SORT_BY_FILING_DATE)) {
            orders = orderDao.getCompletedOrdersSortByFilingDate(startPeriodDate, endPeriodDate,
                                                                 hibernateSessionFactory.getSession());
        } else if (sortParameter.equals(SortParameter.COMPLETED_ORDERS_SORT_BY_EXECUTION_DATE)) {
            orders = orderDao.getCompletedOrdersSortByExecutionDate(startPeriodDate, endPeriodDate,
                                                                    hibernateSessionFactory.getSession());
        } else if (sortParameter.equals(SortParameter.COMPLETED_ORDERS_SORT_BY_PRICE)) {
            orders = orderDao
                .getCompletedOrdersSortByPrice(startPeriodDate, endPeriodDate, hibernateSessionFactory.getSession());
        } else if (sortParameter.equals(SortParameter.CANCELED_ORDERS_SORT_BY_FILING_DATE)) {
            orders = orderDao.getCanceledOrdersSortByFilingDate(startPeriodDate, endPeriodDate,
                                                                hibernateSessionFactory.getSession());
        } else if (sortParameter.equals(SortParameter.CANCELED_ORDERS_SORT_BY_EXECUTION_DATE)) {
            orders = orderDao.getCanceledOrdersSortByExecutionDate(startPeriodDate, endPeriodDate,
                                                                   hibernateSessionFactory.getSession());
        } else if (sortParameter.equals(SortParameter.CANCELED_ORDERS_SORT_BY_PRICE)) {
            orders = orderDao
                .getCanceledOrdersSortByPrice(startPeriodDate, endPeriodDate, hibernateSessionFactory.getSession());
        } else if (sortParameter.equals(SortParameter.DELETED_ORDERS_SORT_BY_FILING_DATE)) {
            orders = orderDao
                .getDeletedOrdersSortByFilingDate(startPeriodDate, endPeriodDate, hibernateSessionFactory.getSession());
        } else if (sortParameter.equals(SortParameter.DELETED_ORDERS_SORT_BY_EXECUTION_DATE)) {
            orders = orderDao.getDeletedOrdersSortByExecutionDate(startPeriodDate, endPeriodDate,
                                                                  hibernateSessionFactory.getSession());
        } else if (sortParameter.equals(SortParameter.DELETED_ORDERS_SORT_BY_PRICE)) {
            orders = orderDao
                .getDeletedOrdersSortByPrice(startPeriodDate, endPeriodDate, hibernateSessionFactory.getSession());
        }
        if (orders.isEmpty()) {
            throw new BusinessException("There are no orders");
        }
        return orders;
    }

    @Override
    public List<Order> getMasterOrders(Master master) {
        LOGGER.debug("Method getMasterOrders");
        LOGGER.debug("Parameter master: " + master);
        List<Order> orders = orderDao.getMasterOrders(master, hibernateSessionFactory.getSession());
        if (orders.isEmpty()) {
            hibernateSessionFactory.closeSession();
            throw new BusinessException("Master doesn't have any orders");
        }
        hibernateSessionFactory.closeSession();
        return orders;
    }

    @Override
    public List<Master> getOrderMasters(Order order) {
        LOGGER.debug("Method getOrderMasters");
        LOGGER.debug("Parameter order: " + order);
        List<Master> masters = orderDao.getOrderMasters(order, hibernateSessionFactory.getSession());
        if (masters.isEmpty()) {
            hibernateSessionFactory.closeSession();
            throw new BusinessException("There are no masters in order");
        }
        hibernateSessionFactory.closeSession();
        return masters;
    }

    @Override
    public Long getNumberOrders() {
        LOGGER.debug("Method getNumberOrders");
        Long numberOrders = orderDao.getNumberOrders(hibernateSessionFactory.getSession());
        hibernateSessionFactory.closeSession();
        return numberOrders;
    }

    private void checkMasters() {
        LOGGER.debug("Method checkMasters");
        if (masterDao.getNumberMasters(hibernateSessionFactory.getSession()) == 0) {
            hibernateSessionFactory.closeSession();
            throw new BusinessException("There are no masters");
        }
        hibernateSessionFactory.closeSession();
    }

    private void checkPlaces() {
        LOGGER.debug("Method checkPlaces");
        if (placeDao.getNumberPlaces(hibernateSessionFactory.getSession()) == 0) {
            hibernateSessionFactory.closeSession();
            throw new BusinessException("There are no places");
        }
        hibernateSessionFactory.closeSession();
    }

    private void checkStatusOrder(Order order) {
        LOGGER.debug("Method checkStatusOrder");
        LOGGER.debug("Parameter order: " + order);
        if (order.isDeleteStatus()) {
            hibernateSessionFactory.closeSession();
            throw new BusinessException("The order has been deleted");
        }
        if (order.getStatus() == StatusOrder.COMPLETED) {
            hibernateSessionFactory.closeSession();
            throw new BusinessException("The order has been completed");
        }
        if (order.getStatus() == StatusOrder.PERFORM) {
            hibernateSessionFactory.closeSession();
            throw new BusinessException("Order is being executed");
        }
        if (order.getStatus() == StatusOrder.CANCELED) {
            hibernateSessionFactory.closeSession();
            throw new BusinessException("The order has been canceled");
        }
    }

    private void checkStatusOrderShiftTime(Order order) {
        LOGGER.debug("Method checkStatusOrderShiftTime");
        LOGGER.debug("Parameter order: " + order);
        if (order.isDeleteStatus()) {
            hibernateSessionFactory.closeSession();
            throw new BusinessException("The order has been deleted");
        }
        if (order.getStatus() == StatusOrder.COMPLETED) {
            hibernateSessionFactory.closeSession();
            throw new BusinessException("The order has been completed");
        }
        if (order.getStatus() == StatusOrder.CANCELED) {
            hibernateSessionFactory.closeSession();
            throw new BusinessException("The order has been canceled");
        }
    }
}