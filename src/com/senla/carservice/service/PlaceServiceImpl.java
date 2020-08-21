package com.senla.carservice.service;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.container.objectadjuster.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.dao.PlaceDao;
import com.senla.carservice.domain.Place;
import com.senla.carservice.exception.BusinessException;

import java.util.Date;
import java.util.List;

@Singleton
public class PlaceServiceImpl implements PlaceService {
    @Dependency
    private PlaceDao placeDao;
    @ConfigProperty
    private Boolean isBlockAddPlace;
    @ConfigProperty
    private Boolean isBlockDeletePlace;

    public PlaceServiceImpl() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Place> getPlaces() {
        List<Place> places = placeDao.getAllRecords();
        if (places.isEmpty()) {
            throw new BusinessException("There are no places");
        }
        return places;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addPlace(Integer number) {
        if (isBlockAddPlace) {
            throw new BusinessException("Permission denied");
        }
        placeDao.createRecord(new Place(number));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void deletePlace(Place place) {
        if (place.getBusyStatus()) {
            throw new BusinessException("Place is busy");
        }
        if (isBlockDeletePlace) {
            throw new BusinessException("Permission denied");
        }
        placeDao.deleteRecord(place);
    }

    @Override
    public int getNumberFreePlaceByDate(Date startDayDate) {
        return placeDao.getAllRecords().size();
    }

    @Override
    public List<Place> getFreePlaceByDate(Date executeDate) {
        List<Place> freePlace = placeDao.getFreePlaces(executeDate);
        if (freePlace.isEmpty()) {
            throw new BusinessException("There are no free places");
        }
        return freePlace;
    }
}