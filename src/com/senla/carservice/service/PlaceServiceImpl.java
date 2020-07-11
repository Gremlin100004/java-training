package com.senla.carservice.service;

import com.senla.carservice.domain.Place;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.factory.annotation.Dependency;
import com.senla.carservice.repository.PlaceRepository;

import java.util.Date;
import java.util.List;

public class PlaceServiceImpl implements PlaceService {
    @Dependency
    private PlaceRepository placeRepository;

    public PlaceServiceImpl() {
    }

    @Override
    public List<Place> getPlaces() {
        checkPlaces();
        return placeRepository.getPlaces();
    }

    @Override
    public void addPlace(Integer number) {
        placeRepository.addPlace(new Place(number));
    }

    @Override
    public void deletePlace(Place place) {
        placeRepository.deletePlace(place);
    }

    @Override
    public int getNumberFreePlaceByDate(Date startDayDate) {
        checkPlaces();
        return placeRepository.getFreePlaces(startDayDate).size();
    }

    @Override
    public List<Place> getFreePlaceByDate(Date executeDate) {
        checkPlaces();
        List<Place> freePlace = placeRepository.getFreePlaces(executeDate);
        if (freePlace.isEmpty()) {
            throw new BusinessException("There are no free places");
        }
        return freePlace;
    }

    private void checkPlaces() {
        if (placeRepository.getPlaces().isEmpty()) {
            throw new BusinessException("There are no places");
        }
    }
}