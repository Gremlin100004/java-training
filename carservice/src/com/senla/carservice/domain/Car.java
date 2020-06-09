package com.senla.carservice.domain;

// доменная модель не должна имплементить интерфейсы
public class Car implements ICar {
    private String automaker;
    private String model;
    private String registrationNumber;

    public Car(String automaker, String model, String registrationNumber) {
        this.automaker = automaker;
        this.model = model;
        this.registrationNumber = registrationNumber;
    }

    @Override
    public String getAutomaker() {
        return automaker;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public String getRegistrationNumber() {
        return registrationNumber;
    }
}