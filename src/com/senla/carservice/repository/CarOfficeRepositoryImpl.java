package com.senla.carservice.repository;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.util.IdGenerator;

public class CarOfficeRepositoryImpl implements CarOfficeRepository {
    private String name;
    private Order[] orders;
    private Master[] masters;
    private Garage[] garages;
    private final IdGenerator idGeneratorGarage;
    private final IdGenerator idGeneratorOrder;
    private final IdGenerator idGeneratorMaster;

    public CarOfficeRepositoryImpl () {
        this.orders = new Order[0];
        this.masters = new Master[0];
        this.garages = new Garage[0];
        this.idGeneratorGarage = new IdGenerator();
        this.idGeneratorOrder = new IdGenerator();
        this.idGeneratorMaster = new IdGenerator();
    }

    public IdGenerator getIdGeneratorGarage() {
        return idGeneratorGarage;
    }

    public IdGenerator getIdGeneratorOrder() {
        return idGeneratorOrder;
    }

    public IdGenerator getIdGeneratorMaster() {
        return idGeneratorMaster;
    }

    public String getName() {
        return name;
    }

    public Order[] getOrders() {
        return this.orders;
    }

    public Master[] getMasters() {
        return this.masters;
    }

    public Garage[] getGarages() {
        return this.garages;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrders(Order[] orders) {
        this.orders = orders;
    }

    public void setMasters(Master[] masters) {
        this.masters = masters;
    }

    public void setGarages(Garage[] garages) {
        this.garages = garages;
    }
}