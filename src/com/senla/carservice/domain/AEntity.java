package com.senla.carservice.domain;

import java.io.Serializable;
import java.util.Objects;

public abstract class AEntity implements Serializable {
    private static final long serialVersionUID = 1L;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AEntity aEntity = (AEntity) o;
        return id.equals(aEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}