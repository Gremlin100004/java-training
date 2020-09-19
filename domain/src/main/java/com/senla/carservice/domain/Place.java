package com.senla.carservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "places")

public class Place extends AEntity {

    @Column(name = "number")
    private Integer number;
    @Column(name = "is_busy")
    private Boolean isBusy;
    @Column(name = "is_deleted")
    private Boolean deleteStatus;


    public Place() {
    }

    public Place(Integer number) {
        this.number = number;
        this.isBusy = false;
        this.deleteStatus = false;
    }

    public Integer getNumber() {
        return number;
    }

    public Boolean getBusy() {
        return isBusy;
    }

    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setBusy(Boolean isBusy) {
        this.isBusy = isBusy;
    }

    public void setDeleteStatus(Boolean delete) {
        deleteStatus = delete;
    }

    @Override
    public String toString() {
        return "Place{" + "number=" + number + ", isBusy=" + isBusy + ", isDelete=" + deleteStatus + '}';
    }
}