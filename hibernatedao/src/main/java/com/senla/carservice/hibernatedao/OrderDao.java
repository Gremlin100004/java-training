package com.senla.carservice.hibernatedao;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public interface OrderDao extends GenericDao<Order, Long> {
    Order getLastOrder();

    Long getNumberBusyMasters(Date startPeriod, Date endPeriod);

    Long getNumberBusyPlaces(Date startPeriod, Date endPeriod);

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

    Order getOrderById(Long index);
}