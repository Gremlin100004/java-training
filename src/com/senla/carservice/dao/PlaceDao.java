package com.senla.carservice.dao;

import com.senla.carservice.domain.Place;
import com.senla.carservice.repository.IdGenerator;

import java.util.Date;
import java.util.List;

public interface PlaceDao {

    List<Place> getPlaces();

    List<Place> getFreePlaces(Date startDayDate);

    void addPlace(Place place);

    void updatePlace(Place place);

    void deletePlace(Place place);

    void updateListPlace(List<Place> places);
}
