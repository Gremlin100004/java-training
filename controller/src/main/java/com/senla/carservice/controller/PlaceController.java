package com.senla.carservice.controller;

import com.senla.carservice.util.DateUtil;
import com.senla.carservice.domain.Place;
import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.controller.util.StringPlaces;
import com.senla.carservice.util.exception.DateException;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.hibernatedao.exception.DaoException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Date;

@Singleton
public class PlaceController {

    @Dependency
    private PlaceService placeService;
    private static final Logger LOGGER = LogManager.getLogger(PlaceController.class);


    public PlaceController() {
    }

    public String addPlace(int number) {
        LOGGER.info("Method addPlace");
        LOGGER.trace("Parameter number: " + number);
        try {
            placeService.addPlace(number);
            return "-place \"" + number + "\" has been added to service";
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getArrayPlace() {
        LOGGER.info("Method getArrayPlace");
        try {
            return StringPlaces.getStringFromPlaces(placeService.getPlaces());
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String deletePlace(int index) {
        LOGGER.info("Method deletePlace");
        LOGGER.trace("Parameter index: " + index);
        try {
            if (placeService.getNumberPlace() < index || index < 0) {
                return "There are no such place";
            } else {
                Place deletedPlace = placeService.getPlaces().get(index);
                placeService.deletePlace(deletedPlace);
                return " -delete place in service number \"" + deletedPlace.getNumber() + "\"";
            }
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getFreePlacesByDate(String stringExecuteDate) {
        LOGGER.info("Method getFreePlacesByDate");
        LOGGER.trace("Parameter stringExecuteDate: " + stringExecuteDate);
        try {
            Date executeDate = DateUtil.getDatesFromString(stringExecuteDate, true);
            return StringPlaces.getStringFromPlaces(placeService.getFreePlaceByDate(executeDate));
        } catch (BusinessException | DaoException | DateException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }
}