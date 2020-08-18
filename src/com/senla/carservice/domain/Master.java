package com.senla.carservice.domain;

public class Master extends AEntity {
    private String name;
    private Boolean isDelete;
    private Integer numberOrders;

    public Master() {
    }

    public Master(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public Integer getNumberOrders() {
        return numberOrders;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public void setNumberOrders(final Integer numberOrders) {
        this.numberOrders = numberOrders;
    }

    @Override
    public String toString() {
        return "Master{" +
               "name='" + name + '\'' +
               ", isDelete=" + isDelete +
               ", numberOrders=" + numberOrders +
               '}';
    }
}