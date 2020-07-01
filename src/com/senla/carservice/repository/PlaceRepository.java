package com.senla.carservice.repository;

import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;

import java.util.List;

public interface PlaceRepository {
    List<Place> getPlaces();

    List<Place> getFreePlaces(List<Order> orders);

    List<Place> getCurrentFreePlaces();

    void addPlace(Place place);

    void updatePlace(Place place);

    void deletePlace(Place place);
}
