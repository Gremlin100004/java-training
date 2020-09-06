package com.senla.carservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "masters")
public class Master extends AEntity {

    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "masters")
    private List<Order> orders = new ArrayList<>();
    @Column(name = "is_deleted")
    private Boolean deleteStatus;

    public Master() {
    }

    public Master(String name) {
        this.name = name;
        deleteStatus = false;
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

    @Override
    public String toString() {
        return "Master{" + "name='" + name + '\'' + ", orders=" + orders + ", deleteStatus=" + deleteStatus + '}';
    }
}