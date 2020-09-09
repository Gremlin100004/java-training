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
    private Boolean isDelete;


    public Place() {
    }

    public Place(Integer number) {
        this.number = number;
        this.isBusy = false;
        this.isDelete = false;
    }

    public Integer getNumber() {
        return number;
    }

    public Boolean getBusy() {
        return isBusy;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setBusy(Boolean isBusy) {
        this.isBusy = isBusy;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    @Override
    public String toString() {
        return "Place{" + "number=" + number + ", isBusy=" + isBusy + ", isDelete=" + isDelete + '}';
    }
}