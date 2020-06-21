package com.senla.carservice.domain;

import java.util.Objects;

public class Car extends AEntity {
    private String automaker;
    private String model;
    private String registrationNumber;

    public Car() {
    }

    public Car(Long id, String automaker, String model, String registrationNumber) {
        super.setId(id);
        this.automaker = automaker;
        this.model = model;
        this.registrationNumber = registrationNumber;
    }

    public String getAutomaker() {
        return automaker;
    }

    public String getModel() {
        return model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    @Override
    public String toString() {
        return "Car{" +
                "automaker='" + automaker + '\'' +
                ", model='" + model + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return super.getId().equals(car.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId());
    }
}