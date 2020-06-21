package com.senla.carservice.domain;

import java.util.Objects;

public class Place extends AEntity {
    private Boolean busyStatus;

    public Place() {
        this.busyStatus = false;
    }

    public Boolean isBusyStatus() {
        return busyStatus;
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

    @Override
    public int hashCode() {
        return Objects.hash(super.getId());
    }
}