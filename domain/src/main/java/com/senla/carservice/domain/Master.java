package com.senla.carservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "masters")
public class Master extends AEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "masters")
    private List<Order> orders;
    @Column(name = "is_deleted")
    private Boolean deleteStatus;

    public Master() {
    }

    public Master(String name) {
        this.name = name;
        orders = new ArrayList<>();
        deleteStatus = false;
    }

    public String getName() {
        return name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Boolean getDelete() {
        return deleteStatus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDelete(Boolean delete) {
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