package com.senla.carservice.service;

import com.senla.carservice.domain.Place;
import com.senla.carservice.domain.Garage;
import com.senla.carservice.repository.CarOfficeRepository;
import com.senla.carservice.repository.CarOfficeRepositoryImpl;
import com.senla.carservice.util.Deleter;

import java.util.Arrays;

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
    public Garage[] getGarages() {
        return Arrays.copyOf(this.carOfficeRepository.getGarages(), this.carOfficeRepository.getGarages().length);
    }

    @Override
    public void addGarage(String name) {
        int index = this.carOfficeRepository.getGarages().length;
        Garage garage = new Garage(name);
        garage.setId(this.carOfficeRepository.getIdGeneratorGarage().getId());
        this.carOfficeRepository.setGarages(Arrays.copyOf(this.carOfficeRepository.getGarages(), index + 1));
        this.carOfficeRepository.getGarages()[index] = garage;
    }

    @Override
    public void deleteGarage(Garage garage) {
        this.carOfficeRepository.setGarages(Deleter.deleteElementArray(this.carOfficeRepository.getGarages(), garage));
    }

    @Override
    public void addGaragePlace(Garage garage) {
        int length = garage.getPlaces().length;
        Place place = new Place();
        place.setId(garage.getIdGeneratorPlace().getId());
        Place[] places = Arrays.copyOf(garage.getPlaces(), length + 1);
        places[length] = place;
        garage.setPlaces(places);
    }

    @Override
    public int getNumberGaragePlaces(Garage garage) {
        return garage.getPlaces().length;
    }

    @Override
    public void deleteGaragePlace(Garage garage) {
        Place place = garage.getPlaces()[garage.getPlaces().length - 1];
        garage.setPlaces(Deleter.deleteElementArray(garage.getPlaces(), place));
    }

    @Override
    public Place[] getFreePlaceGarage(Garage garage) {
        Place[] freePlaces = new Place[0];
        int index;
        for (Place place : garage.getPlaces())
            if (!place.isBusyStatus()) {
                index = freePlaces.length;
                freePlaces = Arrays.copyOf(freePlaces, index + 1);
                freePlaces[index] = place;
            }
        return freePlaces;
    }
}