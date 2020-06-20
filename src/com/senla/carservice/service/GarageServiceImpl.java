package com.senla.carservice.service;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Place;
import com.senla.carservice.repository.GarageRepository;
import com.senla.carservice.repository.GarageRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class GarageServiceImpl implements GarageService {
    private static GarageService instance;
    private final GarageRepository garageRepository;

    private GarageServiceImpl() {
        this.garageRepository = GarageRepositoryImpl.getInstance();
    }

    public static GarageService getInstance() {
        if (instance == null) {
            instance = new GarageServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Garage> getGarages() {
        return this.garageRepository.getGarages();
    }

    @Override
    public void addGarage(String name) {
        Garage garage = new Garage(name);
        garage.setId(this.garageRepository.getIdGeneratorGarage().getId());
        this.garageRepository.getGarages().add(garage);
    }

    @Override
    public void deleteGarage(Garage garage) {
        this.garageRepository.getGarages().remove(garage);
    }

    @Override
    public void addGaragePlace(Garage garage) {
        Place place = new Place();
        place.setId(this.garageRepository.getIdGeneratorPlace().getId());
        garage.getPlaces().add(place);
    }

    @Override
    public int getNumberPlaces() {
        int numberPlaces = 0;
        for (Garage garage : this.garageRepository.getGarages()) {
            numberPlaces += garage.getPlaces().size();
        }
        return numberPlaces;
    }

    @Override
    public void deleteGaragePlace(Garage garage) {
        garage.getPlaces().remove(garage.getPlaces().get(garage.getPlaces().size() - 1));
    }

    @Override
    public List<Place> getFreePlaceGarage(Garage garage) {
        List<Place> freePlaces = new ArrayList<>();
        for (Place place : garage.getPlaces())
            if (!place.isBusyStatus()) {
                freePlaces.add(place);
            }
        return freePlaces;
    }
}