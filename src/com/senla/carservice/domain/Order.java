package com.senla.carservice.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Order extends AEntity {
    private final Date creationTime;
    private Date executionStartTime;
    private Date leadTime;
    private List<Master> masters;
    private Garage garage;
    private Place place;
    private Car car;
    private BigDecimal price;
    private Status status;
    private boolean deleteStatus;

    public Order(Long id, Car car) {
        super.setId(id);
        this.creationTime = new Date();
        this.car = car;
        this.status = Status.WAIT;
        this.deleteStatus = false;
    }

    public List<Master> getMasters() {
        return masters;
    }

    public Car getCar() {
        return car;
    }

    public Status getStatus() {
        return status;
    }

    public Garage getGarage() {
        return this.garage;
    }

    public Place getPlace() {
        return place;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public Date getExecutionStartTime() {
        return executionStartTime;
    }

    public Date getLeadTime() {
        return leadTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isDeleteStatus() {
        return deleteStatus;
    }

    public void setLeadTime(Date leadTime) {
        this.leadTime = leadTime;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    public void setMasters(List<Master> masters) {
        this.masters = masters;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public void setExecutionStartTime(Date executionStartTime) {
        this.executionStartTime = executionStartTime;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return super.getId().equals(order.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId());
    }
}