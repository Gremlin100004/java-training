package com.senla.carservice.service;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;

import java.util.Date;
import java.util.List;

public interface OrderService {
    List<Order> getOrders();

    void addOrder(String automaker, String model, String registrationNumber);

    boolean completeOrder(Order order);

    boolean cancelOrder(Order order);

    boolean closeOrder(Order order);

    boolean deleteOrder(Order order);

    boolean shiftLeadTime(Order order, Date executionStartTime,
                          Date leadTime);

    List<Order> sortOrderCreationTime(List<Order> order);

    List<Order> sortOrderByLeadTime(List<Order> order);

    List<Order> sortOrderByStartTime(List<Order> order);

    List<Order> sortOrderByPrice(List<Order> order);

    List<Order> sortOrderByPeriod(List<Order> orders, Date startPeriod, Date endPeriod);

    List<Order> getCurrentRunningOrders();

    List<Order> getMasterOrders(Master master);

    List<Master> getOrderMasters(Order order);

    List<Order> getCompletedOrders(List<Order> orders);

    List<Order> getCanceledOrders(List<Order> orders);

    List<Order> getDeletedOrders(List<Order> orders);

    String exportOrder();
}