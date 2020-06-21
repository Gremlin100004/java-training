package com.senla.carservice.domain;

public abstract class AEntity {
    private Long id;

    public AEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}