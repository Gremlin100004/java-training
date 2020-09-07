package com.senla.carservice.service;

import com.senla.carservice.domain.Place;
import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.container.objectadjuster.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.hibernatedao.PlaceDao;
import com.senla.carservice.hibernatedao.session.HibernateSessionFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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
    private static final Logger LOGGER = LogManager.getLogger(PlaceServiceImpl.class);

    public PlaceServiceImpl() {
    }

    @Override
    public List<Place> getPlaces() {
        LOGGER.debug("Method getPlaces");
        List<Place> places = placeDao.getAllRecords(hibernateSessionFactory.getSession(), Place.class);
        if (places.isEmpty()) {
            hibernateSessionFactory.closeSession();
            throw new BusinessException("There are no places");
        }
        hibernateSessionFactory.closeSession();
        return places;
    }

    @Override
    public void addPlace(Integer number) {
        LOGGER.debug("Method addPlace");
        LOGGER.debug("Parameter number: " + number);
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
        LOGGER.debug("Parameter place: " + place);
        if (isBlockDeletePlace) {
            throw new BusinessException("Permission denied");
        }
        try {
            hibernateSessionFactory.openTransaction();
            if (place.getBusy()) {
                throw new BusinessException("Place is busy");
            }
            placeDao.updateRecord(place, hibernateSessionFactory.getSession());
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
    public Long getNumberFreePlaceByDate(Date startDayDate) {
        LOGGER.debug("Method getNumberFreePlaceByDate");
        LOGGER.debug("Parameter startDayDate: " + startDayDate);
        Long numberGeneralPlaces = placeDao.getNumberPlaces(hibernateSessionFactory.getSession());
        Long numberBusyPlaces = placeDao.getNumberBusyPlaces(startDayDate, hibernateSessionFactory.getSession());
        hibernateSessionFactory.closeSession();
        return numberGeneralPlaces-numberBusyPlaces;
    }

    @Override
    public List<Place> getFreePlaceByDate(Date executeDate) {
        LOGGER.debug("Method getFreePlaceByDate");
        LOGGER.debug("Parameter executeDate: " + executeDate);
        List<Place> busyPlaces = placeDao.getBusyPlaces(executeDate, hibernateSessionFactory.getSession());
        List<Place> freePlace = placeDao.getAllRecords(hibernateSessionFactory.getSession(), Place.class);
        freePlace.removeAll(busyPlaces);
        if (freePlace.isEmpty()) {
            hibernateSessionFactory.closeSession();
            throw new BusinessException("There are no free places");
        }
        hibernateSessionFactory.closeSession();
        return freePlace;
    }

    @Override
    public Long getNumberPlace() {
        LOGGER.debug("Method getNumberMasters");
        Long numberPlaces = placeDao.getNumberPlaces(hibernateSessionFactory.getSession());
        hibernateSessionFactory.closeSession();
        return numberPlaces;
    }
}