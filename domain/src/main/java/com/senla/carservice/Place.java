package com.senla.carservice;

public class Place extends AEntity {
    private Integer number;
    private Boolean busyStatus;
    private Boolean deleteStatus;

    public Place() {
    }

    public Place(Integer number) {
        this.number = number;
        this.busyStatus = false;
        this.deleteStatus = false;
    }

    public Integer getNumber() {
        return number;
    }

    public Boolean getBusyStatus() {
        return busyStatus;
    }

    public Boolean getDelete() {
        return deleteStatus;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setBusyStatus(Boolean busyStatus) {
        this.busyStatus = busyStatus;
    }

    public void setDelete(Boolean delete) {
        deleteStatus = delete;
    }

    @Override
    public String toString() {
        return "Place{" +
               "number=" + number +
               ", busyStatus=" + busyStatus +
               '}';
    }
}