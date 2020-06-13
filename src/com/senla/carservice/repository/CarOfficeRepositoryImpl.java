package com.senla.carservice.repository;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.util.IdGenerator;

public final class CarOfficeRepositoryImpl implements CarOfficeRepository {
    private static CarOfficeRepositoryImpl instance;
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

    public static CarOfficeRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new CarOfficeRepositoryImpl();
        }
        return instance;
    }

    @Override
    public IdGenerator getIdGeneratorGarage() {
        return idGeneratorGarage;
    }

    @Override
    public IdGenerator getIdGeneratorOrder() {
        return idGeneratorOrder;
    }

    @Override
    public IdGenerator getIdGeneratorMaster() {
        return idGeneratorMaster;
    }

    @Override
    public Order[] getOrders() {
        return this.orders;
    }

    public Master[] getMasters() {
        return this.masters;
    }

    @Override
    public Garage[] getGarages() {
        return this.garages;
    }

    @Override
    public void setOrders(Order[] orders) {
        this.orders = orders;
    }

    @Override
    public void setMasters(Master[] masters) {
        this.masters = masters;
    }

    @Override
    public void setGarages(Garage[] garages) {
        this.garages = garages;
    }
}