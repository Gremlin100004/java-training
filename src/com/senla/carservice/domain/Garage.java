package com.senla.carservice.domain;

import com.senla.carservice.util.IdGenerator;

import java.util.ArrayList;

public class Garage {
    private Long id;
    private String name;
    private ArrayList<Place> places;
    private IdGenerator idGeneratorPlace;

    public Garage() {
    }

    public Garage(String name) {
        this.name = name;
        this.places = new ArrayList<>();
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

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlaces(ArrayList<Place> places) {
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