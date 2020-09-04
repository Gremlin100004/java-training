package com.senla.carservice.domain;

import java.util.Objects;

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

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AEntity aEntity = (AEntity) object;
        return id.equals(aEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}