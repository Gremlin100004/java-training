package com.senla.carservice.service;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.exception.DateException;
import com.senla.carservice.exception.NullDateException;
import com.senla.carservice.exception.NumberObjectZeroException;
import com.senla.carservice.exception.OrderStatusException;
import com.senla.carservice.util.DateUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderService {
    List<Order> getOrders();

    void addOrder(String automaker, String model, String registrationNumber);

    void addOrderDeadlines(Date executionStartTime, Date leadTime) throws NullDateException, DateException, NumberObjectZeroException;

    void addOrderMasters(List<Master> masters) throws NumberObjectZeroException;

    void addOrderPlace(Place place);

    void addOrderPrice(BigDecimal price);

    void completeOrder(Order order) throws OrderStatusException;

    void cancelOrder(Order order) throws OrderStatusException;

    void closeOrder(Order order) throws OrderStatusException;

    void deleteOrder(Order order) throws OrderStatusException;

    void shiftLeadTime(Order order, Date executionStartTime,
                          Date leadTime) throws NullDateException, OrderStatusException, DateException;

    List<Order> sortOrderCreationTime(List<Order> order);

    List<Order> sortOrderByLeadTime(List<Order> order);

    List<Order> sortOrderByStartTime(List<Order> order);

    List<Order> sortOrderByPrice(List<Order> order);

    List<Order> getOrderByPeriod(Date startPeriod, Date endPeriod) throws NullDateException;

    List<Order> getCurrentRunningOrders();

    List<Order> getMasterOrders(Master master);

    List<Master> getOrderMasters(Order order);

    List<Order> getCompletedOrders(List<Order> orders);

    List<Order> getCanceledOrders(List<Order> orders);

    List<Order> getDeletedOrders(List<Order> orders);

//    String exportOrder();

//    String importOrder();
}