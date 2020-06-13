package com.senla.carservice.service;

import com.senla.carservice.domain.Place;
import com.senla.carservice.domain.Garage;

public interface GarageService {
    Garage[] getGarages();

    void addGarage(String name);

    void deleteGarage(Garage garage);

    void addGaragePlace(Garage garage);

    int getNumberGaragePlaces(Garage garage);

    void deleteGaragePlace(Garage garage);

    Place[] getFreePlaceGarage(Garage garage);
}