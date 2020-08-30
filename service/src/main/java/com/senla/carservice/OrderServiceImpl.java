package com.senla.carservice;

import com.senla.carservice.annotation.Singleton;
import com.senla.carservice.connection.DatabaseConnection;
import com.senla.carservice.enumaration.SortParameter;
import com.senla.carservice.enumaration.Status;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.objectadjuster.propertyinjection.annotation.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private DatabaseConnection databaseConnection;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    public OrderServiceImpl() {
    }

    @Override
    public List<Order> getOrders() {
        LOGGER.debug("Method getOrders");
        List<Order> orders = orderDao.getAllRecords(databaseConnection);
        if (orders.isEmpty()) {
            throw new BusinessException("There are no orders");
        }
        return orders;
    }

    @Override
    public void addOrder(String automaker, String model, String registrationNumber) {
        LOGGER.debug("Method addOrder");
        LOGGER.debug("Parameter automaker: {}", automaker);
        LOGGER.debug("Parameter model: {}", model);
        LOGGER.debug("Parameter registrationNumber: {}", registrationNumber);
        try {
            databaseConnection.disableAutoCommit();
            checkMasters();
            checkPlaces();
            orderDao.createRecord(new Order(automaker, model, registrationNumber), databaseConnection);
            databaseConnection.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction add order");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public void addOrderDeadlines(Date executionStartTime, Date leadTime) {
        LOGGER.debug("Method addOrderDeadlines");
        LOGGER.debug("Parameter executionStartTime: {}", executionStartTime);
        LOGGER.debug("Parameter leadTime: {}", leadTime);
        try {
            databaseConnection.disableAutoCommit();
            DateUtil.checkDateTime(executionStartTime, leadTime, false);
            Order currentOrder = orderDao.getLastOrder(databaseConnection);
            if (currentOrder == null) {
                throw new BusinessException("There are no orders");
            }
            String stringExecutionStartTime = DateUtil.getStringFromDate(executionStartTime, true);
            String stringLeadTime = DateUtil.getStringFromDate(leadTime, true);
            int numberFreeMasters = masterDao.getNumberMasters(databaseConnection) - orderDao
                .getNumberBusyMasters(stringExecutionStartTime, stringLeadTime, databaseConnection);
            int numberFreePlace = placeDao.getNumberPlaces(databaseConnection) - orderDao
                .getNumberBusyPlaces(stringExecutionStartTime, stringLeadTime, databaseConnection);
            if (numberFreeMasters == 0) {
                throw new BusinessException("The number of masters is zero");
            }
            if (numberFreePlace == 0) {
                throw new BusinessException("The number of places is zero");
            }
            currentOrder.setExecutionStartTime(executionStartTime);
            currentOrder.setLeadTime(leadTime);
            orderDao.updateRecord(currentOrder, databaseConnection);
            databaseConnection.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction add dead line to order");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public void addOrderMasters(int index) {
        LOGGER.debug("Method addOrderMasters");
        LOGGER.debug("Parameter index: {}", index);
        try {
            databaseConnection.disableAutoCommit();
            Order currentOrder = orderDao.getLastOrder(databaseConnection);
            Master master = masterDao.getAllRecords(databaseConnection).get(index);
            if (currentOrder == null) {
                throw new BusinessException("There are no orders");
            }
            if (master.getDelete()) {
                throw new BusinessException("Master has been deleted");
            }
            for (Master orderMaster : currentOrder.getMasters()) {
                if (orderMaster.equals(master)) {
                    throw new BusinessException("This master already exists");
                }
            }
            orderDao.addRecordToTableManyToMany(currentOrder, databaseConnection);
            databaseConnection.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction add masters to order");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public void addOrderPlace(Place place) {
        LOGGER.debug("Method addOrderPlace");
        LOGGER.debug("Parameter place: {}", place);
        try {
            databaseConnection.disableAutoCommit();
            Order currentOrder = orderDao.getLastOrder(databaseConnection);
            if (currentOrder == null) {
                throw new BusinessException("There are no orders");
            }
            currentOrder.setPlace(place);
            orderDao.updateRecord(currentOrder, databaseConnection);
            databaseConnection.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction add place to order");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public void addOrderPrice(BigDecimal price) {
        LOGGER.debug("Method addOrderPrice");
        LOGGER.debug("Parameter price: {}", price);
        try {
            databaseConnection.disableAutoCommit();
            Order currentOrder = orderDao.getLastOrder(databaseConnection);
            if (currentOrder == null) {
                throw new BusinessException("There are no orders");
            }
            currentOrder.setPrice(price);
            orderDao.updateRecord(currentOrder, databaseConnection);
            databaseConnection.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction add price to order");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public void completeOrder(Order order) {
        LOGGER.debug("Method completeOrder");
        LOGGER.debug("Parameter order: {}", order);
        try {
            databaseConnection.disableAutoCommit();
            checkStatusOrder(order);
            order.setStatus(Status.PERFORM);
            order.setExecutionStartTime(new Date());
            order.getPlace().setBusyStatus(true);
            orderDao.updateRecord(order, databaseConnection);
            databaseConnection.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction transfer order to execution status");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public void cancelOrder(Order order) {
        LOGGER.debug("Method cancelOrder");
        LOGGER.debug("Parameter order: {}", order);
        try {
            databaseConnection.disableAutoCommit();
            checkStatusOrder(order);
            order.setLeadTime(new Date());
            order.setStatus(Status.CANCELED);
            orderDao.updateRecord(order, databaseConnection);
            Place place = order.getPlace();
            place.setBusyStatus(false);
            placeDao.updateRecord(place, databaseConnection);
            databaseConnection.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction cancel order");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public void closeOrder(Order order) {
        LOGGER.debug("Method closeOrder");
        LOGGER.debug("Parameter order: {}", order);
        try {
            databaseConnection.disableAutoCommit();
            checkStatusOrder(order);
            order.setLeadTime(new Date());
            order.setStatus(Status.COMPLETED);
            orderDao.updateRecord(order, databaseConnection);
            Place place = order.getPlace();
            place.setBusyStatus(false);
            placeDao.updateRecord(place, databaseConnection);
            databaseConnection.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction close order");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public void deleteOrder(Order order) {
        LOGGER.debug("Method deleteOrder");
        LOGGER.debug("Parameter order: {}", order);
        if (isBlockDeleteOrder) {
            throw new BusinessException("Permission denied");
        }
        try {
            databaseConnection.disableAutoCommit();
            orderDao.deleteRecord(order, databaseConnection);
            databaseConnection.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction get masters");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public void shiftLeadTime(Order order, Date executionStartTime, Date leadTime) {
        LOGGER.debug("Method shiftLeadTime");
        LOGGER.debug("Parameter order: {}", order.toString());
        LOGGER.debug("Parameter executionStartTime: {}", executionStartTime);
        LOGGER.debug("Parameter leadTime: {}", leadTime);
        if (isBlockShiftTime) {
            throw new BusinessException("Permission denied");
        }
        try {
            databaseConnection.disableAutoCommit();
            DateUtil.checkDateTime(executionStartTime, leadTime, false);
            checkStatusOrderShiftTime(order);
            order.setLeadTime(leadTime);
            order.setExecutionStartTime(executionStartTime);
            orderDao.updateRecord(order, databaseConnection);
            databaseConnection.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction shift lead time");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public List<Order> getSortOrders(SortParameter sortParameter) {
        LOGGER.debug("Method getSortOrders");
        LOGGER.debug("Parameter sortParameter: {}", sortParameter);
        List<Order> orders = new ArrayList<>();
        if (sortParameter.equals(SortParameter.SORT_BY_FILING_DATE)) {
            orders = orderDao.getOrdersSortByFilingDate(databaseConnection);
        } else if (sortParameter.equals(SortParameter.SORT_BY_EXECUTION_DATE)) {
            orders = orderDao.getOrdersSortByExecutionDate(databaseConnection);
        } else if (sortParameter.equals(SortParameter.BY_PLANNED_START_DATE)) {
            orders = orderDao.getOrdersSortByPlannedStartDate(databaseConnection);
        } else if (sortParameter.equals(SortParameter.SORT_BY_PRICE)) {
            orders = orderDao.getOrdersSortByPrice(databaseConnection);
        } else if (sortParameter.equals(SortParameter.EXECUTE_ORDER_SORT_BY_FILING_DATE)) {
            orders = orderDao.getExecuteOrderSortByFilingDate(databaseConnection);
        } else if (sortParameter.equals(SortParameter.EXECUTE_ORDER_SORT_BY_EXECUTION_DATE)) {
            orders = orderDao.getExecuteOrderSortExecutionDate(databaseConnection);
        }
        if (orders.isEmpty()) {
            throw new BusinessException("There are no orders");
        }
        return orders;
    }

    @Override
    public List<Order> getSortOrdersByPeriod(Date startPeriodDate, Date endPeriodDate, SortParameter sortParameter) {
        LOGGER.debug("Method getSortOrdersByPeriod");
        LOGGER.debug("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.debug("Parameter endPeriodDate: {}", endPeriodDate);
        LOGGER.debug("Parameter sortParameter: {}", sortParameter);
        List<Order> orders = new ArrayList<>();
        DateUtil.checkDateTime(startPeriodDate, endPeriodDate, true);
        String stringStartPeriodDate = DateUtil.getStringFromDate(startPeriodDate, true);
        String stringEndPeriodDate = DateUtil.getStringFromDate(endPeriodDate, true);
        if (sortParameter.equals(SortParameter.COMPLETED_ORDERS_SORT_BY_FILING_DATE)) {
            orders = orderDao
                .getCompletedOrdersSortByFilingDate(stringStartPeriodDate, stringEndPeriodDate, databaseConnection);
        } else if (sortParameter.equals(SortParameter.COMPLETED_ORDERS_SORT_BY_EXECUTION_DATE)) {
            orders = orderDao
                .getCompletedOrdersSortByExecutionDate(stringStartPeriodDate, stringEndPeriodDate, databaseConnection);
        } else if (sortParameter.equals(SortParameter.COMPLETED_ORDERS_SORT_BY_PRICE)) {
            orders =
                orderDao.getCompletedOrdersSortByPrice(stringStartPeriodDate, stringEndPeriodDate, databaseConnection);
        } else if (sortParameter.equals(SortParameter.CANCELED_ORDERS_SORT_BY_FILING_DATE)) {
            orders = orderDao
                .getCanceledOrdersSortByFilingDate(stringStartPeriodDate, stringEndPeriodDate, databaseConnection);
        } else if (sortParameter.equals(SortParameter.CANCELED_ORDERS_SORT_BY_EXECUTION_DATE)) {
            orders = orderDao
                .getCanceledOrdersSortByExecutionDate(stringStartPeriodDate, stringEndPeriodDate, databaseConnection);
        } else if (sortParameter.equals(SortParameter.CANCELED_ORDERS_SORT_BY_PRICE)) {
            orders =
                orderDao.getCanceledOrdersSortByPrice(stringStartPeriodDate, stringEndPeriodDate, databaseConnection);
        } else if (sortParameter.equals(SortParameter.DELETED_ORDERS_SORT_BY_FILING_DATE)) {
            orders = orderDao
                .getDeletedOrdersSortByFilingDate(stringStartPeriodDate, stringEndPeriodDate, databaseConnection);
        } else if (sortParameter.equals(SortParameter.DELETED_ORDERS_SORT_BY_EXECUTION_DATE)) {
            orders = orderDao
                .getDeletedOrdersSortByExecutionDate(stringStartPeriodDate, stringEndPeriodDate, databaseConnection);
        } else if (sortParameter.equals(SortParameter.DELETED_ORDERS_SORT_BY_PRICE)) {
            orders =
                orderDao.getDeletedOrdersSortByPrice(stringStartPeriodDate, stringEndPeriodDate, databaseConnection);
        }
        if (orders.isEmpty()) {
            throw new BusinessException("There are no orders");
        }
        return orders;
    }

    @Override
    public List<Order> getMasterOrders(Master master) {
        LOGGER.debug("Method getMasterOrders");
        LOGGER.debug("Parameter master: {}", master.toString());
        List<Order> orders = orderDao.getMasterOrders(master, databaseConnection);
        if (orders.isEmpty()) {
            throw new BusinessException("Master doesn't have any orders");
        }
        return orders;
    }

    @Override
    public List<Master> getOrderMasters(Order order) {
        LOGGER.debug("Method getOrderMasters");
        LOGGER.debug("Parameter order: {}", order);
        List<Master> masters = order.getMasters();
        if (masters.isEmpty()) {
            throw new BusinessException("There are no masters in order");
        }
        return masters;
    }

    @Override
    public int getNumberOrders() {
        LOGGER.debug("Method getNumberOrders");
        return orderDao.getNumberOrders(databaseConnection);
    }

    @Override
    public Order getOrderById(Long index) {
        LOGGER.debug("Method getOrderById");
        LOGGER.debug("Parameter index: {}", index);
        Order order = orderDao.getOrderById(index, databaseConnection);
        if (order == null) {
            throw new BusinessException("There are no such order");
        }
        return order;
    }

    private void checkMasters() {
        LOGGER.debug("Method checkMasters");
        if (masterDao.getAllRecords(databaseConnection).isEmpty()) {
            throw new BusinessException("There are no masters");
        }
    }

    private void checkPlaces() {
        LOGGER.debug("Method checkPlaces");
        if (masterDao.getAllRecords(databaseConnection).isEmpty()) {
            throw new BusinessException("There are no places");
        }
    }

    private void checkStatusOrder(Order order) {
        LOGGER.debug("Method checkStatusOrder");
        LOGGER.debug("Parameter order: {}", order.toString());
        if (order.isDeleteStatus()) {
            throw new BusinessException("The order has been deleted");
        }
        if (order.getStatus() == Status.COMPLETED) {
            throw new BusinessException("The order has been completed");
        }
        if (order.getStatus() == Status.PERFORM) {
            throw new BusinessException("Order is being executed");
        }
        if (order.getStatus() == Status.CANCELED) {
            throw new BusinessException("The order has been canceled");
        }
    }

    private void checkStatusOrderShiftTime(Order order) {
        LOGGER.debug("Method checkStatusOrderShiftTime");
        LOGGER.debug("Parameter order: {}", order.toString());
        if (order.isDeleteStatus()) {
            throw new BusinessException("The order has been deleted");
        }
        if (order.getStatus() == Status.COMPLETED) {
            throw new BusinessException("The order has been completed");
        }
        if (order.getStatus() == Status.CANCELED) {
            throw new BusinessException("The order has been canceled");
        }
    }
}