package com.senla.carservice.service;

import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;
import com.senla.carservice.service.enumaration.SortParameter;

import java.util.Date;
import java.util.List;

public interface OrderService {

    List<OrderDto> getOrders();

    OrderDto addOrder(OrderDto orderDto);

    void checkOrderDeadlines(OrderDto orderDto);

    void completeOrder(Long orderId);

    void cancelOrder(Long orderId);

    void closeOrder(Long orderId);

    void deleteOrder(Long orderId);

    void shiftLeadTime(OrderDto orderDto);

    List<OrderDto> getSortOrders(SortParameter sortParameter);

    List<OrderDto> getSortOrdersByPeriod(Date startPeriodDate, Date endPeriodDate, SortParameter sortParameter);

    List<OrderDto> getMasterOrders(MasterDto masterDto);

    List<MasterDto> getOrderMasters(OrderDto orderDto);

    Long getNumberOrders();
}