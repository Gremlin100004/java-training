package com.senla.carservice.repository;

import com.senla.carservice.domain.Car;
import com.senla.carservice.domain.Order;
import com.senla.carservice.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {
    private static OrderRepository instance;
    private List<Order> orders;
    private final IdGenerator idGeneratorOrder;
    private final IdGenerator idGeneratorCar;

    private OrderRepositoryImpl() {
        this.orders = new ArrayList<>();
        this.idGeneratorOrder = new IdGenerator();
        this.idGeneratorCar = new IdGenerator();
    }

    public static OrderRepository getInstance() {
        if (instance == null) {
            instance = new OrderRepositoryImpl();
        }
        return instance;
    }

    @Override
    public IdGenerator getIdGeneratorOrder() {
        return idGeneratorOrder;
    }

    @Override
    public IdGenerator getIdGeneratorCar() {
        return idGeneratorCar;
    }

    @Override
    public List<Order> getOrders() {
        return this.orders;
    }

    @Override
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public List<Car> getCars() {
        List<Car> cars = new ArrayList<>();
        this.getOrders().forEach(order -> cars.add(order.getCar()));
        return cars;
    }
}