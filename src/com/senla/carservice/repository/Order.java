package com.senla.carservice.repository;

import com.senla.carservice.domain.*;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    private Date creationTime;
    private Date executionStartTime;
    private Date leadTime;
    private Master[] masters;
    private Garage garage;
    private Place place;
    private Car car;
    private BigDecimal price;
    private Status status;
    private boolean deleteStatus;

    public Order(Date executionStartTime, Date leadTime, Master[] masters, Garage garage, Place place,
                 Car car, BigDecimal price) {
        this.creationTime = new Date();
        this.executionStartTime = executionStartTime;
        this.leadTime = leadTime;
        this.masters = masters;
        this.garage = garage;
        this.place = place;
        this.car = car;
        this.price = price;
        this.status = Status.WAIT;
        this.deleteStatus = false;
    }

    public Master[] getMasters() {
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

    public void setMasters(Master[] masters) {
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
}