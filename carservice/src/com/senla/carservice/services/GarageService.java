package com.senla.carservice.services;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.ICarService;
import com.senla.carservice.domain.IGarage;
import com.senla.carservice.domain.IPlace;
import com.senla.carservice.domain.Place;
import com.senla.carservice.util.Deleter;

import java.util.Arrays;

public class GarageService implements IGarageService {
    private ICarService carService;

    public GarageService(ICarService carService) {
        this.carService = carService;
    }

    @Override
    public IGarage[] getGarage() {
        return Arrays.copyOf(this.carService.getGarages(), this.carService.getGarages().length);
    }

    @Override
    public void addGarage(String name) {
        int index = this.carService.getGarages().length;
        this.carService.setGarages(Arrays.copyOf(this.carService.getGarages(), index + 1));
        this.carService.getGarages()[index] = new Garage(name);
    }

    @Override
    public void deleteGarage(IGarage garage) {
        Deleter deleter = new Deleter();
        this.carService.setGarages(deleter.deleteElementArray(this.carService.getGarages(), garage));
    }

    @Override
    public void addGaragePlace(IGarage garage) {
        int length = garage.getPlaces().length;
        for (int i = 0; i < this.carService.getGarages().length; i++) {
            if (this.carService.getGarages()[i].equals(garage)) {
                this.carService.getGarages()[i].setPlaces(Arrays.copyOf(garage.getPlaces(), length + 1));
                this.carService.getGarages()[i].getPlaces()[length] = new Place();
                break;
            }
        }
    }

    @Override
    public void deleteGaragePlace(IGarage garage, IPlace place) {
        Deleter deleter = new Deleter();
        garage.setPlaces(deleter.deleteElementArray(garage.getPlaces(), place));
    }

    @Override
    public IPlace[] getFreePlaceGarage(IGarage garage) {
        IPlace[] freePlaces = new Place[0];
        int index;
        for (IPlace place : garage.getPlaces())
            if (!place.isBusyStatus()) {
                index = freePlaces.length;
                freePlaces = Arrays.copyOf(freePlaces, index + 1);
                freePlaces[index] = place;
            }
        return freePlaces;
    }
}