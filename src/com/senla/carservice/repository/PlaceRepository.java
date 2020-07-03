package com.senla.carservice.repository;

import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;

import java.util.List;

public interface PlaceRepository {

    IdGenerator getIdGeneratorPlace();

    List<Place> getPlaces();

    List<Place> getFreePlaces(List<Order> orders);

    void addPlace(Place place);

    void updatePlace(Place place);

    void deletePlace(Place place);

    void updateListPlace(List<Place> places);

    void updateGenerator(IdGenerator idGenerator);
}
