// сервис и контроллер - это разные слои, и они никак не могут быть в одном пакете
package com.senla.carservice.controller.service;

import com.senla.carservice.controller.data.Data;
import com.senla.carservice.domain.IGarage;
import com.senla.carservice.domain.IPlace;
import com.senla.carservice.services.IAdministrator;

public class GarageController {
    private IAdministrator carService;
    private Data data;

    public GarageController(IAdministrator carService, Data data) {
        this.carService = carService;
        this.data = data;
    }

    // нейминг
    public void AddGarage() {
        System.out.println("Add garage to service:");
        for (String garageName : this.data.getArrayGarageNames()) {
            this.carService.addGarage(garageName);
            System.out.println(String.format(" -garage \"%s\" has been added to service.", garageName));
        }
        System.out.println("******************************");
    }

    public void deleteGarage() {
        IGarage garage = this.carService.getGarage()[2];
        System.out.println(String.format("Delete garage in service with name \"%s\"", garage.getName()));
        this.carService.deleteGarage(garage);
        System.out.println("******************************");
    }

    public void addGaragePlace() {
        System.out.println("Add places in garages.");
        for (IGarage garage : this.carService.getGarage()) {
            for (int j = 0; j < 4; j++) {
                this.carService.addGaragePlace(garage);
            }
        }
        System.out.println("******************************");
    }

    public void deleteGaragePlace() {
        System.out.println("Delete place in Garage:");
        IGarage garage = this.carService.getGarage()[1];
        IPlace place = garage.getPlaces()[2];
        System.out.println(String.format(" -the place in garage with name \"%s\" has been deleted successfully.",
                garage.getName()));
        this.carService.deleteGaragePlace(garage, place);
        System.out.println("******************************");
    }

    public void getFreePlaceGarage() {
        int numberFreePlaces;
        System.out.println("Get free place in garage");
        for (IGarage garage : this.carService.getGarage()) {
            numberFreePlaces = this.carService.getFreePlaceGarage(garage).length;
            System.out.println(String.format(" -garage with name \"%s\" %s available places", garage.getName(),
                    numberFreePlaces));
        }
        System.out.println("******************************");
    }
}