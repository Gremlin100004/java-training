package com.senla.carservice.repository;

import java.io.Serializable;

public class IdGenerator implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    public IdGenerator() {
        this.id = 0L;
    }

    public Long getId() {
        return this.id++;
    }
}