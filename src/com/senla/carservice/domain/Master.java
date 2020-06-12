package com.senla.carservice.domain;

public class Master  {
    private String name;
    private Integer numberOrder;

    public Master(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Master{" +
                "name='" + name + '\'' +
                ", numberOrder=" + numberOrder +
                '}';
    }

    public Integer getNumberOrder() {
        return numberOrder;
    }

    public void setNumberOrder(int numberOrder) {
        this.numberOrder = numberOrder;
    }
}