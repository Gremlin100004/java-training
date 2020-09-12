package com.senla.carservice.service;

import com.senla.carservice.domain.Place;

import java.util.Date;
import java.util.List;

public interface PlaceService {

    List<Place> getPlaces();

    void addPlace(Integer number);

    void deletePlace(Long idPlace);

    Long getNumberFreePlaceByDate(Date startDate);

    List<Place> getFreePlaceByDate(Date executeDate);

    Long getNumberPlace();
}