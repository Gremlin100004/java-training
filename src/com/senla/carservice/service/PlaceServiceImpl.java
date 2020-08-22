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
    @SuppressWarnings("unchecked")
    public List<Place> getPlaces() {
        try {
            databaseConnection.disableAutoCommit();
            List<Place> places = placeDao.getAllRecords();
            if (places.isEmpty()) {
                throw new BusinessException("There are no places");
            }
            databaseConnection.commitTransaction();
            return places;
        } catch (BusinessException e) {
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction get places");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addPlace(Integer number) {
        try {
            databaseConnection.disableAutoCommit();
            if (isBlockAddPlace) {
                throw new BusinessException("Permission denied");
            }
            placeDao.createRecord(new Place(number));
            databaseConnection.commitTransaction();
        } catch (BusinessException e) {
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction add places");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void deletePlace(Place place) {
        try {
            databaseConnection.disableAutoCommit();
            if (place.getBusyStatus()) {
                throw new BusinessException("Place is busy");
            }
            if (isBlockDeletePlace) {
                throw new BusinessException("Permission denied");
            }
            placeDao.deleteRecord(place);
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
        try {
            databaseConnection.disableAutoCommit();
            int numberPlace = placeDao.getAllRecords().size();
            databaseConnection.commitTransaction();
            return numberPlace;
        } catch (BusinessException e) {
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction get number free places");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public List<Place> getFreePlaceByDate(Date executeDate) {
        try {
            databaseConnection.disableAutoCommit();
            List<Place> freePlace = placeDao.getFreePlaces(executeDate);
            if (freePlace.isEmpty()) {
                throw new BusinessException("There are no free places");
            }
            databaseConnection.commitTransaction();
            return freePlace;
        } catch (BusinessException e) {
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction get free places");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }
}