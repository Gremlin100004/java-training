package com.senla.carservice.domain;

import java.util.Objects;

public class Place extends AEntity {
    private Long id;
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
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return id.equals(place.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}