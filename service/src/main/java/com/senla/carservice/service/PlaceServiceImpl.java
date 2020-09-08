package com.senla.carservice.service;

import com.senla.carservice.domain.Place;
import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.container.objectadjuster.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.hibernatedao.PlaceDao;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

    private static final Logger LOGGER = LogManager.getLogger(PlaceServiceImpl.class);

    public PlaceServiceImpl() {
    }

    @Override
    public List<Place> getPlaces() {
        LOGGER.debug("Method getPlaces");
        Session session = placeDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<Place> places = placeDao.getAllRecords(Place.class);
            if (places.isEmpty()) {
                throw new BusinessException("There are no places");
            }
            transaction.commit();
            return places;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction get places");
        }
    }

    @Override
    public void addPlace(Integer number) {
        LOGGER.debug("Method addPlace");
        LOGGER.debug("Parameter number: " + number);
        if (isBlockAddPlace) {
            throw new BusinessException("Permission denied");
        }
        Session session = placeDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            placeDao.saveRecord(new Place(number));
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();    
            }
            throw new BusinessException("Error transaction add places");
        }
    }

    @Override
    public void deletePlace(Place place) {
        LOGGER.debug("Method deletePlace");
        LOGGER.debug("Parameter place: " + place);
        if (isBlockDeletePlace) {
            throw new BusinessException("Permission denied");
        }
        Session session = placeDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (place.getBusy()) {
                throw new BusinessException("Place is busy");
            }
            placeDao.updateRecord(place);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();    
            }
            throw new BusinessException("Error transaction delete place");
        }
    }

    @Override
    public Long getNumberFreePlaceByDate(Date startDayDate) {
        LOGGER.debug("Method getNumberFreePlaceByDate");
        LOGGER.debug("Parameter startDayDate: " + startDayDate);
        Session session = placeDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Long numberGeneralPlaces = placeDao.getNumberPlaces();
            Long numberBusyPlaces = placeDao.getNumberBusyPlaces(startDayDate);
            transaction.commit();
            return numberGeneralPlaces-numberBusyPlaces;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction get number free places");
        }
    }

    @Override
    public List<Place> getFreePlaceByDate(Date executeDate) {
        LOGGER.debug("Method getFreePlaceByDate");
        LOGGER.debug("Parameter executeDate: " + executeDate);
        Session session = placeDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<Place> busyPlaces = placeDao.getBusyPlaces(executeDate);
            List<Place> freePlace = placeDao.getAllRecords(Place.class);
            freePlace.removeAll(busyPlaces);
            if (freePlace.isEmpty()) {
                throw new BusinessException("There are no free places");
            }
            transaction.commit();
            return freePlace;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction free places");
        }
    }

    @Override
    public Long getNumberPlace() {
        LOGGER.debug("Method getNumberMasters");
        Session session = placeDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Long numberPlace = placeDao.getNumberPlaces();
            transaction.commit();
            return numberPlace;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction number places");
        }
    }
}