package com.senla.carservice.controller;

import com.senla.carservice.controller.util.StringPlaces;
import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.domain.Place;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class PlaceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlaceController.class);
    @Autowired
    private PlaceService placeService;

    public PlaceController() {
    }

    public String addPlace(int number) {
        LOGGER.info("Method addPlace");
        LOGGER.trace("Parameter number: {}", number);
        try {
            placeService.addPlace(number);
            return "-place \"" + number + "\" has been added to service";
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String checkPlaces() {
        LOGGER.info("Method checkPlaces");
        try {
            if (placeService.getNumberPlace() == 0) {
                throw new  BusinessException("There are no places");
            }
            return "verification was successfully";
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getPlaces() {
        LOGGER.info("Method getArrayPlace");
        try {
            return StringPlaces.getStringFromPlaces(placeService.getPlaces());
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public List<String> getPlacesWithId() {
        LOGGER.info("Method getArrayPlace");
        List<String> stringList = new ArrayList<>();
        try {
            List<Place> places = placeService.getPlaces();
            stringList.add(StringPlaces.getStringFromPlaces(places));
            stringList.addAll(StringPlaces.getListId(places));
            return stringList;
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            stringList.add(e.getMessage());
            return stringList;
        }
    }

    public String deletePlace(Long idPlace) {
        LOGGER.info("Method deletePlace");
        LOGGER.trace("Parameter idPlace: {}", idPlace);
        try {
            placeService.deletePlace(idPlace);
            return " -delete place in service successfully";
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public List<String> getFreePlacesByDate(String stringExecuteDate) {
        LOGGER.info("Method getFreePlacesByDate");
        LOGGER.trace("Parameter stringExecuteDate: {}", stringExecuteDate);
        List<String> stringList = new ArrayList<>();
        try {
            Date executeDate = DateUtil.getDatesFromString(stringExecuteDate, true);
            List<Place> places = placeService.getFreePlaceByDate(executeDate);
            stringList.add(StringPlaces.getStringFromPlaces(places));
            stringList.addAll(StringPlaces.getListId(places));
            return stringList;
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            stringList.add(e.getMessage());
            return stringList;
        }
    }
}