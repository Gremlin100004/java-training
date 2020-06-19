package com.senla.carservice.repository;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.util.IdGenerator;

import java.util.ArrayList;

// для каждой сущности свой репозиторий
public final class CarOfficeRepositoryImpl implements CarOfficeRepository {
    private static CarOfficeRepositoryImpl instance;
    private ArrayList<Order> orders;
    private ArrayList<Master> masters;
    private ArrayList<Garage> garages;
    private final IdGenerator idGeneratorGarage;
    private final IdGenerator idGeneratorOrder;
    private final IdGenerator idGeneratorMaster;

    public CarOfficeRepositoryImpl() {
        this.orders = new ArrayList<>();
        this.masters = new ArrayList<>();
        this.garages = new ArrayList<>();
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
    public ArrayList<Order> getOrders() {
        return this.orders;
    }

    public ArrayList<Master> getMasters() {
        return this.masters;
    }

    @Override
    public ArrayList<Garage> getGarages() {
        return this.garages;
    }

    @Override
    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    @Override
    public void setMasters(ArrayList<Master> masters) {
        this.masters = masters;
    }

    @Override
    public void setGarages(ArrayList<Garage> garages) {
        this.garages = garages;
    }
}