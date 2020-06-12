package com.senla.carservice.domain;

public class Place {
    private boolean busyStatus;

    public Place() {
        this.busyStatus = false;
    }


    public boolean isBusyStatus() {
        return busyStatus;
    }


    public void setBusyStatus(boolean busyStatus) {
        this.busyStatus = busyStatus;
    }
}