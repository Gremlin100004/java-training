package com.senla.carservice.service;

import com.senla.carservice.domain.Master;
import com.senla.carservice.repository.Order;

import java.util.Date;

public interface IOrderService {
    Order[] getOrders();

    void addOrder(Order order);

    boolean completeOrder(Order order);

    boolean cancelOrder(Order order);

    boolean closeOrder(Order order);

    boolean deleteOrder(Order order);

    boolean shiftLeadTime(Order order, Date executionStartTime,
                          Date leadTime);

    Order[] sortOrderCreationTime(Order[] order);

    Order[] sortOrderByLeadTime(Order[] order);

    Order[] sortOrderByStartTime(Order[] order);

    Order[] sortOrderByPrice(Order[] order);

    Order[] sortOrderByPeriod(Order[] orders, Date startPeriod, Date endPeriod);

    Order[] getCurrentRunningOrders();

    Order[] getMasterOrders(Master master);

    Master[] getOrderMasters(Order order);

    Order[] getCompletedOrders(Order[] orders);

    Order[] getCanceledOrders(Order[] orders);

    Order[] getDeletedOrders(Order[] orders);
}