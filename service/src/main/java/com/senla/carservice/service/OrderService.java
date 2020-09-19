package com.senla.carservice.service;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.service.enumaration.SortParameter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderService {

    List<Order> getOrders();

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

    List<Order> getSortOrders(SortParameter sortParameter);

    List<Order> getSortOrdersByPeriod(Date startPeriodDate, Date endPeriodDate, SortParameter sortParameter);

    List<Order> getMasterOrders(Long idMaster);

    List<Master> getOrderMasters(Long idOrder);

    Long getNumberOrders();
}