package com.senla.carservice.service;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.exception.*;
import com.senla.carservice.util.DateUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderService {
    List<Order> getOrders() throws NumberObjectZeroException;

    void addOrder(String automaker, String model, String registrationNumber) throws NumberObjectZeroException;

    void addOrderDeadlines(Date executionStartTime, Date leadTime) throws NullDateException, DateException, NumberObjectZeroException;

    void addOrderMasters(Master masters) throws NumberObjectZeroException, EqualObjectsException;

    void addOrderPlace(Place place) throws NumberObjectZeroException;

    void addOrderPrice(BigDecimal price) throws NumberObjectZeroException;

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

    List<Order> getOrderByPeriod(Date startPeriod, Date endPeriod) throws NullDateException, NumberObjectZeroException, DateException;

    List<Order> getCurrentRunningOrders() throws NumberObjectZeroException;

    List<Order> getMasterOrders(Master master) throws NumberObjectZeroException;

    List<Master> getOrderMasters(Order order) throws NumberObjectZeroException;

    List<Order> getCompletedOrders() throws NumberObjectZeroException;

    List<Order> getCanceledOrders() throws NumberObjectZeroException;

    List<Order> getDeletedOrders() throws NumberObjectZeroException;

//    String exportOrder();

//    String importOrder();
}