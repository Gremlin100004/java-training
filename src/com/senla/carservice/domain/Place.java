package com.senla.carservice.domain;

public class Place {
    private Long id;
    private Boolean busyStatus;

    public Place() {
        this.busyStatus = false;
    }

    public Long getId() {
        return id;
    }

    public Boolean isBusyStatus() {
        return busyStatus;
    }

    public void setBusyStatus(Boolean busyStatus) {
        this.busyStatus = busyStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }
}