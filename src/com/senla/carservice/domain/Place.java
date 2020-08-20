package com.senla.carservice.domain;

public class Place extends AEntity {
    private Integer number;
    private Boolean busyStatus;
    private Boolean isDelete;

    public Place() {
    }

    public Place(Integer number) {
        this.number = number;
        this.busyStatus = false;
        this.isDelete = false;
    }

    public Integer getNumber() {
        return number;
    }

    public Boolean getBusyStatus() {
        return busyStatus;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setBusyStatus(Boolean busyStatus) {
        this.busyStatus = busyStatus;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    @Override
    public String toString() {
        return "Place{" +
               "number=" + number +
               ", busyStatus=" + busyStatus +
               '}';
    }
}