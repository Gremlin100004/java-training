package com.senla.carservice.repository;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Place;
import com.senla.carservice.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class GarageRepositoryImpl implements GarageRepository {
    private static GarageRepository instance;
    private List<Garage> garages;
    private final IdGenerator idGeneratorGarage;
    private final IdGenerator idGeneratorPlace;

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

    @Override
    public List<Garage> getGarages() {
        return garages;
    }

    @Override
    public IdGenerator getIdGeneratorGarage() {
        return idGeneratorGarage;
    }

    @Override
    public IdGenerator getIdGeneratorPlace() {
        return idGeneratorPlace;
    }

    @Override
    public void setGarages(List<Garage> garages) {
        this.garages = garages;
    }

    // этот метод должен быть в PlaceRepository
    @Override
    public List<Place> getPlaces() {
        List<Place> places = new ArrayList<>();
        this.garages.forEach(garage -> places.addAll(garage.getPlaces()));
        return places;
    }
}