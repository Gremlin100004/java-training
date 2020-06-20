package com.senla.carservice.repository;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.util.IdGenerator;

import java.util.List;

public interface GarageRepository {
    List<Garage> getGarages();

    IdGenerator getIdGeneratorGarage();

    IdGenerator getIdGeneratorPlace();

    void setGarages(List<Garage> garages);
}