package com.senla.carservice.service;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.container.objectadjuster.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.dao.OrderDao;
import com.senla.carservice.dao.PlaceDao;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.domain.enumaration.Status;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.repository.MasterRepository;
import com.senla.carservice.repository.PlaceRepository;
import com.senla.carservice.util.DateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class OrderServiceImpl implements OrderService {
    @Dependency
    private OrderDao orderDao;
    @Dependency
    private PlaceDao placeDao;
    @Dependency
    private MasterRepository masterRepository;
    @ConfigProperty
    private boolean isBlockShiftTime;

    public OrderServiceImpl() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Order> getOrders() {
        List<Order> orders = orderDao.getAllRecords();
        checkOrders();
        //TODO check
        return orders;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addOrder(String automaker, String model, String registrationNumber) {
        //TODO check
        checkMasters();
        checkPlaces();
        orderDao.createRecord(new Order(automaker, model, registrationNumber));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addOrderDeadlines(Date executionStartTime, Date leadTime) {
        DateUtil.checkDateTime(executionStartTime, leadTime);
        //TODO check
        checkOrders();
        Order currentOrder = orderDao.getLastOrder();
        String stringExecutionStartTime = DateUtil.getStringFromDate(executionStartTime, true);
        String stringLeadTime = DateUtil.getStringFromDate(leadTime, true);
        int numberFreeMasters = orderDao.getNumberFreeMasters(stringExecutionStartTime, stringLeadTime);
        int numberFreePlace = orderDao.getNumberFreePlaces(stringExecutionStartTime, stringLeadTime);
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
    public void addOrderMasters(Master master) {
        checkOrders();
        //TODO check
        Order currentOrder = orderDao.getLastOrder();
        List<Master> masters = orderDao.getOrderMasters(currentOrder);
        for (Master orderMaster : masters) {
            if (orderMaster.equals(master)) {
                throw new BusinessException("This master already exists");
            }
        }
        masters.add(master);
        orderDao.createRecordTableOrdersMasters(currentOrder, master);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addOrderPlace(Place place) {
        checkOrders();
        //TODO check
        Order currentOrder = orderDao.getLastOrder();
        currentOrder.setPlace(place);
        orderDao.updateRecord(currentOrder);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addOrderPrice(BigDecimal price) {
        checkOrders();
        //TODO check
        Order currentOrder = orderDao.getLastOrder();
        currentOrder.setPrice(price);
        orderDao.updateRecord(currentOrder);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void completeOrder(Order order) {
        checkStatusOrder(order);
        order.setStatus(Status.PERFORM);
        order.setExecutionStartTime(new Date());
        order.getPlace().setBusyStatus(true);
        orderDao.updateRecord(order);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void cancelOrder(Order order) {
        checkStatusOrder(order);
        order.setLeadTime(new Date());
        order.setStatus(Status.CANCELED);
        orderDao.updateRecord(order);
        Place place = order.getPlace();
        place.setBusyStatus(false);
        placeDao.updateRecord(place);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void closeOrder(Order order) {
        checkStatusOrder(order);
        order.setLeadTime(new Date());
        order.setStatus(Status.COMPLETED);
        orderDao.updateRecord(order);
        Place place = order.getPlace();
        place.setBusyStatus(false);
        placeDao.updateRecord(place);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void deleteOrder(Order order) {
        orderDao.deleteRecord(order);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void shiftLeadTime(Order order, Date executionStartTime, Date leadTime) {
        if (isBlockShiftTime) {
            throw new BusinessException("Permission denied");
        }
        DateUtil.checkDateTime(executionStartTime, leadTime);
        checkStatusOrderShiftTime(order);
        order.setLeadTime(leadTime);
        order.setExecutionStartTime(executionStartTime);
        orderDao.updateRecord(order);
    }

    public List<Order> getOrdersSortByFilingDate() {

        return null;
    }

    public List<Order> getOrdersSortByExecutionDate() {
        return null;
    }

    public List<Order> getOrdersSortByPlannedStartDate() {
        return null;
    }

    public List<Order> getOrdersSortByPrice() {
        return null;
    }

    public List<Order> getExecuteOrderFilingDate() {
        return null;
    }

    public List<Order> getCompletedOrdersFilingDate() {
        return null;
    }

    public List<Order> getCompletedOrdersExecutionDate() {
        return null;
    }

    public List<Order> getCompletedOrdersPrice() {
        return null;
    }

    public List<Order> getCanceledOrdersFilingDate() {
        return null;
    }

    public List<Order> getCanceledOrdersExecutionDate() {
        return null;
    }

    public List<Order> getCanceledOrdersPrice() {
        return null;
    }

    public List<Order> getDeletedOrdersFilingDate() {
        return null;
    }

    public List<Order> getDeletedOrdersExecutionDate() {
        return null;
    }

    public List<Order> getDeletedOrdersPrice() {
        return null;
    }












    @Override
    public List<Order> sortOrderByCreationTime(List<Order> orders) {
        return orders.stream()
            .sorted(Comparator.comparing(Order::getCreationTime, Comparator.nullsLast(Comparator.naturalOrder())))
            .collect(Collectors.toList());
    }

    @Override
    public List<Order> sortOrderByLeadTime(List<Order> orders) {
        return orders.stream()
            .sorted(Comparator.comparing(Order::getLeadTime, Comparator.nullsLast(Comparator.naturalOrder())))
            .collect(Collectors.toList());
    }

    @Override
    public List<Order> sortOrderByPrice(List<Order> orders) {
        return orders.stream()
            .sorted(Comparator.comparing(Order::getPrice, Comparator.nullsLast(Comparator.naturalOrder())))
            .collect(Collectors.toList());
    }

    @Override
    public List<Order> sortOrderByStartTime(List<Order> orders) {
        return orders.stream()
            .sorted(Comparator.comparing(Order::getExecutionStartTime, Comparator.nullsLast(Comparator.naturalOrder())))
            .collect(Collectors.toList());
    }

    @Override
    public List<Order> getCurrentRunningOrders() {
        checkOrders();
        if (orderRepository.getRunningOrders().isEmpty()) {
            throw new BusinessException("There are no orders with status PERFORM");
        }
        return orderRepository.getRunningOrders();
    }

    @Override
    public List<Order> getMasterOrders(Master master) {
        checkOrders();
        if (orderRepository.getMasterOrders(master).isEmpty()) {
            throw new BusinessException("Master doesn't have any orders");
        }
        return orderRepository.getMasterOrders(master);
    }

    @Override
    public List<Master> getOrderMasters(Order order) {
        if (orderRepository.getOrderMasters(order).isEmpty()) {
            throw new BusinessException("There are no masters in order");
        }
        return orderRepository.getOrderMasters(order);
    }

    @Override
    public List<Order> getCompletedOrders(Date startPeriod, Date endPeriod) {
        if (sortOrderByPeriod(orderRepository.getCompletedOrders(), startPeriod, endPeriod).isEmpty()) {
            throw new BusinessException("There are no orders with status COMPLETED");
        }
        return (sortOrderByPeriod(orderRepository.getCompletedOrders(), startPeriod, endPeriod));
    }

    @Override
    public List<Order> getCanceledOrders(Date startPeriod, Date endPeriod) {
        if (sortOrderByPeriod(orderRepository.getCanceledOrders(), startPeriod, endPeriod).isEmpty()) {
            throw new BusinessException("There are no orders with status CANCELED");
        }
        return sortOrderByPeriod(orderRepository.getCanceledOrders(), startPeriod, endPeriod);
    }

    @Override
    public List<Order> getDeletedOrders(Date startPeriod, Date endPeriod) {
        if (sortOrderByPeriod(orderRepository.getDeletedOrders(), startPeriod, endPeriod).isEmpty()) {
            throw new BusinessException("There are no deleted orders");
        }
        return sortOrderByPeriod(orderRepository.getDeletedOrders(), startPeriod, endPeriod);
    }

    private void checkOrders() {
        if (orderRepository.getOrders().isEmpty()) {
            throw new BusinessException("There are no orders");
        }
    }

    private void checkMasters() {
        if (masterRepository.getMasters().isEmpty()) {
            throw new BusinessException("There are no masters");
        }
    }

    private void checkPlaces() {
        if (placeRepository.getPlaces().isEmpty()) {
            throw new BusinessException("There are no places");
        }
    }

    private List<Order> sortOrderByPeriod(List<Order> orders, Date startPeriod, Date endPeriod) {
        if (orders.isEmpty()) {
            throw new BusinessException("There are no orders");
        }
        List<Order> sortArrayOrder = new ArrayList<>();
        orders.forEach(order -> {
            if (order.getLeadTime().after(startPeriod) && order.getLeadTime().equals(startPeriod) &&
                order.getLeadTime().before(endPeriod) && order.getLeadTime().equals(endPeriod)) {
                sortArrayOrder.add(order);
            }
        });
        return sortArrayOrder;
    }

    private void checkStatusOrder(Order order) {
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