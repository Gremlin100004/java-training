package com.senla.carservice.repository;

import com.senla.carservice.domain.Place;

public class Garage {
    private Long id;
    private String name;
    private Place[] places;
    private IGeneratorId generatorIdPlace;

    public Garage(String name) {
        this.name = name;
        this.places = new Place[0];
        this.generatorIdPlace = new GeneratorId();
    }

    public Long getId() {
        return id;
    }

    public IGeneratorId getGeneratorIdPlace() {
        return generatorIdPlace;
    }

    public String getName() {
        return name;
    }

    public Place[] getPlaces() {
        return places;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlaces(Place[] places) {
        this.places = places;
    }

    public void setGeneratorIdPlace(IGeneratorId generatorIdPlace) {
        this.generatorIdPlace = generatorIdPlace;
    }

    @Override
    public String toString() {
        return "Garage{" +
                "name='" + name + '\'' +
                '}';
    }
}