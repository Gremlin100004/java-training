package com.senla.carservice.repository;

import com.senla.carservice.domain.Place;

import java.util.List;

public interface PlaceRepository {
    List<Place> getPlaces();

    void addPlace(Place place);

    void deletePlace(Place place);

    List<Place> getFreePlaces();
}
