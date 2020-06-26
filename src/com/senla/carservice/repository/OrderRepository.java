package com.senla.carservice.repository;

import com.senla.carservice.domain.Car;
import com.senla.carservice.domain.Order;
import com.senla.carservice.util.IdGenerator;

import java.util.List;

public interface OrderRepository {
    IdGenerator getIdGeneratorOrder();

    IdGenerator getIdGeneratorCar();

    List<Order> getOrders();

    // такого метода быть здесь не должно
    void setOrders(List<Order> orders);

    List<Car> getCars();
}