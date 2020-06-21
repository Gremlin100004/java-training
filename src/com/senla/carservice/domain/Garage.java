package com.senla.carservice.domain;

import com.senla.carservice.util.IdGenerator;

import java.util.ArrayList;
import java.util.Objects;

public class Garage extends AEntity {
    private String name;
    private ArrayList<Place> places;

    public Garage() {
    }

    public Garage(String name) {
        this.name = name;
        this.places = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return "Garage{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Garage garage = (Garage) o;
        return super.getId().equals(garage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId());
    }
}