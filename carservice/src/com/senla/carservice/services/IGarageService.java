package com.senla.carservice.services;

import com.senla.carservice.domain.IGarage;
import com.senla.carservice.domain.IPlace;

public interface IGarageService {
    IGarage[] getGarage();

    void addGarage(String name);

    void deleteGarage(IGarage garage);

    void addGaragePlace(IGarage garage);

    void deleteGaragePlace(IGarage garage, IPlace place);

    IPlace[] getFreePlaceGarage(IGarage garage);
}