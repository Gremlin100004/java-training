package com.senla.carservice.dao;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;

import java.util.List;

public interface OrderDao extends GenericDao <Order> {

    Order getLastOrder();

    int getNumberBusyMasters(String startPeriod, String endPeriod);

    int getNumberBusyPlaces(String startPeriod, String endPeriod);

    void addRecordToTableManyToMany(Order order);

    List<Order> getOrdersSortByFilingDate();

    List<Order> getOrdersSortByExecutionDate();

    List<Order> getOrdersSortByPlannedStartDate();

    List<Order> getOrdersSortByPrice();

    List<Order> getExecuteOrderSortByFilingDate();

    List<Order> getExecuteOrderSortExecutionDate();

    List<Order> getCompletedOrdersSortByFilingDate(String startPeriodDate, String endPeriodDate);

    List<Order> getCompletedOrdersSortByExecutionDate(String startPeriodDate, String endPeriodDate);

    List<Order> getCompletedOrdersSortByPrice(String startPeriodDate, String endPeriodDate);

    List<Order> getCanceledOrdersSortByFilingDate(String startPeriodDate, String endPeriodDate);

    List<Order> getCanceledOrdersSortByExecutionDate(String startPeriodDate, String endPeriodDate);

    List<Order> getCanceledOrdersSortByPrice(String startPeriodDate, String endPeriodDate);

    List<Order> getDeletedOrdersSortByFilingDate(String startPeriodDate, String endPeriodDate);

    List<Order> getDeletedOrdersSortByExecutionDate(String startPeriodDate, String endPeriodDate);

    List<Order> getDeletedOrdersSortByPrice(String startPeriodDate, String endPeriodDate);

    List<Order> getMasterOrders(Master master);
}