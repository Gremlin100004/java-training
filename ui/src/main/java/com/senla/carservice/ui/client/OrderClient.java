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

    String getSortOrders(String sortParameter);

    String getSortOrdersByPeriod(String sortParameter, String startPeriod, String endPeriod);

    String getOrderMasters(Long orderId);

}
