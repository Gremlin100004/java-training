package com.senla.carservice.service.util;

import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.dao.PlaceDao;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.enumaration.StatusOrder;
import com.senla.carservice.dto.OrderDto;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static List<OrderDto> transferDataFromOrderToOrderDto(List<Order> orders) {
        return orders.stream()
            .map(OrderMapper::transferDataFromOrderToOrderDto)
            .collect(Collectors.toList());
    }

    public static OrderDto transferDataFromOrderToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setAutomaker(order.getAutomaker());
        orderDto.setModel(order.getModel());
        orderDto.setRegistrationNumber(order.getRegistrationNumber());
        orderDto.setCreationTime(order.getCreationTime());
        orderDto.setExecutionStartTime(order.getExecutionStartTime());
        orderDto.setLeadTime(order.getLeadTime());
        orderDto.setStatus(String.valueOf(order.getStatus()));
        orderDto.setPrice(order.getPrice());
        orderDto.setDeleteStatus(order.isDeleteStatus());
        return orderDto;
    }

    public static Order transferDataFromOrderDtoToOrder(OrderDto orderDto, MasterDao masterDao, PlaceDao placeDao) {
        Order order = new Order();
        if (orderDto.getId() != null) {
            order.setId(orderDto.getId());
        }
        order.setAutomaker(orderDto.getAutomaker());
        order.setModel(orderDto.getModel());
        order.setRegistrationNumber(orderDto.getRegistrationNumber());
        order.setCreationTime(orderDto.getCreationTime());
        order.setExecutionStartTime(orderDto.getExecutionStartTime());
        order.setLeadTime(orderDto.getLeadTime());
        if (orderDto.getStatus() != null) {
            order.setStatus(StatusOrder.valueOf(orderDto.getStatus()));
        }
        order.setPrice(orderDto.getPrice());
        order.setDeleteStatus(orderDto.isDeleteStatus());
        if (orderDto.getPlace() != null) {
            order.setPlace(PlaceMapper.transferDataFromPlaceDtoToPLace(orderDto.getPlace(), placeDao));
        }
        if (orderDto.getMasters() != null) {
            order.setMasters(MasterMapper.transferDataFromMasterDtoToMaster(orderDto.getMasters(), masterDao));
        }
        return order;
    }
}