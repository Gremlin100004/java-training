package com.senla.carservice.service;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Place;
import com.senla.carservice.repository.CarOfficeRepository;
import com.senla.carservice.repository.CarOfficeRepositoryImpl;

import java.util.ArrayList;

public final class GarageServiceImpl implements GarageService {
    private static GarageServiceImpl instance;
    private final CarOfficeRepository carOfficeRepository;

    public GarageServiceImpl() {
        this.carOfficeRepository = CarOfficeRepositoryImpl.getInstance();
    }

    public static GarageServiceImpl getInstance() {
        if (instance == null) {
            instance = new GarageServiceImpl();
        }
        return instance;
    }

    @Override
    public ArrayList<Garage> getGarages() {
        return this.carOfficeRepository.getGarages();
    }

    @Override
    public void addGarage(String name) {
        Garage garage = new Garage(name);
        garage.setId(this.carOfficeRepository.getIdGeneratorGarage().getId());
        this.carOfficeRepository.getGarages().add(garage);
    }

    @Override
    public void deleteGarage(Garage garage) {
        this.carOfficeRepository.getGarages().remove(garage);
    }

    @Override
    public void addGaragePlace(Garage garage) {
        Place place = new Place();
        place.setId(garage.getIdGeneratorPlace().getId());
        garage.getPlaces().add(place);
    }

    @Override
    public int getNumberPlaces() {
        int numberPlaces = 0;
        for (Garage garage : this.carOfficeRepository.getGarages()) {
            numberPlaces += garage.getPlaces().size();
        }
        return numberPlaces;
    }

    @Override
    public void deleteGaragePlace(Garage garage) {
        garage.getPlaces().remove(garage.getPlaces().get(garage.getPlaces().size() - 1));
    }

    @Override
    public ArrayList<Place> getFreePlaceGarage(Garage garage) {
        ArrayList<Place> freePlaces = new ArrayList<>();
        for (Place place : garage.getPlaces())
            if (!place.isBusyStatus()) {
                freePlaces.add(place);
            }
        return freePlaces;
    }
}