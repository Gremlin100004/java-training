package com.senla.carservice.domain;

public class Master extends AEntity {
    private String name;
    private Boolean deleteStatus;
    private Integer numberOrders;

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

    public Boolean getDelete() {
        return deleteStatus;
    }

    public Integer getNumberOrders() {
        return numberOrders;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDelete(Boolean delete) {
        deleteStatus = delete;
    }

    public void setNumberOrders(final Integer numberOrders) {
        this.numberOrders = numberOrders;
    }

    @Override
    public String toString() {
        return "Master{" +
               "name='" + name + '\'' +
               ", isDelete=" + deleteStatus +
               ", numberOrders=" + numberOrders +
               '}';
    }
}