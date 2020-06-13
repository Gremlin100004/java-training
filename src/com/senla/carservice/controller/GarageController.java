package com.senla.carservice.controller;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.service.GarageService;
import com.senla.carservice.service.GarageServiceImpl;

public class GarageController {
    private final GarageService garageService;

    public GarageController() {
        this.garageService = new GarageServiceImpl();
    }

    public String addGarage(String name) {
        this.garageService.addGarage(name);
        return name;
    }

    public Garage[] getArrayGarages() {
        return this.garageService.getGarages();
    }

    public String deleteGarage(Garage garage) {
        this.garageService.deleteGarage(garage);
        return garage.getName();
    }

    public String addGaragePlace(Garage garage) {
        this.garageService.addGaragePlace(garage);
        return garage.getName();
    }

    public int getNumberGaragePlaces(Garage garage) {
        return this.garageService.getNumberGaragePlaces(garage);
    }

    public String deleteGaragePlace(Garage garage) {
        this.garageService.deleteGaragePlace(garage);
        return garage.getName();
    }

    public int getNumberFreePlaceGarage(Garage garage) {
        return this.garageService.getFreePlaceGarage(garage).length;
    }
}