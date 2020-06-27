package com.senla.carservice.repository;

import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class PlaceRepositoryImpl implements PlaceRepository {
    private static PlaceRepository instance;
    private List<Place> places;
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
    public void addPlace(Place place) {
        place.setId(this.idGeneratorPlace.getId());
        this.places.add(place);
    }

    @Override
    public void deletePlace(Place place) {
        this.places.remove(place);
    }

    @Override
    public List<Place> getFreePlaces() {
        List<Place> freePlaces = new ArrayList<>();
        this.places.forEach(place -> {
            if (!place.isBusyStatus()){
                freePlaces.add(place);
            }
        });
       return freePlaces;
    }
}