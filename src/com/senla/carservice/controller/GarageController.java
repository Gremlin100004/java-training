package com.senla.carservice.controller;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Place;
import com.senla.carservice.service.GarageService;
import com.senla.carservice.service.GarageServiceImpl;

import java.util.ArrayList;

public class GarageController {
    private final GarageService garageService;

    public GarageController() {
        this.garageService = new GarageServiceImpl();
    }

    public String addGarage(String name) {
        this.garageService.addGarage(name);
        return String.format("-garage \"%s\" has been added to service", name);
    }

    public ArrayList<Garage> getArrayGarages() {
        return this.garageService.getGarages();
    }

    public String deleteGarage(Garage garage) {
        this.garageService.deleteGarage(garage);
        return String.format(" -delete garage in service with name \"%s\"", garage.getName());
    }

    public String addGaragePlace(Garage garage) {
        this.garageService.addGaragePlace(garage);
        return String.format("Add place in garage \"%s\"", garage.getName());
    }

    public int getNumberGaragePlaces(Garage garage) {
        return this.garageService.getNumberGaragePlaces(garage);
    }

    public String deleteGaragePlace(Garage garage) {
        this.garageService.deleteGaragePlace(garage);
        return String.format(" -the place in garage with name \"%s\" has been deleted successfully.", garage.getName());
    }

    public int getNumberFreePlaceGarage(Garage garage) {
        return this.garageService.getFreePlaceGarage(garage).size();
    }

    public ArrayList<Place> getFreePlaceGarage(Garage garage) {
        return this.garageService.getFreePlaceGarage(garage);
    }
}