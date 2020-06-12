package com.senla.carservice.domain;

public class Place {
    private Long id;
    private boolean busyStatus;

    public Place() {
        this.busyStatus = false;
    }

    public Long getId() {
        return id;
    }

    public boolean isBusyStatus() {
        return busyStatus;
    }

    public void setBusyStatus(boolean busyStatus) {
        this.busyStatus = busyStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }
}