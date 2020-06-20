package com.senla.carservice.repository;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class GarageRepositoryImpl implements GarageRepository {
    private static GarageRepository instance;
    private List<Garage> garages;
    private final IdGenerator idGeneratorGarage;
    private IdGenerator idGeneratorPlace;

    private GarageRepositoryImpl() {
        this.garages = new ArrayList<>();
        this.idGeneratorGarage = new IdGenerator();
        this.idGeneratorPlace = new IdGenerator();
    }

    public static GarageRepository getInstance() {
        if (instance == null) {
            instance = new GarageRepositoryImpl();
        }
        return instance;
    }

    public List<Garage> getGarages() {
        return garages;
    }

    public IdGenerator getIdGeneratorGarage() {
        return idGeneratorGarage;
    }

    public IdGenerator getIdGeneratorPlace() {
        return idGeneratorPlace;
    }

    public void setGarages(List<Garage> garages) {
        this.garages = garages;
    }
}