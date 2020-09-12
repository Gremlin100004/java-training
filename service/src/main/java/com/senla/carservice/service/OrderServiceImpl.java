package com.senla.carservice.service;

import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.util.DateUtil;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.senla.carservice.service.enumaration.SortParameter;
import com.senla.carservice.domain.enumaration.StatusOrder;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.dao.OrderDao;
import com.senla.carservice.dao.PlaceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private PlaceDao placeDao;
    @Autowired
    private MasterDao masterDao;
    @Value("${com.senla.carservice.service.OrderServiceImpl.isBlockShiftTime}")
    private boolean isBlockShiftTime;
    @Value("${com.senla.carservice.service.OrderServiceImpl.isBlockDeleteOrder}")
    private boolean isBlockDeleteOrder;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    public OrderServiceImpl() {
    }

    @Override
    public List<Order> getOrders() {
        LOGGER.debug("Method getOrders");
        try (Session session = orderDao.getSession()) {
            session.beginTransaction();
            List<Order> orders = orderDao.getAllRecords(Order.class);
            if (orders.isEmpty()) {
                throw new BusinessException("There are no orders");
            }
            return orders;
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException("Error transaction get orders");
        }
    }

    @Override
    public void addOrder(String automaker, String model, String registrationNumber) {
        LOGGER.debug("Method addOrder");
        LOGGER.debug("Parameter automaker: {}", automaker);
        LOGGER.debug("Parameter model: {}", model);
        LOGGER.debug("Parameter registrationNumber: {}", registrationNumber);
        Session session = orderDao.getSession();
        Transaction transaction =session.beginTransaction();
        try {
            checkMasters();
            checkPlaces();
            Order order = new Order(automaker, model, registrationNumber);
            Place place = placeDao.getRecordById(Place.class, (long) 1);
            order.setPlace(place);
            orderDao.saveRecord(order);
            transaction.commit();
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException("Error transaction add order");
        }
    }

    @Override
    public void addOrderDeadlines(Date executionStartTime, Date leadTime) {
        LOGGER.debug("Method addOrderDeadlines");
        LOGGER.debug("Parameter executionStartTime: {}", executionStartTime);
        LOGGER.debug("Parameter leadTime: {}", leadTime);
        Session session = orderDao.getSession();
        Transaction transaction =session.beginTransaction();
        try {
            DateUtil.checkDateTime(executionStartTime, leadTime, false);
            Order currentOrder = orderDao.getLastOrder();
            if (currentOrder == null) {
                throw new BusinessException("There are no orders");
            }
            long numberFreeMasters =
                masterDao.getNumberMasters() - orderDao.getNumberBusyMasters(executionStartTime, leadTime);
            long numberFreePlace =
                placeDao.getNumberPlaces() - orderDao.getNumberBusyPlaces(executionStartTime, leadTime);
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
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException("Error transaction add dead line to order");
        }
    }

    @Override
    public void addOrderMasters(Long idMaster) {
        LOGGER.debug("Method addOrderMasters");
        LOGGER.debug("Parameter idMaster: {}", idMaster);
        Session session = orderDao.getSession();
        Transaction transaction =session.beginTransaction();
        try {
            Order currentOrder = orderDao.getLastOrder();
            Master master = masterDao.getRecordById(Master.class, idMaster);
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
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException("Error transaction add masters to order");
        }
    }

    @Override
    public void addOrderPlace(Long idPlace) {
        LOGGER.debug("Method addOrderPlace");
        LOGGER.debug("Parameter idPlace: {}", idPlace);
        Session session = orderDao.getSession();
        Transaction transaction =session.beginTransaction();
        try {
            Order currentOrder = orderDao.getLastOrder();
            if (currentOrder == null) {
                throw new BusinessException("There are no orders");
            }
            currentOrder.setPlace(placeDao.getRecordById(Place.class, idPlace));
            orderDao.updateRecord(currentOrder);
            transaction.commit();
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException("Error transaction add place to order");
        }
    }

    @Override
    public void addOrderPrice(BigDecimal price) {
        LOGGER.debug("Method addOrderPrice");
        LOGGER.debug("Parameter price: {}", price);
        Session session = orderDao.getSession();
        Transaction transaction =session.beginTransaction();
        try {
            Order currentOrder = orderDao.getLastOrder();
            if (currentOrder == null) {
                throw new BusinessException("There are no orders");
            }
            currentOrder.setPrice(price);
            orderDao.updateRecord(currentOrder);
            transaction.commit();
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException("Error transaction add price to order");
        }
    }

    @Override
    public void completeOrder(Long idOrder) {
        LOGGER.debug("Method completeOrder");
        LOGGER.debug("Parameter idOrder: {}", idOrder);
        Session session = orderDao.getSession();
        Transaction transaction =session.beginTransaction();
        try {
            Order order = orderDao.getRecordById(Order.class, idOrder);
            checkStatusOrder(order);
            order.setStatus(StatusOrder.PERFORM);
            order.setExecutionStartTime(new Date());
            order.getPlace().setBusy(true);
            orderDao.updateRecord(order);
            transaction.commit();
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException("Error transaction transfer order to execution status");
        }
    }

    @Override
    public void cancelOrder(Long idOrder) {
        LOGGER.debug("Method cancelOrder");
        LOGGER.debug("Parameter idOrder: {}", idOrder);
        Session session = orderDao.getSession();
        Transaction transaction =session.beginTransaction();
        try {
            Order order = orderDao.getRecordById(Order.class, idOrder);
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
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException("Error transaction cancel order");
        }
    }

    @Override
    public void closeOrder(Long idOrder) {
        LOGGER.debug("Method closeOrder");
        LOGGER.debug("Parameter idOrder: {}", idOrder);
        Session session = orderDao.getSession();
        Transaction transaction =session.beginTransaction();
        try {
            Order order = orderDao.getRecordById(Order.class, idOrder);
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
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException("Error transaction close order");
        }
    }

    @Override
    public void deleteOrder(Long idOrder) {
        LOGGER.debug("Method deleteOrder");
        LOGGER.debug("Parameter idOrder: {}", idOrder);
        if (isBlockDeleteOrder) {
            throw new BusinessException("Permission denied");
        }
        Session session = orderDao.getSession();
        Transaction transaction =session.beginTransaction();
        try {
            orderDao.updateRecord(orderDao.getRecordById(Order.class, idOrder));
            transaction.commit();
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException("Error transaction get masters");
        }
    }

    @Override
    public void shiftLeadTime(Long idOrder, Date executionStartTime, Date leadTime) {
        LOGGER.debug("Method shiftLeadTime");
        LOGGER.debug("Parameter idOrder: {}", idOrder);
        LOGGER.debug("Parameter executionStartTime: {}", executionStartTime);
        LOGGER.debug("Parameter leadTime: {}", leadTime);
        if (isBlockShiftTime) {
            throw new BusinessException("Permission denied");
        }
        Session session = orderDao.getSession();
        Transaction transaction =session.beginTransaction();
        try {
            DateUtil.checkDateTime(executionStartTime, leadTime, false);
            Order order = orderDao.getRecordById(Order.class, idOrder);
            checkStatusOrderShiftTime(order);
            order.setLeadTime(leadTime);
            order.setExecutionStartTime(executionStartTime);
            orderDao.updateRecord(order);
            transaction.commit();
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException("Error transaction shift lead time");
        }
    }

    @Override
    public List<Order> getSortOrders(SortParameter sortParameter) {
        LOGGER.debug("Method getSortOrders");
        LOGGER.debug("Parameter sortParameter: {}", sortParameter);
        try (Session session = orderDao.getSession()) {
            session.beginTransaction();
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
            return orders;
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException("Error transaction get sort orders");
        }
    }

    @Override
    public List<Order> getSortOrdersByPeriod(Date startPeriodDate, Date endPeriodDate, SortParameter sortParameter) {
        LOGGER.debug("Method getSortOrdersByPeriod");
        LOGGER.debug("Parameter startPeriodDate: {}", startPeriodDate);
        LOGGER.debug("Parameter endPeriodDate: {}", endPeriodDate);
        LOGGER.debug("Parameter sortParameter: {}", sortParameter);
        try (Session session = orderDao.getSession()) {
            session.beginTransaction();
            List<Order> orders = new ArrayList<>();
            DateUtil.checkDateTime(startPeriodDate, endPeriodDate, true);
            if (sortParameter.equals(SortParameter.COMPLETED_ORDERS_SORT_BY_FILING_DATE)) {
                orders = orderDao.getCompletedOrdersSortByFilingDate(startPeriodDate, endPeriodDate);
            } else if (sortParameter.equals(SortParameter.COMPLETED_ORDERS_SORT_BY_EXECUTION_DATE)) {
                orders = orderDao.getCompletedOrdersSortByExecutionDate(startPeriodDate, endPeriodDate);
            } else if (sortParameter.equals(SortParameter.COMPLETED_ORDERS_SORT_BY_PRICE)) {
                orders = orderDao.getCompletedOrdersSortByPrice(startPeriodDate, endPeriodDate);
            } else if (sortParameter.equals(SortParameter.CANCELED_ORDERS_SORT_BY_FILING_DATE)) {
                orders = orderDao.getCanceledOrdersSortByFilingDate(startPeriodDate, endPeriodDate);
            } else if (sortParameter.equals(SortParameter.CANCELED_ORDERS_SORT_BY_EXECUTION_DATE)) {
                orders = orderDao.getCanceledOrdersSortByExecutionDate(startPeriodDate, endPeriodDate);
            } else if (sortParameter.equals(SortParameter.CANCELED_ORDERS_SORT_BY_PRICE)) {
                orders = orderDao.getCanceledOrdersSortByPrice(startPeriodDate, endPeriodDate);
            } else if (sortParameter.equals(SortParameter.DELETED_ORDERS_SORT_BY_FILING_DATE)) {
                orders = orderDao.getDeletedOrdersSortByFilingDate(startPeriodDate, endPeriodDate);
            } else if (sortParameter.equals(SortParameter.DELETED_ORDERS_SORT_BY_EXECUTION_DATE)) {
                orders = orderDao.getDeletedOrdersSortByExecutionDate(startPeriodDate, endPeriodDate);
            } else if (sortParameter.equals(SortParameter.DELETED_ORDERS_SORT_BY_PRICE)) {
                orders = orderDao.getDeletedOrdersSortByPrice(startPeriodDate, endPeriodDate);
            }
            if (orders.isEmpty()) {
                throw new BusinessException("There are no orders");
            }
            return orders;
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException("Error transaction get sort orders by date");
        }
    }

    @Override
    public List<Order> getMasterOrders(Long idMaster) {
        LOGGER.debug("Method getMasterOrders");
        LOGGER.debug("Parameter idMaster: {}", idMaster);
        try (Session session = orderDao.getSession()) {
            session.beginTransaction();
            List<Order> orders = orderDao.getMasterOrders(masterDao.getRecordById(Master.class, idMaster));
            if (orders.isEmpty()) {
                throw new BusinessException("Master doesn't have any orders");
            }
            return orders;
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException("Error transaction get master orders");
        }
    }

    @Override
    public List<Master> getOrderMasters(Long idOrder) {
        LOGGER.debug("Method getOrderMasters");
        LOGGER.debug("Parameter idOrder: {}", idOrder);
        try (Session session = orderDao.getSession()) {
            session.beginTransaction();
            Order order = orderDao.getRecordById(Order.class, idOrder);
            List<Master> masters = orderDao.getOrderMasters(order);
            if (masters.isEmpty()) {
                throw new BusinessException("There are no masters in order");
            }
            return masters;
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException("Error transaction order masters");
        }
    }

    @Override
    public Long getNumberOrders() {
        LOGGER.debug("Method getNumberOrders");
        try (Session session = orderDao.getSession()) {
            session.beginTransaction();
            return orderDao.getNumberOrders();
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
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
        LOGGER.debug("Parameter order: {}", order);
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
        LOGGER.debug("Parameter order: {}", order);
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