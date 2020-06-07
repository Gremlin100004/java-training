package com.senla.carservice.domain;

public class Garage implements IGarage {
    private String name;
    private IPlace[] places;

    public Garage(String name, IPlace[] places) {
        this.name = name;
        this.places = places;
    }

    public Garage(String name) {
        this.name = name;
        this.places = new Place[0];
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IPlace[] getPlaces() {
        return places;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setPlaces(IPlace[] places) {
        this.places = places;
    }
}