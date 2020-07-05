package com.senla.carservice.service;

import com.senla.carservice.annotation.InjectObject;
import com.senla.carservice.domain.Place;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.repository.PlaceRepository;
import com.senla.carservice.repository.PlaceRepositoryImpl;
import com.senla.carservice.util.Serializer;

import java.util.Date;
import java.util.List;

public class PlaceServiceImpl implements PlaceService {
    @InjectObject
    private static PlaceService instance;
    private final PlaceRepository placeRepository;

    private PlaceServiceImpl() {
        placeRepository = PlaceRepositoryImpl.getInstance();
    }

    public static PlaceService getInstance() {
        if (instance == null) {
            instance = new PlaceServiceImpl();
        }
        return instance;
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

    @Override
    public void serializePlace() {
        Serializer.serializePlace(PlaceRepositoryImpl.getInstance());
    }

    @Override
    public void deserializePlace() {
        PlaceRepository placeRepositoryRestore = Serializer.deserializePlace();
        placeRepository.updateListPlace(placeRepositoryRestore.getPlaces());
        placeRepository.updateGenerator(placeRepositoryRestore.getIdGeneratorPlace());
    }

    private void checkPlaces() {
        if (placeRepository.getPlaces().isEmpty()) {
            throw new BusinessException("There are no places");
        }
    }
}