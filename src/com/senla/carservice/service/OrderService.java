package com.senla.carservice.service;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderService {
    List<Order> getOrders();

    void addOrder(String automaker, String model, String registrationNumber);

    void addOrderDeadlines(Date executionStartTime, Date leadTime);

    void addOrderMasters(Master masters);

    void addOrderPlace(Place place);

    void addOrderPrice(BigDecimal price);

    void completeOrder(Order order);

    void cancelOrder(Order order);

    void closeOrder(Order order);

    void deleteOrder(Order order);

    void shiftLeadTime(Order order, Date executionStartTime, Date leadTime);

    List<Order> sortOrderByCreationTime(List<Order> order);

    List<Order> sortOrderByLeadTime(List<Order> order);

    List<Order> sortOrderByStartTime(List<Order> order);

    List<Order> sortOrderByPrice(List<Order> order);

    List<Order> getOrderByPeriod(Date startPeriod, Date endPeriod);

    List<Order> getCurrentRunningOrders();

    List<Order> getMasterOrders(Master master);

    List<Master> getOrderMasters(Order order);

    List<Order> getCompletedOrders(Date startPeriod, Date endPeriod);

    List<Order> getCanceledOrders(Date startPeriod, Date endPeriod);

    List<Order> getDeletedOrders(Date startPeriod, Date endPeriod);

    void exportOrder();

    String importOrder();

    void serializeOrder();

    void deserializeOrder();
}