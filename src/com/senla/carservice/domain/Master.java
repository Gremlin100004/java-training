package com.senla.carservice.domain;

import java.util.Objects;

public class Master extends AEntity {
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
}