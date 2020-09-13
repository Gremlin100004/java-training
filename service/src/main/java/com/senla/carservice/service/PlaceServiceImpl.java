package com.senla.carservice.service;

import com.senla.carservice.domain.Place;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.dao.PlaceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

@Component
public class PlaceServiceImpl implements PlaceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlaceServiceImpl.class);
    @Autowired
    private PlaceDao placeDao;
    @Value("${com.senla.carservice.service.PlaceServiceImpl.isBlockAddPlace}")
    private Boolean isBlockAddPlace;
    @Value("${com.senla.carservice.service.PlaceServiceImpl.isBlockDeletePlace}")
    private Boolean isBlockDeletePlace;

    public PlaceServiceImpl() {
    }

    @Override
    public List<Place> getPlaces() {
        LOGGER.debug("Method getPlaces");
        try (Session session = placeDao.getSession()) {
            session.beginTransaction();
            List<Place> places = placeDao.getAllRecords(Place.class);
            if (places.isEmpty()) {
                throw new BusinessException("There are no places");
            }
            return places;
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException("Error transaction get places");
        }
    }

    @Override
    public void addPlace(Integer number) {
        LOGGER.debug("Method addPlace");
        LOGGER.debug("Parameter number: {}", number);
        if (isBlockAddPlace) {
            throw new BusinessException("Permission denied");
        }
        Session session = placeDao.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            placeDao.saveRecord(new Place(number));
            transaction.commit();
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException("Error transaction add places");
        }
    }

    @Override
    public void deletePlace(Long idPlace) {
        LOGGER.debug("Method deletePlace");
        LOGGER.debug("Parameter idPlace: {}", idPlace);
        if (isBlockDeletePlace) {
            throw new BusinessException("Permission denied");
        }
        Session session = placeDao.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Place place = placeDao.getRecordById(Place.class, idPlace);
            if (place.getBusy()) {
                throw new BusinessException("Place is busy");
            }
            placeDao.updateRecord(place);
            transaction.commit();
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException("Error transaction delete place");
        }
    }

    @Override
    public Long getNumberFreePlaceByDate(Date startDayDate) {
        LOGGER.debug("Method getNumberFreePlaceByDate");
        LOGGER.debug("Parameter startDayDate: {}", startDayDate);
        try (Session session = placeDao.getSession()) {
            session.beginTransaction();
            return placeDao.getNumberFreePlaces(startDayDate);
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException("Error transaction get number free places");
        }
    }

    @Override
    public List<Place> getFreePlaceByDate(Date executeDate) {
        LOGGER.debug("Method getFreePlaceByDate");
        LOGGER.debug("Parameter executeDate: {}", executeDate);
        try (Session session = placeDao.getSession()) {
            session.beginTransaction();
            return placeDao.getFreePlaces(executeDate);
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException("Error transaction free places");
        }
    }

    @Override
    public Long getNumberPlace() {
        LOGGER.debug("Method getNumberMasters");
        try (Session session = placeDao.getSession()) {
            session.beginTransaction();
            return placeDao.getNumberPlaces();
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException("Error transaction number places");
        }
    }
}