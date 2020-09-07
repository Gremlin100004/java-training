package com.senla.carservice.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "masters")
public class Master extends AEntity {

    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "masters", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
    @Column(name = "number_orders")
    private Integer numberOrders;
    @Column(name = "is_deleted")
    private Boolean deleteStatus;

    public Master() {
    }

    public Master(String name) {
        this.name = name;
        deleteStatus = false;
        numberOrders = 0;
    }

    public String getName() {
        return name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeleteStatus(Boolean delete) {
        deleteStatus = delete;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Integer getNumberOrders() {
        return numberOrders;
    }

    public void setNumberOrders(final Integer numberOrders) {
        this.numberOrders = numberOrders;
    }

    @Override
    public String toString() {
        return "Master{" + "name='" + name + '\'' + ", numberOrders=" + numberOrders + ", deleteStatus=" +
               deleteStatus + '}';
    }
}