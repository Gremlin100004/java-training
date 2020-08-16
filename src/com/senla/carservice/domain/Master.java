package com.senla.carservice.domain;

import java.util.ArrayList;
import java.util.List;

public class Master extends AEntity {
    private String name;
    private List<Order> orders = new ArrayList<>();

    public Master() {
    }

    public Master(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Master{" +
               "name='" + name + '\'' +
               ", orders=" + orders +
               '}';
    }
}