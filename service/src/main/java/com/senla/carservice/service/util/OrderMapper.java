package com.senla.carservice.service.util;

import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.dao.PlaceDao;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.enumaration.StatusOrder;
import com.senla.carservice.dto.OrderDto;
import com.senla.carservice.util.DateUtil;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static List<OrderDto> getOrderDto(List<Order> orders) {
        return orders.stream()
            .map(OrderMapper::getOrderDto)
            .collect(Collectors.toList());
    }

    public static OrderDto getOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setAutomaker(order.getAutomaker());
        orderDto.setModel(order.getModel());
        orderDto.setRegistrationNumber(order.getRegistrationNumber());
        orderDto.setCreationTime(DateUtil.getStringFromDate(order.getCreationTime(), true));
        orderDto.setExecutionStartTime(DateUtil.getStringFromDate(order.getExecutionStartTime(), true));
        orderDto.setLeadTime(DateUtil.getStringFromDate(order.getLeadTime(), true));
        orderDto.setStatus(String.valueOf(order.getStatus()));
        orderDto.setPrice(order.getPrice());
        orderDto.setDeleteStatus(order.isDeleteStatus());
        return orderDto;
    }

    public static Order getOrder(OrderDto orderDto, MasterDao masterDao, PlaceDao placeDao) {
        Order order = new Order();
        if (orderDto.getId() != null) {
            order.setId(orderDto.getId());
        }
        order.setAutomaker(orderDto.getAutomaker());
        order.setModel(orderDto.getModel());
        order.setRegistrationNumber(orderDto.getRegistrationNumber());
        if (orderDto.getCreationTime() != null) {
            order.setCreationTime(DateUtil.getDatesFromString(orderDto.getCreationTime(), true));
        }
        if (orderDto.getExecutionStartTime() != null) {
            order.setExecutionStartTime(DateUtil.getDatesFromString(orderDto.getExecutionStartTime(), true));
        }
        if (orderDto.getLeadTime() != null) {
            order.setLeadTime(DateUtil.getDatesFromString(orderDto.getLeadTime(), true));
        }
        if (orderDto.getStatus() != null) {
            order.setStatus(StatusOrder.valueOf(orderDto.getStatus()));
        }
        order.setPrice(orderDto.getPrice());
        order.setDeleteStatus(orderDto.isDeleteStatus());
        if (orderDto.getPlace() != null) {
            order.setPlace(PlaceMapper.getPlace(orderDto.getPlace(), placeDao));
        }
        if (orderDto.getMasters() != null) {
            order.setMasters(MasterMapper.getMaster(orderDto.getMasters(), masterDao));
        }
        return order;
    }

}
