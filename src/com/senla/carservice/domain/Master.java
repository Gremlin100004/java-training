package com.senla.carservice.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Master extends AEntity implements Serializable {
    private static final long serialVersionUID = 1L;
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

    public void setName(final String name) {
        this.name = name;
    }

    public void setOrders(final List<Order> orders) {
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