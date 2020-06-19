package com.senla.carservice.service;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Place;

import java.util.ArrayList;

public interface GarageService {
    ArrayList<Garage> getGarages();

    void addGarage(String name);

    void deleteGarage(Garage garage);

    void addGaragePlace(Garage garage);

    int getNumberPlaces();

    void deleteGaragePlace(Garage garage);

    ArrayList<Place> getFreePlaceGarage(Garage garage);
}