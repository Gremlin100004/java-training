package com.senla.carservice.hibernatedao;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import org.hibernate.Session;

import java.util.List;

public interface OrderDao extends GenericDao<Order> {

    Order getLastOrder(Session session);

    int getNumberBusyMasters(String startPeriod, String endPeriod, Session session);

    int getNumberBusyPlaces(String startPeriod, String endPeriod, Session session);

    void addRecordToTableManyToMany(Order order, Session session);

    List<Order> getOrdersSortByFilingDate(Session session);

    List<Order> getOrdersSortByExecutionDate(Session session);

    List<Order> getOrdersSortByPlannedStartDate(Session session);

    List<Order> getOrdersSortByPrice(Session session);

    List<Order> getExecuteOrderSortByFilingDate(Session session);

    List<Order> getExecuteOrderSortExecutionDate(Session session);

    List<Order> getCompletedOrdersSortByFilingDate(String startPeriodDate, String endPeriodDate,
                                                   Session session);

    List<Order> getCompletedOrdersSortByExecutionDate(String startPeriodDate, String endPeriodDate,
                                                      Session session);

    List<Order> getCompletedOrdersSortByPrice(String startPeriodDate, String endPeriodDate,
                                              Session session);

    List<Order> getCanceledOrdersSortByFilingDate(String startPeriodDate, String endPeriodDate,
                                                  Session session);

    List<Order> getCanceledOrdersSortByExecutionDate(String startPeriodDate, String endPeriodDate,
                                                     Session session);

    List<Order> getCanceledOrdersSortByPrice(String startPeriodDate, String endPeriodDate,
                                             Session session);

    List<Order> getDeletedOrdersSortByFilingDate(String startPeriodDate, String endPeriodDate,
                                                 Session session);

    List<Order> getDeletedOrdersSortByExecutionDate(String startPeriodDate, String endPeriodDate,
                                                    Session session);

    List<Order> getDeletedOrdersSortByPrice(String startPeriodDate, String endPeriodDate,
                                            Session session);

    List<Order> getMasterOrders(Master master, Session session);

    int getNumberOrders(Session session);

    Order getOrderById(Long index, Session session);
}