package com.senla.carservice.domain;

import com.senla.carservice.domain.enumaration.Status;

import java.math.BigDecimal;
import java.util.Date;

public class Order extends AEntity {
    private Date creationTime;
    private Date executionStartTime;
    private Date leadTime;
    private Place place;
    private String automaker;
    private String model;
    private String registrationNumber;
    private BigDecimal price;
    private Status status;
    private boolean deleteStatus;

    public Order(String automaker, String model, String registrationNumber) {
        this.automaker = automaker;
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.status = Status.WAIT;
        this.deleteStatus = false;
    }

    public String getAutomaker() {
        return automaker;
    }

    public String getModel() {
        return model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public Status getStatus() {
        return status;
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

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public void setLeadTime(Date leadTime) {
        this.leadTime = leadTime;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public void setAutomaker(String automaker) {
        this.automaker = automaker;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
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
    public String toString() {
        return "Order{" +
                "creationTime=" + creationTime +
                ", executionStartTime=" + executionStartTime +
                ", leadTime=" + leadTime +
                ", place=" + place +
                ", automaker='" + automaker + '\'' +
                ", model='" + model + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", price=" + price +
                ", status=" + status +
                ", deleteStatus=" + deleteStatus +
                '}';
    }
}