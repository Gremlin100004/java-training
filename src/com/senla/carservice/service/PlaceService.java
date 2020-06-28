package com.senla.carservice.service;

import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.exception.DateException;
import com.senla.carservice.exception.NullDateException;
import com.senla.carservice.exception.NumberObjectZeroException;

import java.util.Date;
import java.util.List;

public interface PlaceService {
    List<Place> getPlaces() throws NumberObjectZeroException;

    void addPlace(Integer number);

    void deletePlace(Place place);

    List<Place> getFreePlaces() throws NumberObjectZeroException;

    int getNumberFreePlaceByDate(Date executeDate, Date leadDate, List<Order> orders) throws DateException, NullDateException, NumberObjectZeroException;

    List<Place> getFreePlaceByDate(Date executeDate, Date leadDate, List<Order> orders) throws DateException, NullDateException, NumberObjectZeroException;

//    String exportPlaces();
//
//    String importPlaces();
}