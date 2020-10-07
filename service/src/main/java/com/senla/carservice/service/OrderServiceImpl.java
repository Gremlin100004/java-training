package com.senla.carservice.service;

import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.dao.OrderDao;
import com.senla.carservice.dao.PlaceDao;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.domain.enumaration.StatusOrder;
import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;
import com.senla.carservice.service.enumaration.SortParameter;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.service.util.MasterMapper;
import com.senla.carservice.service.util.OrderMapper;
import com.senla.carservice.util.DateUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<OrderDto> getOrders() {
        log.debug("Method getOrders");
        return OrderMapper.getOrderDto(orderDao.getAllRecords());
    }

    @Override
    @Transactional
    public OrderDto addOrder(OrderDto orderDto) {
        log.debug("Method addOrder");
        log.trace("Parameter orderDto: {}", orderDto);
        return OrderMapper.getOrderDto(
            orderDao.saveRecord(OrderMapper.getOrder(orderDto, masterDao, placeDao)));
    }

    @Override
    @Transactional
    public void checkOrderDeadlines(Date executionStartTime, Date leadTime) {
        log.debug("Method checkOrderDeadlines");
        log.trace("Parameters executionStartTime: {}, leadTime: {}", executionStartTime, leadTime);
        DateUtil.checkDateTime(executionStartTime, leadTime, false);
        long numberFreeMasters = masterDao.getNumberFreeMasters(executionStartTime);
        long numberFreePlace = placeDao.getNumberFreePlaces(executionStartTime);
        if (numberFreeMasters == 0) {
            throw new BusinessException("Error, the number of masters is zero");
        }
        if (numberFreePlace == 0) {
            throw new BusinessException("Error, the number of places is zero");
        }
    }

    @Override
    @Transactional
    public void completeOrder(Long orderId) {
        log.debug("Method completeOrder");
        log.trace("Parameter orderId: {}", orderId);
        Order order = orderDao.findById(orderId);
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
    public void cancelOrder(Long orderId) {
        log.debug("Method cancelOrder");
        log.trace("Parameter orderId: {}", orderId);
        Order order = orderDao.findById(orderId);
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
    public void closeOrder(Long orderId) {
        log.debug("Method closeOrder");
        log.trace("Parameter orderId: {}", orderId);
        Order order = orderDao.findById(orderId);
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
    public void deleteOrder(Long orderId) {
        log.debug("Method deleteOrder");
        log.trace("Parameter orderId: {}", orderId);
        if (isBlockDeleteOrder) {
            throw new BusinessException("Permission denied");
        }
        Order order = orderDao.findById(orderId);
        checkStatusOrderToDelete(order);
        order.setDeleteStatus(true);
        orderDao.updateRecord(order);
    }

    @Override
    @Transactional
    public void shiftLeadTime(OrderDto orderDto) {
        log.debug("Method shiftLeadTime");
        log.trace("Parameter orderDto: {}", orderDto);
        if (isBlockShiftTime) {
            throw new BusinessException("Permission denied");
        }
        Date executionStartTime = DateUtil.getDatesFromString(orderDto.getExecutionStartTime(), true);
        Date leadTime = DateUtil.getDatesFromString(orderDto.getLeadTime(), true);
        DateUtil.checkDateTime(executionStartTime, leadTime, false);
        Order order = orderDao.findById(orderDto.getId());
        order.setExecutionStartTime(executionStartTime);
        order.setLeadTime(leadTime);
        checkStatusOrderShiftTime(order);
        order.setLeadTime(leadTime);
        order.setExecutionStartTime(executionStartTime);
        orderDao.updateRecord(order);
    }

    @Override
    @Transactional
    public List<OrderDto> getSortOrders(SortParameter sortParameter) {
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
            throw new BusinessException("Error, there are no orders");
        }
        return OrderMapper.getOrderDto(orders);
    }

    @Override
    @Transactional
    public List<OrderDto> getSortOrdersByPeriod(Date startPeriodDate, Date endPeriodDate, SortParameter sortParameter) {
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
        return OrderMapper.getOrderDto(orders);
    }

    @Override
    @Transactional
    public List<MasterDto> getOrderMasters(Long orderId) {
        log.debug("Method getOrderMasters");
        log.trace("Parameter orderId: {}", orderId);
        return MasterMapper.getMasterDto(
            orderDao.getOrderMasters(orderDao.findById(orderId)));
    }

    @Override
    @Transactional
    public void checkOrders() {
        log.debug("Method getNumberOrders");
        if (orderDao.getNumberOrders() == 0) {
            throw new BusinessException("Error, there are no orders");
        }
    }

    private void checkStatusOrder(Order order) {
        log.debug("Method checkStatusOrder");
        log.trace("Parameter order: {}", order);
        if (order.isDeleteStatus()) {
            throw new BusinessException("Error, the order has been deleted");
        }
        if (order.getStatus() == StatusOrder.COMPLETED) {
            throw new BusinessException("Error, the order has been completed");
        }
        if (order.getStatus() == StatusOrder.PERFORM) {
            throw new BusinessException("Error, order is being executed");
        }
        if (order.getStatus() == StatusOrder.CANCELED) {
            throw new BusinessException("Error, the order has been canceled");
        }
    }

    private void checkStatusOrderShiftTime(Order order) {
        log.debug("Method checkStatusOrderShiftTime");
        log.trace("Parameter order: {}", order);
        if (order.isDeleteStatus()) {
            throw new BusinessException("Error, the order has been deleted");
        }
        if (order.getStatus() == StatusOrder.COMPLETED) {
            throw new BusinessException("Error, the order has been completed");
        }
        if (order.getStatus() == StatusOrder.CANCELED) {
            throw new BusinessException("Error, the order has been canceled");
        }
    }
    private void checkStatusOrderToDelete(Order order) {
        log.debug("Method checkStatusOrderToDelete");
        log.trace("Parameter order: {}", order);
        if (order.isDeleteStatus()) {
            throw new BusinessException("Error, the order has been deleted");
        }
        if (order.getStatus() == StatusOrder.WAIT) {
            throw new BusinessException("Error, the order has been waiting");
        }
        if (order.getStatus() == StatusOrder.PERFORM) {
            throw new BusinessException("Error, the order has been canceled");
        }
    }

}
