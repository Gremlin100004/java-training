package com.senla.carservice.ui.client;

import com.senla.carservice.dto.OrderDto;

import java.util.List;

public interface OrderClient {
    String addOrder(OrderDto orderDto);

    String checkOrderDeadlines(String stringExecutionStartTime, String stringLeadTime);

    String checkOrders();

    List<OrderDto> getOrders();

    String completeOrder(Long idOrder);

    String closeOrder(Long idOrder);

    String cancelOrder(Long idOrder);

    String deleteOrder(Long idOrder);

    String shiftLeadTime(OrderDto orderDto);

    String getOrdersSortByFilingDate();

    String getOrdersSortByExecutionDate();

    String getOrdersSortByPlannedStartDate();

    String getOrdersSortByPrice();

    String getExecuteOrderFilingDate();

    String getExecuteOrderExecutionDate();

    String getCompletedOrdersFilingDate(String startPeriod, String endPeriod);

    String getCompletedOrdersExecutionDate(String startPeriod, String endPeriod);

    String getCompletedOrdersPrice(String startPeriod, String endPeriod);

    String getCanceledOrdersFilingDate(String startPeriod, String endPeriod);

    String getCanceledOrdersExecutionDate(String startPeriod, String endPeriod);

    String getCanceledOrdersPrice(String startPeriod, String endPeriod);

    String getDeletedOrdersFilingDate(String startPeriod, String endPeriod);

    String getDeletedOrdersExecutionDate(String startPeriod, String endPeriod);

    String getDeletedOrdersPrice(String startPeriod, String endPeriod);

    String getOrderMasters(Long orderId);

}
