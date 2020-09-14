package com.senla.carservice.service;

import com.senla.carservice.dao.PlaceDao;
import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.domain.Place;
import com.senla.carservice.service.exception.BusinessException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlaceServiceImpl.class);
    @Autowired
    private PlaceDao placeDao;
    @Value("${com.senla.carservice.service.PlaceServiceImpl.isBlockAddPlace:false}")
    private Boolean isBlockAddPlace;
    @Value("${com.senla.carservice.service.PlaceServiceImpl.isBlockDeletePlace:false}")
    private Boolean isBlockDeletePlace;

    public PlaceServiceImpl() {
    }

    @Override
    @Transactional(readOnly = true)
    public List<Place> getPlaces() {
        LOGGER.debug("Method getPlaces");
        List<Place> places = placeDao.getAllRecords(Place.class);
        if (places.isEmpty()) {
            throw new BusinessException("There are no places");
        }
        return places;
    }

    @Override
    @Transactional
    public void addPlace(Integer number) {
        LOGGER.debug("Method addPlace");
        LOGGER.debug("Parameter number: {}", number);
        if (isBlockAddPlace) {
            throw new BusinessException("Permission denied");
        }
        placeDao.saveRecord(new Place(number));
    }

    @Override
    @Transactional
    public void deletePlace(Long idPlace) {
        LOGGER.debug("Method deletePlace");
        LOGGER.debug("Parameter idPlace: {}", idPlace);
        if (isBlockDeletePlace) {
            throw new BusinessException("Permission denied");
        }
        Place place = placeDao.findById(Place.class, idPlace);
        if (place.getBusy()) {
            throw new BusinessException("Place is busy");
        }
        placeDao.updateRecord(place);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getNumberFreePlaceByDate(Date startDayDate) {
        LOGGER.debug("Method getNumberFreePlaceByDate");
        LOGGER.debug("Parameter startDayDate: {}", startDayDate);
        return placeDao.getNumberFreePlaces(startDayDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Place> getFreePlaceByDate(Date executeDate) {
        LOGGER.debug("Method getFreePlaceByDate");
        LOGGER.debug("Parameter executeDate: {}", executeDate);
        return placeDao.getFreePlaces(executeDate);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getNumberPlace() {
        LOGGER.debug("Method getNumberMasters");
        return placeDao.getNumberPlaces();
    }
}