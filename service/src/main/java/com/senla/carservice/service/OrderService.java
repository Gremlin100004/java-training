package com.senla.carservice.service;

import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;
import com.senla.carservice.service.enumaration.OrderSortParameter;

import java.util.Date;
import java.util.List;

public interface OrderService {

    List<OrderDto> getOrders();

    OrderDto addOrder(OrderDto orderDto);

    void checkOrderDeadlines(Date executionStartTime, Date leadTime);

    void completeOrder(Long orderId);

    void cancelOrder(Long orderId);

    void closeOrder(Long orderId);

    void deleteOrder(Long orderId);

    void shiftLeadTime(OrderDto orderDto);

    List<OrderDto> getSortOrders(OrderSortParameter sortParameter);

    List<OrderDto> getSortOrdersByPeriod(Date startPeriodDate, Date endPeriodDate, OrderSortParameter sortParameter);

    List<MasterDto> getOrderMasters(Long orderId);

    void checkOrders();
}