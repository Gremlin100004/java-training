package com.senla.carservice.service;

import com.senla.carservice.Place;
import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.container.objectadjuster.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.service.exception.BusinessException;
import hibernatedao.PlaceDao;
import hibernatedao.session.HibernateSessionFactory;
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
    private HibernateSessionFactory hibernateSessionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaceServiceImpl.class);

    public PlaceServiceImpl() {
    }

    @Override
    public List<Place> getPlaces() {
        LOGGER.debug("Method getPlaces");
        List<Place> places = placeDao.getAllRecords(hibernateSessionFactory.getSession());
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
            hibernateSessionFactory.openTransaction();
            placeDao.saveRecord(new Place(number), hibernateSessionFactory.getSession());
            hibernateSessionFactory.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            hibernateSessionFactory.rollBackTransaction();
            throw new BusinessException("Error transaction add places");
        } finally {
            hibernateSessionFactory.closeSession();
        }
    }

    @Override
    public void deletePlace(Place place) {
        LOGGER.debug("Method deletePlace");
        LOGGER.debug("Parameter place: {}", place);
        if (isBlockDeletePlace) {
            throw new BusinessException("Permission denied");
        }
        try {
            hibernateSessionFactory.openTransaction();
            if (place.getIsBusy()) {
                throw new BusinessException("Place is busy");
            }
            placeDao.deleteRecord(place, hibernateSessionFactory.getSession());
            hibernateSessionFactory.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            hibernateSessionFactory.rollBackTransaction();
            throw new BusinessException("Error transaction delete place");
        } finally {
            hibernateSessionFactory.closeSession();
        }
    }

    @Override
    public int getNumberFreePlaceByDate(Date startDayDate) {
        LOGGER.debug("Method getNumberFreePlaceByDate");
        LOGGER.debug("Parameter startDayDate: {}", startDayDate);
        return placeDao.getAllRecords(hibernateSessionFactory.getSession()).size();
    }

    @Override
    public List<Place> getFreePlaceByDate(Date executeDate) {
        LOGGER.debug("Method getFreePlaceByDate");
        LOGGER.debug("Parameter executeDate: {}", executeDate);
        List<Place> freePlace = placeDao.getFreePlaces(executeDate, hibernateSessionFactory.getSession());
        if (freePlace.isEmpty()) {
            throw new BusinessException("There are no free places");
        }
        return freePlace;
    }

    @Override
    public int getNumberPlace() {
        LOGGER.debug("Method getNumberMasters");
        return placeDao.getNumberPlaces(hibernateSessionFactory.getSession());
    }
}