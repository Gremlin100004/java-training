package com.senla.carservice.ui.client;

import com.senla.carservice.dto.DateDto;
import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;

import java.util.List;

public interface OrderClient {
    String addOrder(OrderDto orderDto);

    List<OrderDto> getOrders();

    String completeOrder(Long idOrder);

    String closeOrder(Long idOrder);

    String cancelOrder(Long idOrder);

    String deleteOrder(Long idOrder);

    String shiftLeadTime(OrderDto orderDto);

    List<OrderDto> getSortOrders(String sortParameter);

    List<OrderDto> getSortOrdersByPeriod(String sortParameter, String startPeriod, String endPeriod);

    List<MasterDto> getOrderMasters(Long orderId);

    DateDto getNearestFreeDate();

    String exportEntities();

    String importEntities();

}
