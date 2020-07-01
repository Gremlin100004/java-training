package com.senla.carservice.service;

import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;

import java.util.Date;
import java.util.List;

public interface PlaceService {
    List<Place> getPlaces();

    void addPlace(Integer number);

    void deletePlace(Place place);

    int getNumberFreePlaceByDate(Date executeDate, Date leadDate, List<Order> orders);

    List<Place> getFreePlaceByDate(Date executeDate, Date leadDate, List<Order> orders);

    void exportPlaces();

    void importPlaces();

    void serializePlace();

    void deserializePlace();
}