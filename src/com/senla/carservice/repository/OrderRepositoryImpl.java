package com.senla.carservice.repository;

import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Status;
import com.senla.carservice.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {
    private static OrderRepository instance;
    private List<Order> orders;
    private final IdGenerator idGeneratorOrder;

    private OrderRepositoryImpl() {
        this.orders = new ArrayList<>();
        this.idGeneratorOrder = new IdGenerator();
    }

    public static OrderRepository getInstance() {
        if (instance == null) {
            instance = new OrderRepositoryImpl();
        }
        return instance;
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
        return this.orders.get(orders.size()-1);
    }

    @Override
    public List<Order> getCompletedOrders() {
        List<Order> completedOrders = new ArrayList<>();
        this.orders.forEach(order -> {
            if (order.getStatus().equals(Status.COMPLETED)){
                completedOrders.add(order);
            }
        });
        return completedOrders;
    }

    @Override
    public List<Order> getDeletedOrders() {
        List<Order> deletedOrders = new ArrayList<>();
        this.orders.forEach(order -> {
            if (order.isDeleteStatus()){
                deletedOrders.add(order);
            }
        });
        return deletedOrders;
    }

    @Override
    public List<Order> getCanceledOrders() {
        List<Order> canceledOrders = new ArrayList<>();
        this.orders.forEach(order -> {
            if (order.getStatus().equals(Status.CANCELED)){
                canceledOrders.add(order);
            }
        });
        return canceledOrders;
    }

    @Override
    public void addOrder(Order order) {
        order.setId(this.idGeneratorOrder.getId());
        this.orders.add(order);
    }

    @Override
    public void deleteOrder(Order order) {
        this.orders.remove(order);
    }

    @Override
    public void addExistingOrder(Order order) {

    }

    @Override
    public void updateOrder(Order order) {
        this.orders.remove(order);
        this.orders.add(order);
    }

}