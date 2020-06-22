package com.senla.carservice.service;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.repository.GarageRepository;
import com.senla.carservice.repository.GarageRepositoryImpl;
import com.senla.carservice.util.ExportUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class GarageServiceImpl implements GarageService {
    private static GarageService instance;
    private final GarageRepository garageRepository;

    private GarageServiceImpl() {
        this.garageRepository = GarageRepositoryImpl.getInstance();
    }

    public static GarageService getInstance() {
        if (instance == null) {
            instance = new GarageServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Garage> getGarages() {
        return this.garageRepository.getGarages();
    }

    @Override
    public void addGarage(String name) {
        Garage garage = new Garage(name);
        garage.setId(this.garageRepository.getIdGeneratorGarage().getId());
        this.garageRepository.getGarages().add(garage);
    }

    @Override
    public void deleteGarage(Garage garage) {
        this.garageRepository.getGarages().remove(garage);
    }

    @Override
    public void addGaragePlace(Garage garage) {
        Place place = new Place();
        place.setId(this.garageRepository.getIdGeneratorPlace().getId());
        garage.getPlaces().add(place);
    }

    @Override
    public int getNumberPlaces() {
        int numberPlaces = 0;
        for (Garage garage : this.garageRepository.getGarages()) {
            numberPlaces += garage.getPlaces().size();
        }
        return numberPlaces;
    }

    @Override
    public void deleteGaragePlace(Garage garage) {
        garage.getPlaces().remove(garage.getPlaces().get(garage.getPlaces().size() - 1));
    }

    @Override
    public List<Garage> getGaragesFreePlace(Date executeDate, Date leadDate, List<Order> orders){
        if (executeDate == null || leadDate == null){
            return new ArrayList<>();
        }
        List<Garage> freeGarages = new ArrayList<>(this.garageRepository.getGarages());
        for (Order order : orders) {
            for (Garage garage : freeGarages) {
                if (garage.equals(order.getGarage())) {
                    garage.getPlaces().remove(order.getPlace());
                    break;
                }
            }
        }
        return freeGarages;
    }

    @Override
    public List<Place> getFreePlaceGarage(Garage garage) {
        List<Place> freePlaces = new ArrayList<>();
        for (Place place : garage.getPlaces())
            if (!place.isBusyStatus()) {
                freePlaces.add(place);
            }
        return freePlaces;
    }

    @Override
    public String exportGarages(){
        GarageRepository garageRepository = GarageRepositoryImpl.getInstance();
        List<Garage> garages = garageRepository.getGarages();
        List<Place> places = garageRepository.getPlaces();
        StringBuilder valueGarageCsv = new StringBuilder();
        StringBuilder valuePlaceCsv = new StringBuilder();
        String message;
        for (int i = 0; i < garages.size(); i++){
            if (i == garages.size()-1){
                valueGarageCsv.append(convertGarageToCsv(garages.get(i), false));
            } else {
                valueGarageCsv.append(convertGarageToCsv(garages.get(i), true));
            }
        }
        for (int i = 0; i < places.size(); i++){
            if (i == places.size()-1){
                valuePlaceCsv.append(convertPlaceToCsv(places.get(i), false));
            } else {
                valuePlaceCsv.append(convertPlaceToCsv(places.get(i), true));
            }
        }
        message = ExportUtil.SaveCsv(valueGarageCsv, "csv//garage.csv");
        if (!message.equals("save successfully")){
            return message;
        }
        message = ExportUtil.SaveCsv(valuePlaceCsv, "csv//place.csv");
        if (!message.equals("save successfully")){
            return message;
        }
        return message;
    }

    @Override
    public void importGarage(){
        String path = "csv//garage.csv";
        List<String> csvLines = ExportUtil.GetCsv(path);
        csvLines.forEach(line-> Arrays.asList(line.split("\"")).forEach(line1->Arrays.asList(line1.split(",")).forEach(System.out::println)));
//        csvLines.forEach(line-> System.out.println(line.substring(line.indexOf("\"")).replaceAll("\"", "")));
    }

    private String convertGarageToCsv(Garage garage, boolean isLineFeed){
        StringBuilder stringValue = new StringBuilder();
        stringValue.append(String.format("%s,%s,\"",garage.getId(), garage.getName()));
        for (int i = 0; i < garage.getPlaces().size(); i++){
            if (i == garage.getPlaces().size()-1){
                stringValue.append(garage.getPlaces().get(i).getId());
            } else {
                stringValue.append(garage.getPlaces().get(i).getId()).append(",");
            }
        }
        stringValue.append("\"");
        if(isLineFeed){
            stringValue.append("\n");
        }
        return stringValue.toString();
    }

    private String convertPlaceToCsv(Place place, boolean isLineFeed){
        if(isLineFeed){
            return String.format("%s,%s\n", place.getId(), place.isBusyStatus());
        } else {
            return String.format("%s,%s", place.getId(), place.isBusyStatus());
        }
    }
}