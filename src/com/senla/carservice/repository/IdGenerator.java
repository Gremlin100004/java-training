package com.senla.carservice.repository;

import com.senla.carservice.factory.annotation.Prototype;

import java.io.Serializable;

@Prototype
public class IdGenerator implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    public IdGenerator() {
        this.id = 0L;
    }

    public Long getId() {
        return this.id++;
    }

    public void setId(Long id) {
        this.id = id;
    }
}