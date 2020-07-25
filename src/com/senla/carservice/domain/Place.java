package com.senla.carservice.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Place extends AEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer number;
    private Boolean busyStatus;
    private List<Order> orders = new ArrayList<>();

    public Place() {
    }

    public Place(Integer number) {
        this.number = number;
        this.busyStatus = false;
    }

    public Integer getNumber() {
        return number;
    }

    public Boolean getBusyStatus() {
        return busyStatus;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setNumber(final Integer number) {
        this.number = number;
    }

    public void setBusyStatus(final Boolean busyStatus) {
        this.busyStatus = busyStatus;
    }

    public void setOrders(final List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Place{" +
               "number=" + number +
               ", busyStatus=" + busyStatus +
               ", orders=" + orders +
               '}';
    }
}