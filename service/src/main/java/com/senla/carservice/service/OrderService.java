package com.senla.carservice.service;

import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;
import com.senla.carservice.service.enumaration.SortParameter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderService {

    List<OrderDto> getOrders();

    void addOrder(String automaker, String model, String registrationNumber);

    void addOrderDeadlines(Date executionStartTime, Date leadTime);

    void addOrderMasters(Long idMaster);

    void addOrderPlace(Long idPlace);

    void addOrderPrice(BigDecimal price);

    void completeOrder(Long idOrder);

    void cancelOrder(Long idOrder);

    void closeOrder(Long idOrder);

    void deleteOrder(Long idOrder);

    void shiftLeadTime(Long idOrder, Date executionStartTime, Date leadTime);

    List<OrderDto> getSortOrders(SortParameter sortParameter);

    List<OrderDto> getSortOrdersByPeriod(Date startPeriodDate, Date endPeriodDate, SortParameter sortParameter);

    List<OrderDto> getMasterOrders(Long idMaster);

    List<MasterDto> getOrderMasters(Long idOrder);

    Long getNumberOrders();
}