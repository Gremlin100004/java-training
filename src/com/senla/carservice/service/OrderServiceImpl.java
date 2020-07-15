package com.senla.carservice.service;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.enumeration.Status;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.container.annotation.Dependency;
import com.senla.carservice.container.annotation.Property;
import com.senla.carservice.repository.MasterRepository;
import com.senla.carservice.repository.OrderRepository;
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
    private OrderRepository orderRepository;
    @Dependency
    private PlaceRepository placeRepository;
    @Dependency
    private MasterRepository masterRepository;
    @Property
    private boolean isBlockShiftTime;

    public OrderServiceImpl() {
    }

    @Override
    public List<Order> getOrders() {
        checkOrders();
        return orderRepository.getOrders();
    }

    @Override
    public void addOrder(String automaker, String model, String registrationNumber) {
        checkMasters();
        checkPlaces();
        orderRepository.addOrder(new Order(orderRepository.getIdGeneratorOrder().getId(),
                                           automaker, model, registrationNumber));
    }

    @Override
    public void addOrderDeadlines(Date executionStartTime, Date leadTime) {
        DateUtil.checkDateTime(executionStartTime, leadTime);
        checkOrders();
        Order currentOrder = orderRepository.getLastOrder();
        List<Order> orders = new ArrayList<>(orderRepository.getOrders());
        orders.remove(currentOrder);
        if (!orders.isEmpty()) {
            orders = sortOrderByPeriod(orders, executionStartTime, leadTime);
        }
        int numberFreeMasters = masterRepository.getMasters().size() - orders.stream()
            .mapToInt(order -> order.getMasters().size()).sum();
        int numberFreePlace = orderRepository.getOrders().size() - orders.size();
        if (numberFreeMasters == 0) {
            throw new BusinessException("The number of masters is zero");
        }
        if (numberFreePlace == 0) {
            throw new BusinessException("The number of places is zero");
        }
        currentOrder.setExecutionStartTime(executionStartTime);
        currentOrder.setLeadTime(leadTime);
        orderRepository.updateOrder(currentOrder);
    }

    @Override
    public void addOrderMasters(Master master) {
        checkOrders();
        Order currentOrder = orderRepository.getLastOrder();
        List<Master> masters = currentOrder.getMasters();
        masters.stream()
            .filter(orderMaster -> orderMaster.equals(master))
            .forEachOrdered(orderMaster -> {
                throw new BusinessException("This master already exists");
            });
        masters.add(master);
        currentOrder.setMasters(masters);
        orderRepository.updateOrder(currentOrder);
        master.getOrders().add(currentOrder);
        masterRepository.updateMaster(master);
    }

    @Override
    public void addOrderPlace(Place place) {
        checkOrders();
        Order currentOrder = orderRepository.getLastOrder();
        currentOrder.setPlace(place);
        place.getOrders().add(currentOrder);
        placeRepository.updatePlace(place);
        orderRepository.updateOrder(currentOrder);
    }

    @Override
    public void addOrderPrice(BigDecimal price) {
        checkOrders();
        Order currentOrder = orderRepository.getLastOrder();
        currentOrder.setPrice(price);
        orderRepository.updateOrder(currentOrder);
    }

    @Override
    public void completeOrder(Order order) {
        checkStatusOrder(order);
        order.setStatus(Status.PERFORM);
        order.setExecutionStartTime(new Date());
        order.getPlace().setBusyStatus(true);
        orderRepository.updateOrder(order);
    }

    @Override
    public void cancelOrder(Order order) {
        checkStatusOrder(order);
        order.setLeadTime(new Date());
        order.setStatus(Status.CANCELED);
        order.getMasters().forEach(master -> {
            master.getOrders().remove(order);
            masterRepository.updateMaster(master);
        });
        Place place = order.getPlace();
        place.setBusyStatus(false);
        place.getOrders().remove(order);
        placeRepository.updatePlace(place);
        orderRepository.updateOrder(order);
    }

    @Override
    public void closeOrder(Order order) {
        checkStatusOrder(order);
        order.setLeadTime(new Date());
        order.setStatus(Status.COMPLETED);
        order.getMasters().forEach(master -> {
            master.getOrders().remove(order);
            masterRepository.updateMaster(master);
        });
        Place place = order.getPlace();
        place.setBusyStatus(false);
        place.getOrders().remove(order);
        placeRepository.updatePlace(order.getPlace());
        orderRepository.updateOrder(order);
    }

    @Override
    public void deleteOrder(Order order) {
        orderRepository.deleteOrder(order);
    }

    @Override
    public void shiftLeadTime(Order order, Date executionStartTime, Date leadTime) {
        if (isBlockShiftTime) {
            throw new BusinessException("Permission denied");
        }
        DateUtil.checkDateTime(executionStartTime, leadTime);
        checkStatusOrderShiftTime(order);
        order.setLeadTime(leadTime);
        order.setExecutionStartTime(executionStartTime);
        Place place = order.getPlace();
        place.getOrders().set(place.getOrders().indexOf(order), order);
        order.getMasters().forEach(master -> {
            master.getOrders().set(master.getOrders().indexOf(order), order);
            masterRepository.updateMaster(master);
        });
        placeRepository.updatePlace(place);
        orderRepository.updateOrder(order);
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
        if (order.getStatus().equals(Status.COMPLETED)) {
            throw new BusinessException("The order has been completed");
        }
        if (order.getStatus().equals(Status.PERFORM)) {
            throw new BusinessException("Order is being executed");
        }
        if (order.getStatus().equals(Status.CANCELED)) {
            throw new BusinessException("The order has been canceled");
        }
    }

    private void checkStatusOrderShiftTime(Order order) {
        if (order.isDeleteStatus()) {
            throw new BusinessException("The order has been deleted");
        }
        if (order.getStatus().equals(Status.COMPLETED)) {
            throw new BusinessException("The order has been completed");
        }
        if (order.getStatus().equals(Status.CANCELED)) {
            throw new BusinessException("The order has been canceled");
        }
    }
}