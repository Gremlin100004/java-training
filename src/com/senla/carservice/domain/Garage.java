package com.senla.carservice.domain;

import com.senla.carservice.util.IdGenerator;

public class Garage {
    private Long id;
    private String name;
    private Place[] places;
    private IdGenerator idGeneratorPlace;

    public Garage(String name) {
        this.name = name;
        this.places = new Place[0];
        this.idGeneratorPlace = new IdGenerator();
    }

    public Long getId() {
        return id;
    }

    public IdGenerator getIdGeneratorPlace() {
        return idGeneratorPlace;
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

    public void setIdGeneratorPlace(IdGenerator idGeneratorPlace) {
        this.idGeneratorPlace = idGeneratorPlace;
    }

    @Override
    public String toString() {
        return "Garage{" +
                "name='" + name + '\'' +
                '}';
    }
}