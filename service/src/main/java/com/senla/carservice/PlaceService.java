package com.senla.carservice;

import java.util.Date;
import java.util.List;

public interface PlaceService {
    List<Place> getPlaces();

    void addPlace(Integer number);

    void deletePlace(Place place);

    int getNumberFreePlaceByDate(Date startDate);

    List<Place> getFreePlaceByDate(Date executeDate);

    int getNumberPlace();

    Place getPlaceByIndex(Long index);
}