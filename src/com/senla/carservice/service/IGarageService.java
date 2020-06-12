package com.senla.carservice.service;

import com.senla.carservice.repository.Garage;
import com.senla.carservice.domain.Place;

public interface IGarageService {
    Garage[] getGarage();

    void addGarage(String name);

    void deleteGarage(Garage garage);

    void addGaragePlace(Garage garage);

    int getNumberGaragePlaces(Garage garage);

    void deleteGaragePlace(Garage garage);

    Place[] getFreePlaceGarage(Garage garage);
}