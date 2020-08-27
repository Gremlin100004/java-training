package com.senla.carservice.dao;

import com.senla.carservice.dao.connection.DatabaseConnection;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;

import java.util.List;

public interface OrderDao extends GenericDao <Order> {

    Order getLastOrder(DatabaseConnection databaseConnection);

    int getNumberBusyMasters(String startPeriod, String endPeriod, DatabaseConnection databaseConnection);

    int getNumberBusyPlaces(String startPeriod, String endPeriod, DatabaseConnection databaseConnection);

    void addRecordToTableManyToMany(Order order, DatabaseConnection databaseConnection);

    List<Order> getOrdersSortByFilingDate(DatabaseConnection databaseConnection);

    List<Order> getOrdersSortByExecutionDate(DatabaseConnection databaseConnection);

    List<Order> getOrdersSortByPlannedStartDate(DatabaseConnection databaseConnection);

    List<Order> getOrdersSortByPrice(DatabaseConnection databaseConnection);

    List<Order> getExecuteOrderSortByFilingDate(DatabaseConnection databaseConnection);

    List<Order> getExecuteOrderSortExecutionDate(DatabaseConnection databaseConnection);

    List<Order> getCompletedOrdersSortByFilingDate(String startPeriodDate, String endPeriodDate,
                                                   DatabaseConnection databaseConnection);

    List<Order> getCompletedOrdersSortByExecutionDate(String startPeriodDate, String endPeriodDate,
                                                      DatabaseConnection databaseConnection);

    List<Order> getCompletedOrdersSortByPrice(String startPeriodDate, String endPeriodDate,
                                              DatabaseConnection databaseConnection);

    List<Order> getCanceledOrdersSortByFilingDate(String startPeriodDate, String endPeriodDate,
                                                  DatabaseConnection databaseConnection);

    List<Order> getCanceledOrdersSortByExecutionDate(String startPeriodDate, String endPeriodDate,
                                                     DatabaseConnection databaseConnection);

    List<Order> getCanceledOrdersSortByPrice(String startPeriodDate, String endPeriodDate,
                                             DatabaseConnection databaseConnection);

    List<Order> getDeletedOrdersSortByFilingDate(String startPeriodDate, String endPeriodDate,
                                                 DatabaseConnection databaseConnection);

    List<Order> getDeletedOrdersSortByExecutionDate(String startPeriodDate, String endPeriodDate,
                                                    DatabaseConnection databaseConnection);

    List<Order> getDeletedOrdersSortByPrice(String startPeriodDate, String endPeriodDate,
                                            DatabaseConnection databaseConnection);

    List<Order> getMasterOrders(Master master, DatabaseConnection databaseConnection);
}