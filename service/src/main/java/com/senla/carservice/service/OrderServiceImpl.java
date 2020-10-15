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
import com.senla.carservice.service.enumaration.OrderSortParameter;
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
        log.debug("[getOrders]");
        return OrderMapper.getOrderDto(orderDao.getAllRecords());
    }

    @Override
    @Transactional
    public OrderDto addOrder(OrderDto orderDto) {
        log.debug("[addOrder]");
        log.trace("[orderDto: {}]", orderDto);
        return OrderMapper.getOrderDto(
            orderDao.saveRecord(OrderMapper.getOrder(orderDto, masterDao, placeDao)));
    }

    @Override
    @Transactional
    public void checkOrderDeadlines(Date executionStartTime, Date leadTime) {
        log.debug("[checkOrderDeadlines]");
        log.trace("[executionStartTime: {}][leadTime: {}]", executionStartTime, leadTime);
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
        log.debug("[completeOrder]");
        log.trace("[orderId: {}]", orderId);
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
        log.debug("[cancelOrder]");
        log.trace("[orderId: {}]", orderId);
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
        log.debug("[closeOrder]");
        log.trace("[orderId: {}]", orderId);
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
        log.debug("[deleteOrder]");
        log.trace("[orderId: {}}", orderId);
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
        log.debug("[shiftLeadTime]");
        log.trace("[orderDto: {}]", orderDto);
        if (isBlockShiftTime) {
            throw new BusinessException("Permission denied");
        }
        Date executionStartTime = DateUtil.getDatesFromString(orderDto.getExecutionStartTime(), true);
        Date leadTime = DateUtil.getDatesFromString(orderDto.getLeadTime(), true);
        DateUtil.checkDateTime(executionStartTime, leadTime, false);
        Order order = orderDao.findById(orderDto.getId());
        checkStatusOrderShiftTime(order);
        order.setLeadTime(leadTime);
        order.setExecutionStartTime(executionStartTime);
        orderDao.updateRecord(order);
    }

    @Override
    @Transactional
    public List<OrderDto> getSortOrders(OrderSortParameter sortParameter) {
        log.debug("[getSortOrders]");
        log.trace("[sortParameter: {}]", sortParameter);
        List<Order> orders = new ArrayList<>();
        if (sortParameter.equals(OrderSortParameter.BY_FILING_DATE)) {
            orders = orderDao.getOrdersSortByFilingDate();
        } else if (sortParameter.equals(OrderSortParameter.BY_EXECUTION_DATE)) {
            orders = orderDao.getOrdersSortByExecutionDate();
        } else if (sortParameter.equals(OrderSortParameter.BY_PLANNED_START_DATE)) {
            orders = orderDao.getOrdersSortByPlannedStartDate();
        } else if (sortParameter.equals(OrderSortParameter.BY_PRICE)) {
            orders = orderDao.getOrdersSortByPrice();
        } else if (sortParameter.equals(OrderSortParameter.EXECUTE_ORDERS_BY_FILING_DATE)) {
            orders = orderDao.getExecuteOrderSortByFilingDate();
        } else if (sortParameter.equals(OrderSortParameter.EXECUTE_ORDERS_BY_EXECUTION_DATE)) {
            orders = orderDao.getExecuteOrderSortByExecutionDate();
        } else if (sortParameter.equals(OrderSortParameter.EXECUTE_ORDERS_BY_PRICE)) {
            orders = orderDao.getExecuteOrderSortByPrice();
        }
        if (orders.isEmpty()) {
            throw new BusinessException("Error, there are no orders");
        }
        return OrderMapper.getOrderDto(orders);
    }

    @Override
    @Transactional
    public List<OrderDto> getSortOrdersByPeriod(Date startPeriodDate, Date endPeriodDate, OrderSortParameter sortParameter) {
        log.debug("[getSortOrdersByPeriod]");
        log.trace("[startPeriodDate: {}][endPeriodDate: {}][sortParameter: {}]",
            startPeriodDate, endPeriodDate, sortParameter);
        List<Order> orders = new ArrayList<>();
        DateUtil.checkDateTime(startPeriodDate, endPeriodDate, true);
        if (sortParameter.equals(OrderSortParameter.COMPLETED_ORDERS_BY_FILING_DATE)) {
            orders = orderDao.getCompletedOrdersSortByFilingDate(startPeriodDate, endPeriodDate);
        } else if (sortParameter.equals(OrderSortParameter.COMPLETED_ORDERS_BY_EXECUTION_DATE)) {
            orders = orderDao.getCompletedOrdersSortByExecutionDate(startPeriodDate, endPeriodDate);
        } else if (sortParameter.equals(OrderSortParameter.COMPLETED_ORDERS_BY_PRICE)) {
            orders = orderDao.getCompletedOrdersSortByPrice(startPeriodDate, endPeriodDate);
        } else if (sortParameter.equals(OrderSortParameter.CANCELED_ORDERS_BY_FILING_DATE)) {
            orders = orderDao.getCanceledOrdersSortByFilingDate(startPeriodDate, endPeriodDate);
        } else if (sortParameter.equals(OrderSortParameter.CANCELED_ORDERS_BY_EXECUTION_DATE)) {
            orders = orderDao.getCanceledOrdersSortByExecutionDate(startPeriodDate, endPeriodDate);
        } else if (sortParameter.equals(OrderSortParameter.CANCELED_ORDERS_BY_PRICE)) {
            orders = orderDao.getCanceledOrdersSortByPrice(startPeriodDate, endPeriodDate);
        } else if (sortParameter.equals(OrderSortParameter.DELETED_ORDERS_BY_FILING_DATE)) {
            orders = orderDao.getDeletedOrdersSortByFilingDate(startPeriodDate, endPeriodDate);
        } else if (sortParameter.equals(OrderSortParameter.DELETED_ORDERS_BY_EXECUTION_DATE)) {
            orders = orderDao.getDeletedOrdersSortByExecutionDate(startPeriodDate, endPeriodDate);
        } else if (sortParameter.equals(OrderSortParameter.DELETED_ORDERS_BY_PRICE)) {
            orders = orderDao.getDeletedOrdersSortByPrice(startPeriodDate, endPeriodDate);
        }
        return OrderMapper.getOrderDto(orders);
    }

    @Override
    @Transactional
    public List<MasterDto> getOrderMasters(Long orderId) {
        log.debug("[getOrderMasters]");
        log.trace("[orderId: {}]", orderId);
        Order order = orderDao.findById(orderId);
        if (order == null){
            throw new BusinessException("Error, the is no such order");
        }
        return MasterMapper.getMasterDto(orderDao.getOrderMasters(order));
    }

    @Override
    @Transactional
    public void checkOrders() {
        log.debug("[getNumberOrders]");
        if (orderDao.getNumberOrders() == 0) {
            throw new BusinessException("Error, there are no orders");
        }
    }

    private void checkStatusOrder(Order order) {
        log.debug("[checkStatusOrder]");
        log.trace("[order: {}]", order);
        if (order == null){
            throw new BusinessException("Error, the is no such order");
        }
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
        log.debug("[checkStatusOrderShiftTime]");
        log.trace("[order: {}]", order);
        if (order == null){
            throw new BusinessException("Error, the is no such order");
        }
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
        log.debug("[checkStatusOrderToDelete");
        log.trace("[order: {}]", order);
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
