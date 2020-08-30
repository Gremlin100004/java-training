package com.senla.carservice;

import com.senla.carservice.annotation.Singleton;
import com.senla.carservice.connection.DatabaseConnection;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.objectadjuster.propertyinjection.annotation.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaceServiceImpl.class);

    public PlaceServiceImpl() {
    }

    @Override
    public List<Place> getPlaces() {
        LOGGER.debug("Method getPlaces");
        List<Place> places = placeDao.getAllRecords(databaseConnection);
        if (places.isEmpty()) {
            throw new BusinessException("There are no places");
        }
        return places;
    }

    @Override
    public void addPlace(Integer number) {
        LOGGER.debug("Method addPlace");
        LOGGER.debug("Parameter number: {}", number);
        if (isBlockAddPlace) {
            throw new BusinessException("Permission denied");
        }
        try {
            databaseConnection.disableAutoCommit();
            placeDao.createRecord(new Place(number),databaseConnection);
            databaseConnection.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction add places");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public void deletePlace(Place place) {
        LOGGER.debug("Method deletePlace");
        LOGGER.debug("Parameter place: {}", place.toString());
        if (isBlockDeletePlace) {
            throw new BusinessException("Permission denied");
        }
        try {
            databaseConnection.disableAutoCommit();
            if (place.getBusyStatus()) {
                throw new BusinessException("Place is busy");
            }
            placeDao.deleteRecord(place, databaseConnection);
            databaseConnection.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction delete place");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public int getNumberFreePlaceByDate(Date startDayDate) {
        LOGGER.debug("Method getNumberFreePlaceByDate");
        LOGGER.debug("Parameter startDayDate: {}", startDayDate);
        return placeDao.getAllRecords(databaseConnection).size();
    }

    @Override
    public List<Place> getFreePlaceByDate(Date executeDate) {
        LOGGER.debug("Method getFreePlaceByDate");
        LOGGER.debug("Parameter executeDate: {}", executeDate);
        List<Place> freePlace = placeDao.getFreePlaces(executeDate, databaseConnection);
        if (freePlace.isEmpty()) {
            throw new BusinessException("There are no free places");
        }
        return freePlace;
    }

    @Override
    public int getNumberPlace() {
        LOGGER.debug("Method getNumberMasters");
        return placeDao.getNumberPlaces(databaseConnection);
    }

    @Override
    public Place getPlaceByIndex(Long index) {
        LOGGER.debug("Method getMasterByIndex");
        LOGGER.debug("Parameter index: {}", index);
        Place place = placeDao.getPlaceById(index, databaseConnection);
        if (place == null) {
            throw new BusinessException("There are no such place");
        }
        return place;
    }
}