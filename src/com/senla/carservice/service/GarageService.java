package com.senla.carservice.service;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;

import java.util.Date;
import java.util.List;

public interface GarageService {
    List<Garage> getGarages();

    void addGarage(String name);

    void deleteGarage(Garage garage);

    void addGaragePlace(Garage garage);

    int getNumberPlaces();

    void deleteGaragePlace(Garage garage);

    List<Garage> getGaragesFreePlace(Date executeDate, Date leadDate, List<Order> orders);

    List<Place> getFreePlaceGarage(Garage garage);

    String exportGarages();
}