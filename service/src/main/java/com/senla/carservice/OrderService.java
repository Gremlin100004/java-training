package com.senla.carservice;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.service.enumaration.SortParameter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderService {
    List<Order> getOrders();

    void addOrder(String automaker, String model, String registrationNumber);

    void addOrderDeadlines(Date executionStartTime, Date leadTime);

    void addOrderMasters(int index);

    void addOrderPlace(Place place);

    void addOrderPrice(BigDecimal price);

    void completeOrder(Order order);

    void cancelOrder(Order order);

    void closeOrder(Order order);

    void deleteOrder(Order order);

    void shiftLeadTime(Order order, Date executionStartTime, Date leadTime);

    List<Order> getSortOrders(SortParameter sortParameter);

    List<Order> getSortOrdersByPeriod(Date startPeriodDate, Date endPeriodDate, SortParameter sortParameter);

    List<Order> getMasterOrders(Master master);

    List<Master> getOrderMasters(Order order);
}