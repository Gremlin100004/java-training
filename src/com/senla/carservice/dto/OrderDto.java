package com.senla.carservice.dto;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Place;

import java.math.BigDecimal;
import java.util.ArrayList;

// паттерн ДТО используется для обмена данными между юай и контроллерами
// дто - это как модель, только облегченная, содержит только нужные поля
// или реализуешь общение между юай и контроллерами только через ДТО, или не используешь ДТО вообще
public class OrderDto {
    private String executionStartTime;
    private String leadTime;
    private ArrayList<Master> masters;
    private Garage garage;
    private Place place;
    private String automaker;
    private String model;
    private String registrationNumber;
    private BigDecimal price;

    public OrderDto(String executionStartTime, String leadTime, ArrayList<Master> masters, Garage garage, Place place,
                    String automaker, String model, String registrationNumber, BigDecimal price) {
        this.executionStartTime = executionStartTime;
        this.leadTime = leadTime;
        this.masters = masters;
        this.garage = garage;
        this.place = place;
        this.automaker = automaker;
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.price = price;
    }

    public String getExecutionStartTime() {
        return executionStartTime;
    }

    public String getLeadTime() {
        return leadTime;
    }

    public ArrayList<Master> getMasters() {
        return masters;
    }

    public Garage getGarage() {
        return garage;
    }

    public Place getPlace() {
        return place;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setExecutionStartTime(String executionStartTime) {
        this.executionStartTime = executionStartTime;
    }

    public void setLeadTime(String leadTime) {
        this.leadTime = leadTime;
    }

    public void setMasters(ArrayList<Master> masters) {
        this.masters = masters;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public void setAutomaker(String automaker) {
        this.automaker = automaker;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}