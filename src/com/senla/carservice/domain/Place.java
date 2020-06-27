package com.senla.carservice.domain;

public class Place extends AEntity {
    private Integer number;
    private Boolean busyStatus;

    public Place() {
    }

    public Place(Integer number) {
        this.number = number;
        this.busyStatus = false;
    }

    public Integer getNumber() {
        return number;
    }

    public Boolean isBusyStatus() {
        return busyStatus;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setBusyStatus(Boolean busyStatus) {
        this.busyStatus = busyStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return super.getId().equals(place.getId());
    }
}