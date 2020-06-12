package com.senla.carservice.controller;

import com.senla.carservice.repository.Garage;
import com.senla.carservice.service.IAdministrator;

public class GarageController {
    private IAdministrator carService;

    public GarageController(IAdministrator carService) {
        this.carService = carService;
    }

    public String addGarage(String name) {
        this.carService.addGarage(name);
        return name;
    }

    public Garage[] getArrayGarages(){
        return this.carService.getGarage();
    }

    public String deleteGarage(Garage garage) {
        this.carService.deleteGarage(garage);
        return garage.getName();
    }

    public String addGaragePlace(Garage garage) {
        this.carService.addGaragePlace(garage);
        return garage.getName();
    }

    public int getNumberGaragePlaces(Garage garage){
        return this.carService.getNumberGaragePlaces(garage);
    }

    public String deleteGaragePlace(Garage garage) {
        this.carService.deleteGaragePlace(garage);
        return garage.getName();
    }

    public int getNumberFreePlaceGarage(Garage garage) {
        int numberFreePlaces;
        numberFreePlaces = this.carService.getFreePlaceGarage(garage).length;
        return numberFreePlaces;
    }
}