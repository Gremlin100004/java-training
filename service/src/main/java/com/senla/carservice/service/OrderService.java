package com.senla.carservice.service;

import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;
import com.senla.carservice.service.enumaration.SortParameter;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface OrderService {

    List<OrderDto> getOrders();

    String checkMastersPlaces();

    void addOrder(OrderDto orderDto);

    void checkOrderDeadlines(Date executionStartTime, Date leadTime);

    void completeOrder(OrderDto orderDto);

    void cancelOrder(OrderDto orderDto);

    void closeOrder(OrderDto orderDto);

    void deleteOrder(OrderDto orderDto);

    void shiftLeadTime(OrderDto orderDto, Date executionStartTime, Date leadTime);

    List<OrderDto> getSortOrders(SortParameter sortParameter);

    List<OrderDto> getSortOrdersByPeriod(Date startPeriodDate, Date endPeriodDate, SortParameter sortParameter);

    List<OrderDto> getMasterOrders(MasterDto masterDto);

    List<MasterDto> getOrderMasters(OrderDto orderDto);

    Long getNumberOrders();
}