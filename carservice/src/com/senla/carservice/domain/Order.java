package com.senla.carservice.domain;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Order implements IOrder {
    /*
    Читай общий чат, пожалуйста
    Вопрос: Как хранить дату?
    Ответ: Для даты используем джава класс java.util.Date

    Календарь можно использовать для вычисления дат
     */
    private Calendar creationTime;
    private Calendar executionStartTime;
    private Calendar leadTime;
    private IMaster[] masters;
    private IGarage garage;
    private IPlace place;
    private ICar car;
    private BigDecimal price;
    private String status;
    private boolean deleteStatus;

    public Order(Calendar executionStartTime, Calendar leadTime, IMaster[] masters, IGarage garage,
                 IPlace place, ICar car, BigDecimal price) {
        this.creationTime = new GregorianCalendar();
        this.executionStartTime = executionStartTime;
        this.leadTime = leadTime;
        this.masters = masters;
        this.garage = garage;
        this.place = place;
        this.car = car;
        this.price = price;
        // для хранения статусов нужно использовать ENUM
        this.status = "wait";
        this.deleteStatus = false;
    }

    @Override
    public IMaster[] getMasters() {
        return masters;
    }

    @Override
    public ICar getCar() {
        return car;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public IGarage getGarage() {
        return this.garage;
    }

    @Override
    public IPlace getPlace() {
        return place;
    }

    @Override
    public Calendar getCreationTime() {
        return creationTime;
    }

    @Override
    public Calendar getExecutionStartTime() {
        return executionStartTime;
    }

    @Override
    public Calendar getLeadTime() {
        return leadTime;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean isDeleteStatus() {
        return deleteStatus;
    }

    @Override
    public void setLeadTime(Calendar leadTime) {
        this.leadTime = leadTime;
    }

    @Override
    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    @Override
    public void setPlace(Place place) {
        this.place = place;
    }

    @Override
    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    @Override
    public void setExecutionStartTime(Calendar executionStartTime) {
        this.executionStartTime = executionStartTime;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}