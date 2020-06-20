package com.senla.carservice.repository;

import com.senla.carservice.domain.Order;
import com.senla.carservice.util.IdGenerator;

import java.util.List;

public interface OrderRepository {
    IdGenerator getIdGeneratorOrder();

    List<Order> getOrders();

    void setOrders(List<Order> orders);
}