package com.senla.carservice.service;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.container.objectadjuster.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.domain.Place;
import com.senla.carservice.hibernatedao.PlaceDao;
import com.senla.carservice.service.exception.BusinessException;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaceServiceImpl.class);

    public PlaceServiceImpl() {
    }

    @Override
    public List<Place> getPlaces() {
        LOGGER.debug("Method getPlaces");
        Session session = placeDao.getSessionFactory().openSession();
        List<Place> places = placeDao.getAllRecords(Place.class);
        if (places.isEmpty()) {
            session.close();
            throw new BusinessException("There are no places");
        }
        session.close();
        return places;
    }

    @Override
    public void addPlace(Integer number) {
        LOGGER.debug("Method addPlace");
        LOGGER.debug("Parameter number: {}", number);
        if (isBlockAddPlace) {
            throw new BusinessException("Permission denied");
        }
        Session session = placeDao.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            placeDao.saveRecord(new Place(number));
            transaction.commit();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            transaction.rollback();
            throw new BusinessException("Error transaction add places");
        }
    }

    @Override
    public void deletePlace(Place place) {
        LOGGER.debug("Method deletePlace");
        LOGGER.debug("Parameter place: {}", place);
        if (isBlockDeletePlace) {
            throw new BusinessException("Permission denied");
        }
        Session session = placeDao.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            if (place.getBusy()) {
                throw new BusinessException("Place is busy");
            }
            placeDao.updateRecord(place);
            transaction.commit();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            transaction.rollback();
            throw new BusinessException("Error transaction delete place");
        }
    }

    @Override
    public Long getNumberFreePlaceByDate(Date startDayDate) {
        LOGGER.debug("Method getNumberFreePlaceByDate");
        LOGGER.debug("Parameter startDayDate: {}", startDayDate);
        Session session = placeDao.getSessionFactory().openSession();
        Long numberGeneralPlaces = placeDao.getNumberPlaces();
        Long numberBusyPlaces = placeDao.getNumberBusyPlaces(startDayDate);
        session.close();
        return numberGeneralPlaces-numberBusyPlaces;
    }

    @Override
    public List<Place> getFreePlaceByDate(Date executeDate) {
        LOGGER.debug("Method getFreePlaceByDate");
        LOGGER.debug("Parameter executeDate: {}", executeDate);
        Session session = placeDao.getSessionFactory().openSession();
        List<Place> busyPlaces = placeDao.getBusyPlaces(executeDate);
        List<Place> freePlace = placeDao.getAllRecords(Place.class);
        freePlace.removeAll(busyPlaces);
        if (freePlace.isEmpty()) {
            session.close();
            throw new BusinessException("There are no free places");
        }
        session.close();
        return freePlace;
    }

    @Override
    public Long getNumberPlace() {
        LOGGER.debug("Method getNumberMasters");
        Session session = placeDao.getSessionFactory().openSession();
        Long numberPlaces = placeDao.getNumberPlaces();
        session.close();
        return numberPlaces;
    }
}