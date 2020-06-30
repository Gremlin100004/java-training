package com.senla.carservice.repository;

import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlaceRepositoryImpl implements PlaceRepository {
    private static PlaceRepository instance;
    private final List<Place> places;
    private final IdGenerator idGeneratorPlace;

    public PlaceRepositoryImpl() {
        this.places = new ArrayList<>();
        this.idGeneratorPlace = new IdGenerator();
    }

    public static PlaceRepository getInstance() {
        if (instance == null) {
            instance = new PlaceRepositoryImpl();
        }
        return instance;
    }

    @Override
    public List<Place> getPlaces() {
        return new ArrayList<>(this.places);
    }

    @Override
    public List<Place> getFreePlaces(List<Order> orders) {
        List<Place> freePlaces = new ArrayList<>(this.places);
        orders.forEach(order -> freePlaces.remove(order.getPlace()));
        return freePlaces;
    }

    @Override
    public void addPlace(Place place) {
        place.setId(this.idGeneratorPlace.getId());
        this.places.add(place);
    }

    @Override
    public void updatePlace(Place place) {
        if (this.places.isEmpty()){
            this.places.add(place);
        } else {
            this.places.set(this.places.indexOf(place), place);
        }
    }

    @Override
    public void deletePlace(Place place) {
        this.places.remove(place);
    }

    @Override
    public List<Place> getCurrentFreePlaces() {
        return this.places.stream().filter(place -> !place.isBusyStatus()).collect(Collectors.toList());
    }
}