package com.senla.carservice.service;

import com.senla.carservice.repository.Garage;
import com.senla.carservice.repository.CarService;
import com.senla.carservice.domain.Place;
import com.senla.carservice.util.Deleter;

import java.util.Arrays;

public class GarageService implements IGarageService {
    private CarService carService;

    public GarageService(CarService carService) {
        this.carService = carService;
    }

    @Override
    public Garage[] getGarage() {
        return Arrays.copyOf(this.carService.getGarages(), this.carService.getGarages().length);
    }

    @Override
    public void addGarage(String name) {
        int index = this.carService.getGarages().length;
        this.carService.setGarages(Arrays.copyOf(this.carService.getGarages(), index + 1));
        this.carService.getGarages()[index] = new Garage(name);
    }

    @Override
    public void deleteGarage(Garage garage) {
        this.carService.setGarages(Deleter.deleteElementArray(this.carService.getGarages(), garage));
    }

    @Override
    public void addGaragePlace(Garage garage) {
        int length = garage.getPlaces().length;
        Place[] places = Arrays.copyOf(garage.getPlaces(), length + 1);
        places[length] = new Place();
        garage.setPlaces(places);
    }

    @Override
    public int getNumberGaragePlaces(Garage garage){
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