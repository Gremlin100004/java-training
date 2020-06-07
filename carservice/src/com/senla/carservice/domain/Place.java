package com.senla.carservice.domain;

public class Place implements IPlace {
    private boolean busyStatus;

    public Place() {
        this.busyStatus = false;
    }

    @Override
    public boolean isBusyStatus() {
        return busyStatus;
    }

    @Override
    public void setBusyStatus(boolean busyStatus) {
        this.busyStatus = busyStatus;
    }
}