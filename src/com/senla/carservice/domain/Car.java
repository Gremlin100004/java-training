package com.senla.carservice.domain;

public class Car {
    private Long id;
    private String automaker;
    private String model;
    private String registrationNumber;

    public Car() {
    }

    public Car(String automaker, String model, String registrationNumber) {
        this.automaker = automaker;
        this.model = model;
        this.registrationNumber = registrationNumber;
    }

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Car{" +
                "automaker='" + automaker + '\'' +
                ", model='" + model + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                '}';
    }
}