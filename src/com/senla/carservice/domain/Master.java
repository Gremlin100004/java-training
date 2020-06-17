package com.senla.carservice.domain;

import java.util.Objects;

public class Master extends AEntity {
    private Long id;
    private String name;
    private Integer numberOrder;

    public Master() {
    }

    public Master(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getNumberOrder() {
        return numberOrder;
    }

    public void setNumberOrder(int numberOrder) {
        this.numberOrder = numberOrder;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberOrder(Integer numberOrder) {
        this.numberOrder = numberOrder;
    }

    @Override
    public String toString() {
        return "Master{" +
                "name='" + name + '\'' +
                ", numberOrder=" + numberOrder +
                '}';
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Master master = (Master) o;
        return id.equals(master.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}