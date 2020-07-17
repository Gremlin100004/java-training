package com.senla.carservice.domain;

import java.util.ArrayList;
import java.util.List;

// считается хорошей практикой имплементить явно интерфейс Serializable
// в каждом классе, а не только в родительском (в родительском тоже надо),
// определяя также отдельно в каждом классе serialVersionUID
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