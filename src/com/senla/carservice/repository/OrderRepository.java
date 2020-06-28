package com.senla.carservice.repository;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.util.IdGenerator;

import java.util.List;

public interface OrderRepository {
    IdGenerator getIdGeneratorOrder();

    List<Order> getOrders();

    Order getLastOrder();

    List<Order> getCompletedOrders();

    List<Order> getDeletedOrders();

    List<Order> getCanceledOrders();

    List<Order> getCurrentRunningOrders();

    List<Master> getOrderMasters(Order order);

    void addOrder(Order order);

    void deleteOrder(Order order);

    void addExistingOrder(Order order);

    void updateOrder(Order order);


}