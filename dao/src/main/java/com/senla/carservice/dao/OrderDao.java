package com.senla.carservice.dao;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;

import java.util.Date;
import java.util.List;

public interface OrderDao extends GenericDao<Order, Long> {
    Order getLastOrder();

    List<Order> getOrdersSortByFilingDate();

    List<Order> getOrdersSortByExecutionDate();

    List<Order> getOrdersSortByPlannedStartDate();

    List<Order> getOrdersSortByPrice();

    List<Order> getExecuteOrderSortByFilingDate();

    List<Order> getExecuteOrderSortExecutionDate();

    List<Order> getCompletedOrdersSortByFilingDate(Date startPeriodDate, Date endPeriodDate);

    List<Order> getCompletedOrdersSortByExecutionDate(Date startPeriodDate, Date endPeriodDate);

    List<Order> getCompletedOrdersSortByPrice(Date startPeriodDate, Date endPeriodDate);

    List<Order> getCanceledOrdersSortByFilingDate(Date startPeriodDate, Date endPeriodDate);

    List<Order> getCanceledOrdersSortByExecutionDate(Date startPeriodDate, Date endPeriodDate);

    List<Order> getCanceledOrdersSortByPrice(Date startPeriodDate, Date endPeriodDate);

    List<Order> getDeletedOrdersSortByFilingDate(Date startPeriodDate, Date endPeriodDate);

    List<Order> getDeletedOrdersSortByExecutionDate(Date startPeriodDate, Date endPeriodDate);

    List<Order> getDeletedOrdersSortByPrice(Date startPeriodDate, Date endPeriodDate);

    List<Order> getMasterOrders(Master master);

    Long getNumberOrders();

    List<Master> getOrderMasters(Order order);
}