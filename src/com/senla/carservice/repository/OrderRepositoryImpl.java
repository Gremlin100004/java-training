package com.senla.carservice.repository;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.container.objectadjuster.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.enumaration.Status;
import com.senla.carservice.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class OrderRepositoryImpl implements OrderRepository {
    private final List<Order> orders;
    @ConfigProperty
    private Boolean isBlockDeleteOrder;
    @Dependency
    private IdGenerator idGeneratorOrder;
    private static final int SIZE_INDEX = 1;
    private static final int ORDER_INDEX = -1;

    public OrderRepositoryImpl() {
        this.orders = new ArrayList<>();
    }

    @Override
    public IdGenerator getIdGeneratorOrder() {
        return idGeneratorOrder;
    }

    @Override
    public List<Order> getOrders() {
        return new ArrayList<>(this.orders);
    }

    @Override
    public Order getLastOrder() {
        return this.orders.get(orders.size() - SIZE_INDEX);
    }

    @Override
    public List<Order> getCompletedOrders() {
        return this.orders.stream()
            .filter(order -> order.getStatus().equals(Status.COMPLETED))
            .collect(Collectors.toList());
    }

    @Override
    public List<Order> getDeletedOrders() {
        return this.orders.stream()
            .filter(Order::isDeleteStatus)
            .collect(Collectors.toList());
    }

    @Override
    public List<Order> getCanceledOrders() {
        return this.orders.stream()
            .filter(order -> order.getStatus().equals(Status.CANCELED))
            .collect(Collectors.toList());
    }

    @Override
    public List<Order> getRunningOrders() {
        return this.orders.stream()
            .filter(order -> order.getStatus().equals(Status.PERFORM) && !order.isDeleteStatus())
            .collect(Collectors.toList());
    }

    @Override
    public List<Order> getMasterOrders(Master master) {
        return this.orders.stream()
            .filter(order -> !order.isDeleteStatus() && order.getMasters().stream()
                .anyMatch(masterService -> masterService.equals(master)))
            .collect(Collectors.toList());
    }

    @Override
    public List<Master> getOrderMasters(Order order) {
        return order.getMasters();
    }

    @Override
    public void addOrder(Order order) {
        order.setId(this.idGeneratorOrder.getId());
        this.orders.add(order);
    }

    @Override
    public void deleteOrder(Order order) {
        if (isBlockDeleteOrder) {
            throw new BusinessException("Permission denied");
        }
        checkStatusOrderDelete(order);
        order.setDeleteStatus(true);
        update(order);
    }

    @Override
    public void updateListOrder(List<Order> orders) {
        this.orders.clear();
        this.orders.addAll(orders);
    }

    @Override
    public void updateGenerator(IdGenerator idGenerator) {
        this.idGeneratorOrder.setId(idGenerator.getId());
    }

    @Override
    public void updateOrder(Order order) {
        update(order);
    }

    private void update(Order order) {
        int index = this.orders.indexOf(order);
        if (index == ORDER_INDEX) {
            this.orders.add(order);
        } else {
            this.orders.set(index, order);
        }
    }

    private void checkStatusOrderDelete(Order order) {
        if (order.isDeleteStatus()) {
            throw new BusinessException("The order has been deleted");
        }
        if (order.getStatus().equals(Status.PERFORM)) {
            throw new BusinessException("Order is being executed");
        }
        if (order.getStatus().equals(Status.WAIT)) {
            throw new BusinessException("The order is being waited");
        }
    }
}