package com.senla.carservice.repository;

public class GeneratorId implements IGeneratorId {
    private Long id;

    public GeneratorId() {
        this.id = 0L;
    }

    @Override
    public Long getId() {
        return this.id++;
    }
}