package com.senla.carservice.domain;

public class CarService implements ICarService {
    private String name;
    private IOrder[] orders;
    private IMaster[] masters;
    private IGarage[] garages;

    public CarService(String name) {
        this.name = name;
        this.orders = new Order[0];
        this.masters = new Master[0];
        this.garages = new Garage[0];
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IOrder[] getOrders() {
        return this.orders;
    }

    @Override
    public IMaster[] getMasters() {
        return this.masters;
    }

    @Override
    public IGarage[] getGarages() {
        return this.garages;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setOrders(IOrder[] orders) {
        this.orders = orders;
    }

    @Override
    public void setMasters(IMaster[] masters) {
        this.masters = masters;
    }

    @Override
    public void setGarages(IGarage[] garages) {
        this.garages = garages;
    }
}