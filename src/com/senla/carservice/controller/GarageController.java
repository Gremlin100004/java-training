package com.senla.carservice.controller;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Place;
import com.senla.carservice.service.GarageService;
import com.senla.carservice.service.GarageServiceImpl;

import java.util.List;

public class GarageController {
    private static GarageController instance;
    private final GarageService garageService;

    private GarageController() {
        this.garageService = GarageServiceImpl.getInstance();
    }

    public static GarageController getInstance() {
        if (instance == null) {
            instance = new GarageController();
        }
        return instance;
    }

    public String addGarage(String name) {
        this.garageService.addGarage(name);
        return String.format("-garage \"%s\" has been added to service", name);
    }

    public List<Garage> getArrayGarages() {
        return this.garageService.getGarages();
    }

    public String deleteGarage(Garage garage) {
        this.garageService.deleteGarage(garage);
        return String.format(" -delete garage in service with name \"%s\"", garage.getName());
    }

    // из юай не может придти гараж, может придти его айди
    public String addGaragePlace(Garage garage) {
        this.garageService.addGaragePlace(garage);
        return String.format("Add place in garage \"%s\"", garage.getName());
    }

    public int getNumberFreePlaces() {
        return this.garageService.getNumberPlaces();
    }

    public String deleteGaragePlace(Garage garage) {
        this.garageService.deleteGaragePlace(garage);
        return String.format(" -the place in garage with name \"%s\" has been deleted successfully.", garage.getName());
    }

    public int getNumberFreePlaceGarage(Garage garage) {
        return this.garageService.getFreePlaceGarage(garage).size();
    }

    public List<Place> getFreePlaceGarage(Garage garage) {
        return this.garageService.getFreePlaceGarage(garage);
    }

    public String exportGarages() {
        // использовать исключения
        if (this.garageService.exportGarages().equals("save successfully")) {
            return "Garages have been export successfully!";
        } else {
            return "export problem.";
        }
    }

    public String importGarages() {
        if (this.garageService.importGarage().equals("import successfully")) {
            return "Garages have been import successfully!";
        } else {
            return "import problem.";
        }
    }
}