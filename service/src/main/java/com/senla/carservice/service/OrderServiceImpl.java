package com.senla.carservice.service;

import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.dao.OrderDao;
import com.senla.carservice.dao.PlaceDao;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.domain.enumaration.StatusOrder;
import com.senla.carservice.service.enumaration.SortParameter;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.util.DateUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private PlaceDao placeDao;
    @Autowired
    private MasterDao masterDao;
    @Value("${com.senla.carservice.service.OrderServiceImpl.isBlockShiftTime:false}")
    private Boolean isBlockShiftTime;
    @Value("${com.senla.carservice.service.OrderServiceImpl.isBlockDeleteOrder:false}")
    private Boolean isBlockDeleteOrder;

    @Override
    @Transactional
    public List<Order> getOrders() {
        log.debug("Method getOrders");
        return orderDao.getAllRecords();
    }

    @Override
    @Transactional
    public void addOrder(String automaker, String model, String registrationNumber) {
        log.debug("Method addOrder");
        log.trace("Parameter automaker: {}, model: {}, registrationNumber: {}", automaker, model, registrationNumber);
        checkMasters();
        checkPlaces();
        Order order = new Order(automaker, model, registrationNumber);
        Place place = placeDao.findById(1L);
        order.setPlace(place);
        orderDao.saveRecord(order);
    }

    @Override
    @Transactional
    public void addOrderDeadlines(Date executionStartTime, Date leadTime) {
        log.debug("Method addOrderDeadlines");
        log.trace("Parameters executionStartTime: {}, leadTime: {}", executionStartTime, leadTime);
        DateUtil.checkDateTime(executionStartTime, leadTime, false);
        Order currentOrder = orderDao.getLastOrder();
        long numberFreeMasters = masterDao.getNumberFreeMasters(executionStartTime);
        long numberFreePlace = placeDao.getNumberFreePlaces(executionStartTime);
        if (numberFreeMasters == 0) {
            throw new BusinessException("The number of masters is zero");
        }
        if (numberFreePlace == 0) {
            throw new BusinessException("The number of places is zero");
        }
        currentOrder.setExecutionStartTime(executionStartTime);
        currentOrder.setLeadTime(leadTime);
        orderDao.updateRecord(currentOrder);
    }

    @Override
    @Transactional
    public void addOrderMasters(Long idMaster) {
        log.debug("Method addOrderMasters");
        log.trace("Parameter idMaster: {}", idMaster);
        Order currentOrder = orderDao.getLastOrder();
        Master master = masterDao.findById(idMaster);
        master.setNumberOrders(master.getNumberOrders() + 1);
        if (master.getDeleteStatus()) {
            throw new BusinessException("Master has been deleted");
        }
        for (Master orderMaster : currentOrder.getMasters()) {
            if (orderMaster.equals(master)) {
                throw new BusinessException("This master already exists");
            }
        }
        currentOrder.getMasters().add(master);
        orderDao.updateRecord(currentOrder);
    }

    @Override
    @Transactional
    public void addOrderPlace(Long idPlace) {
        log.debug("Method addOrderPlace");
        log.trace("Parameter idPlace: {}", idPlace);
        Order currentOrder = orderDao.getLastOrder();
        currentOrder.setPlace(placeDao.findById(idPlace));
        orderDao.updateRecord(currentOrder);
    }

    @Override
    @Transactional
    public void addOrderPrice(BigDecimal price) {
        log.debug("Method addOrderPrice");
        log.trace("Parameter price: {}", price);
        Order currentOrder = orderDao.getLastOrder();
        currentOrder.setPrice(price);
        orderDao.updateRecord(currentOrder);
    }

    @Override
    @Transactional
    public void completeOrder(Long idOrder) {
        log.debug("Method completeOrder");
        log.trace("Parameter idOrder: {}", idOrder);
        Order order = orderDao.findById(idOrder);
        checkStatusOrder(order);
        order.setStatus(StatusOrder.PERFORM);
        order.setExecutionStartTime(new Date());
        Place place = order.getPlace();
        place.setIsBusy(true);
        placeDao.updateRecord(place);
        orderDao.updateRecord(order);
    }

    @Override
    @Transactional
    public void cancelOrder(Long idOrder) {
        log.debug("Method cancelOrder");
        log.trace("Parameter idOrder: {}", idOrder);
        Order order = orderDao.findById(idOrder);
        checkStatusOrder(order);
        order.setLeadTime(new Date());
        order.setStatus(StatusOrder.CANCELED);
        List<Master> masters = orderDao.getOrderMasters(order);
        masters.forEach(master -> master.setNumberOrders(master.getNumberOrders() - 1));
        masterDao.updateAllRecords(masters);
        orderDao.updateRecord(order);
        Place place = order.getPlace();
        place.setIsBusy(false);
        placeDao.updateRecord(place);
    }

    @Override
    @Transactional
    public void closeOrder(Long idOrder) {
        log.debug("Method closeOrder");
        log.trace("Parameter idOrder: {}", idOrder);
        Order order = orderDao.findById(idOrder);
        checkStatusOrderShiftTime(order);
        order.setLeadTime(new Date());
        order.setStatus(StatusOrder.COMPLETED);
        orderDao.updateRecord(order);
        Place place = order.getPlace();
        place.setIsBusy(false);
        placeDao.updateRecord(place);
        List<Master> masters = orderDao.getOrderMasters(order);
        masters.forEach(master -> master.setNumberOrders(master.getNumberOrders() - 1));
        masterDao.updateAllRecords(masters);
    }

    @Override
    @Transactional
    public void deleteOrder(Long idOrder) {
        log.debug("Method deleteOrder");
        log.trace("Parameter idOrder: {}", idOrder);
        if (isBlockDeleteOrder) {
            throw new BusinessException("Permission denied");
        }
        Order order = orderDao.findById(idOrder);
        checkStatusOrderToDelete(order);
        order.setDeleteStatus(true);
        orderDao.updateRecord(order);
    }

    @Override
    @Transactional
    public void shiftLeadTime(Long idOrder, Date executionStartTime, Date leadTime) {
        log.debug("Method shiftLeadTime");
        log.trace("Parameter idOrder: {}, executionStartTime: {}, leadTime: {}", idOrder, executionStartTime, leadTime);
        if (isBlockShiftTime) {
            throw new BusinessException("Permission denied");
        }
        DateUtil.checkDateTime(executionStartTime, leadTime, false);
        Order order = orderDao.findById(idOrder);
        checkStatusOrderShiftTime(order);
        order.setLeadTime(leadTime);
        order.setExecutionStartTime(executionStartTime);
        orderDao.updateRecord(order);
    }

    @Override
    @Transactional
    public List<Order> getSortOrders(SortParameter sortParameter) {
        log.debug("Method getSortOrders");
        log.trace("Parameter sortParameter: {}", sortParameter);
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
    }

    @Override
    @Transactional
    public List<Order> getSortOrdersByPeriod(Date startPeriodDate, Date endPeriodDate, SortParameter sortParameter) {
        log.debug("Method getSortOrdersByPeriod");
        log.trace("Parameters startPeriodDate: {}, endPeriodDate: {}, sortParameter: {}",
            startPeriodDate, endPeriodDate, sortParameter);
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
        return orders;
    }

    @Override
    @Transactional
    public List<Order> getMasterOrders(Long idMaster) {
        log.debug("Method getMasterOrders");
        log.trace("Parameter idMaster: {}", idMaster);
        return orderDao.getMasterOrders(masterDao.findById(idMaster));
    }

    @Override
    @Transactional
    public List<Master> getOrderMasters(Long idOrder) {
        log.debug("Method getOrderMasters");
        log.trace("Parameter idOrder: {}", idOrder);
        return orderDao.getOrderMasters(orderDao.findById(idOrder));
    }

    @Override
    @Transactional
    public Long getNumberOrders() {
        log.debug("Method getNumberOrders");
        return orderDao.getNumberOrders();
    }

    private void checkMasters() {
        log.debug("Method checkMasters");
        if (masterDao.getNumberMasters() == 0) {
            throw new BusinessException("There are no masters");
        }
    }

    private void checkPlaces() {
        log.debug("Method checkPlaces");
        if (placeDao.getNumberPlaces() == 0) {
            throw new BusinessException("There are no places");
        }
    }

    private void checkStatusOrder(Order order) {
        log.debug("Method checkStatusOrder");
        log.trace("Parameter order: {}", order);
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
        log.debug("Method checkStatusOrderShiftTime");
        log.trace("Parameter order: {}", order);
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
    private void checkStatusOrderToDelete(Order order) {
        log.debug("Method checkStatusOrderToDelete");
        log.trace("Parameter order: {}", order);
        if (order.isDeleteStatus()) {
            throw new BusinessException("The order has been deleted");
        }
        if (order.getStatus() == StatusOrder.WAIT) {
            throw new BusinessException("The order has been waiting");
        }
        if (order.getStatus() == StatusOrder.PERFORM) {
            throw new BusinessException("The order has been canceled");
        }
    }
}