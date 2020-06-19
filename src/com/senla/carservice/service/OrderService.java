package com.senla.carservice.service;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;

import java.util.ArrayList;
import java.util.Date;

public interface OrderService {
    ArrayList<Order> getOrders();

    void addOrder(Order order);

    boolean completeOrder(Order order);

    boolean cancelOrder(Order order);

    boolean closeOrder(Order order);

    boolean deleteOrder(Order order);

    boolean shiftLeadTime(Order order, Date executionStartTime,
                          Date leadTime);

    ArrayList<Order> sortOrderCreationTime(ArrayList<Order> order);

    ArrayList<Order> sortOrderByLeadTime(ArrayList<Order> order);

    ArrayList<Order> sortOrderByStartTime(ArrayList<Order> order);

    ArrayList<Order> sortOrderByPrice(ArrayList<Order> order);

    ArrayList<Order> sortOrderByPeriod(ArrayList<Order> orders, Date startPeriod, Date endPeriod);

    ArrayList<Order> getCurrentRunningOrders();

    ArrayList<Order> getMasterOrders(Master master);

    ArrayList<Master> getOrderMasters(Order order);

    ArrayList<Order> getCompletedOrders(ArrayList<Order> orders);

    ArrayList<Order> getCanceledOrders(ArrayList<Order> orders);

    ArrayList<Order> getDeletedOrders(ArrayList<Order> orders);
}