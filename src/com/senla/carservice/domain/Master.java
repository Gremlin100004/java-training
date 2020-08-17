package com.senla.carservice.domain;

public class Master extends AEntity {
    private String name;
    private Boolean isDelete;

    public Master() {
    }

    public Master(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    @Override
    public String toString() {
        return "Master{" +
               "name='" + name + '\'' +
               '}';
    }
}