package com.senla.carservice.dao;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;

import java.util.List;

public interface OrderDao extends Dao {
    Order getLastOrder();

    List<Order> getCompletedOrders();

    List<Order> getDeletedOrders();

    List<Order> getCanceledOrders();

    List<Order> getRunningOrders();

    List<Order> getMasterOrders(Master master);

    List<Master> getOrderMasters(Order order);
}