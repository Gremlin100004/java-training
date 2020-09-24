package com.senla.carservice.service;

import com.senla.carservice.dao.PlaceDao;
import com.senla.carservice.domain.Place;
import com.senla.carservice.service.exception.BusinessException;
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
    @Transactional
    public List<Place> getPlaces() {
        LOGGER.debug("Method getPlaces");
        return placeDao.getAllRecords();
    }

    @Override
    @Transactional
    public void addPlace(Integer number) {
        LOGGER.debug("Method addPlace");
        LOGGER.trace("Parameter number: {}", number);
        if (isBlockAddPlace) {
            throw new BusinessException("Permission denied");
        }
        placeDao.saveRecord(new Place(number));
    }

    @Override
    @Transactional
    public void deletePlace(Long idPlace) {
        LOGGER.debug("Method deletePlace");
        LOGGER.trace("Parameter idPlace: {}", idPlace);
        if (isBlockDeletePlace) {
            throw new BusinessException("Permission denied");
        }
        Place place = placeDao.findById(idPlace);
        if (place.getBusy()) {
            throw new BusinessException("Place is busy");
        }
        if (place.getDeleteStatus()) {
            throw new BusinessException("error, place has already been deleted");
        }
        place.setDeleteStatus(true);
        placeDao.updateRecord(place);
    }

    @Override
    @Transactional
    public Long getNumberFreePlaceByDate(Date startDayDate) {
        LOGGER.debug("Method getNumberFreePlaceByDate");
        LOGGER.trace("Parameter startDayDate: {}", startDayDate);
        return placeDao.getNumberFreePlaces(startDayDate);
    }

    @Override
    @Transactional
    public List<Place> getFreePlaceByDate(Date executeDate) {
        LOGGER.debug("Method getFreePlaceByDate");
        LOGGER.trace("Parameter executeDate: {}", executeDate);
        return placeDao.getFreePlaces(executeDate);
    }

    @Override
    @Transactional
    public Long getNumberPlace() {
        LOGGER.debug("Method getNumberMasters");
        return placeDao.getNumberPlaces();
    }
}