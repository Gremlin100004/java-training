package com.senla.carservice.repository;

import com.senla.carservice.domain.Place;

public class Garage  {
    private String name;
    private Place[] places;

    public Garage(String name) {
        this.name = name;
        this.places = new Place[0];
    }

    @Override
    public String toString() {
        return "Garage{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }


    public Place[] getPlaces() {
        return places;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPlaces(Place[] places) {
        this.places = places;
    }
}