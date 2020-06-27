package com.senla.carservice.controller;

import com.senla.carservice.domain.Place;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.service.PlaceServiceImpl;

import java.util.List;

public class PlaceController {
    private static PlaceController instance;
    private final PlaceService placeService;

    private PlaceController() {
        placeService = PlaceServiceImpl.getInstance();
    }

    public static PlaceController getInstance() {
        if (instance == null) {
            instance = new PlaceController();
        }
        return instance;
    }

    public String addPlace(int number) {
        placeService.addPlace(number);
        return String.format("-place \"%s\" has been added to service", number);
    }

    public List<Place> getArrayPlace() {
        return placeService.getPlaces();
    }

    public String deletePlace(Place place) {
        placeService.deletePlace(place);
        return String.format(" -delete place in service number \"%s\"", place.getNumber());
    }

    public List<Place> getNumberFreePlaces() {
        return placeService.getFreePlaces();
    }

//    public String exportGarages() {
//        // использовать исключения
//        if (this.placeService.exportPlaces().equals("save successfully")) {
//            return "Garages have been export successfully!";
//        } else {
//            return "export problem.";
//        }
//    }
//
//    public String importGarages() {
//        if (this.placeService.importPlaces().equals("import successfully")) {
//            return "Garages have been import successfully!";
//        } else {
//            return "import problem.";
//        }
//    }
}