package com.senla.carservice.hibernatedao;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public interface OrderDao extends GenericDao<Order, Long> {
    Order getLastOrder(Session session);

    Long getNumberBusyMasters(Date startPeriod, Date endPeriod, Session session);

    Long getNumberBusyPlaces(Date startPeriod, Date endPeriod, Session session);

    List<Order> getOrdersSortByFilingDate(Session session);

    List<Order> getOrdersSortByExecutionDate(Session session);

    List<Order> getOrdersSortByPlannedStartDate(Session session);

    List<Order> getOrdersSortByPrice(Session session);

    List<Order> getExecuteOrderSortByFilingDate(Session session);

    List<Order> getExecuteOrderSortExecutionDate(Session session);

    List<Order> getCompletedOrdersSortByFilingDate(Date startPeriodDate, Date endPeriodDate, Session session);

    List<Order> getCompletedOrdersSortByExecutionDate(Date startPeriodDate, Date endPeriodDate, Session session);

    List<Order> getCompletedOrdersSortByPrice(Date startPeriodDate, Date endPeriodDate, Session session);

    List<Order> getCanceledOrdersSortByFilingDate(Date startPeriodDate, Date endPeriodDate, Session session);

    List<Order> getCanceledOrdersSortByExecutionDate(Date startPeriodDate, Date endPeriodDate, Session session);

    List<Order> getCanceledOrdersSortByPrice(Date startPeriodDate, Date endPeriodDate, Session session);

    List<Order> getDeletedOrdersSortByFilingDate(Date startPeriodDate, Date endPeriodDate, Session session);

    List<Order> getDeletedOrdersSortByExecutionDate(Date startPeriodDate, Date endPeriodDate, Session session);

    List<Order> getDeletedOrdersSortByPrice(Date startPeriodDate, Date endPeriodDate, Session session);

    List<Order> getMasterOrders(Master master, Session session);

    Long getNumberOrders(Session session);

    Order getOrderById(Long index, Session session);
}