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
import java.util.stream.Collectors;

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
        return transferDataFromOrderToOrderDto(orderDao.getAllRecords());
    }

    @Override
    @Transactional
    public OrderDto addOrder(OrderDto orderDto) {
        log.debug("Method addOrder");
        log.trace("Parameter orderDto: {}", orderDto);
        return transferDataFromOrderToOrderDto(orderDao.saveRecord(transferDataFromOrderDtoToOrder(orderDto)));
    }

    @Override
    @Transactional
    public void checkOrderDeadlines(OrderDto orderDto) {
        log.debug("Method checkOrderDeadlines");
        log.trace("Parameters orderDto: {}", orderDto);
        Date executionStartTime = orderDto.getExecutionStartTime();
        Date leadTime = orderDto.getLeadTime();
        DateUtil.checkDateTime(executionStartTime, leadTime, false);
        long numberFreeMasters = masterDao.getNumberFreeMasters(executionStartTime);
        long numberFreePlace = placeDao.getNumberFreePlaces(executionStartTime);
        if (numberFreeMasters == 0) {
            throw new BusinessException("The number of masters is zero");
        }
        if (numberFreePlace == 0) {
            throw new BusinessException("The number of places is zero");
        }
    }

    //ToDo sent to ui
//    @Override
//    @Transactional
//    public void addOrderMasters(Long idMaster) {
//        log.debug("Method addOrderMasters");
//        log.trace("Parameter idMaster: {}", idMaster);
//        Order currentOrder = orderDao.getLastOrder();
//        Master master = masterDao.findById(idMaster);
//        master.setNumberOrders(master.getNumberOrders() + 1);
//        if (master.getDeleteStatus()) {
//            throw new BusinessException("Master has been deleted");
//        }
//        for (Master orderMaster : currentOrder.getMasters()) {
//            if (orderMaster.equals(master)) {
//                throw new BusinessException("This master already exists");
//            }
//        }
//        currentOrder.getMasters().add(master);
//        orderDao.updateRecord(currentOrder);
//    }

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
        Date executionStartTime = orderDto.getExecutionStartTime();
        Date leadTime = orderDto.getLeadTime();
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
            throw new BusinessException("There are no orders");
        }
        return transferDataFromOrderToOrderDto(orders);
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
        return transferDataFromOrderToOrderDto(orders);
    }

    @Override
    @Transactional
    public List<OrderDto> getMasterOrders(MasterDto masterDto) {
        log.debug("Method getMasterOrders");
        log.trace("Parameter idMaster: {}", masterDto);
        return transferDataFromOrderToOrderDto(orderDao.getMasterOrders(masterDao.findById(masterDto.getId())));
    }

    @Override
    @Transactional
    public List<MasterDto> getOrderMasters(OrderDto orderDto) {
        log.debug("Method getOrderMasters");
        log.trace("Parameter idOrder: {}", orderDto);
        return transferDataFromMasterToMasterDto(orderDao.getOrderMasters(transferDataFromOrderDtoToOrder(orderDto)));
    }

    @Override
    @Transactional
    public Long getNumberOrders() {
        log.debug("Method getNumberOrders");
        return orderDao.getNumberOrders();
    }

    private List<MasterDto> transferDataFromMasterToMasterDto(List<Master> masters) {
        List<MasterDto> mastersDto = new ArrayList<>();
        for (Master master: masters) {
            MasterDto masterDto = new MasterDto();
            masterDto.setId(master.getId());
            masterDto.setName(master.getName());
            masterDto.setNumberOrders(master.getNumberOrders());
            masterDto.setDeleteStatus(master.getDeleteStatus());
            mastersDto.add(masterDto);
        }
        return mastersDto;
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

    private List<OrderDto> transferDataFromOrderToOrderDto(List<Order> orders) {
        return orders.stream()
            .map(this::transferDataFromOrderToOrderDto)
            .collect(Collectors.toList());
    }

    private OrderDto transferDataFromOrderToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setAutomaker(order.getAutomaker());
        orderDto.setModel(order.getModel());
        orderDto.setRegistrationNumber(order.getRegistrationNumber());
        orderDto.setCreationTime(order.getCreationTime());
        orderDto.setExecutionStartTime(order.getExecutionStartTime());
        orderDto.setLeadTime(order.getLeadTime());
        orderDto.setStatus(String.valueOf(order.getStatus()));
        orderDto.setPrice(order.getPrice());
        orderDto.setDeleteStatus(order.isDeleteStatus());
        return orderDto;
    }

    private Order transferDataFromOrderDtoToOrder(OrderDto orderDto) {
        Order order;
        if (orderDto.getId() == null){
            order = new Order();
        } else {
            order = orderDao.findById(orderDto.getId());
        }
        order.setAutomaker(orderDto.getAutomaker());
        order.setModel(orderDto.getModel());
        order.setRegistrationNumber(orderDto.getRegistrationNumber());
        order.setCreationTime(orderDto.getCreationTime());
        order.setExecutionStartTime(orderDto.getExecutionStartTime());
        order.setLeadTime(orderDto.getLeadTime());
        if (orderDto.getStatus() != null) {
            order.setStatus(StatusOrder.valueOf(orderDto.getStatus()));
        }
        order.setPrice(orderDto.getPrice());
        order.setDeleteStatus(orderDto.isDeleteStatus());
        order.setMasters(orderDto.getMasters());
        return order;
    }
}