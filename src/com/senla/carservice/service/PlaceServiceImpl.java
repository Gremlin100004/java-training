package com.senla.carservice.service;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.container.objectadjuster.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.dao.PlaceDao;
import com.senla.carservice.dao.connection.DatabaseConnection;
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
    @Dependency
    private DatabaseConnection databaseConnection;

    public PlaceServiceImpl() {
    }

    @Override
    public List<Place> getPlaces() {
        List<Place> places = placeDao.getAllRecords(databaseConnection);
        if (places.isEmpty()) {
            throw new BusinessException("There are no places");
        }
        return places;
    }

    @Override
    public void addPlace(Integer number) {
        try {
            databaseConnection.disableAutoCommit();
            if (isBlockAddPlace) {
                throw new BusinessException("Permission denied");
            }
            placeDao.createRecord(new Place(number),databaseConnection);
            databaseConnection.commitTransaction();
        } catch (BusinessException e) {
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction add places");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public void deletePlace(Place place) {
        try {
            databaseConnection.disableAutoCommit();
            if (place.getBusyStatus()) {
                throw new BusinessException("Place is busy");
            }
            if (isBlockDeletePlace) {
                throw new BusinessException("Permission denied");
            }
            placeDao.deleteRecord(place, databaseConnection);
            databaseConnection.commitTransaction();
        } catch (BusinessException e) {
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction delete place");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public int getNumberFreePlaceByDate(Date startDayDate) {
        return placeDao.getAllRecords(databaseConnection).size();
    }

    @Override
    public List<Place> getFreePlaceByDate(Date executeDate) {
        List<Place> freePlace = placeDao.getFreePlaces(executeDate, databaseConnection);
        if (freePlace.isEmpty()) {
            throw new BusinessException("There are no free places");
        }
        return freePlace;
    }
}