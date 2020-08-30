package com.senla.carservice;

import com.senla.carservice.annotation.Singleton;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.exception.DaoException;
import com.senla.carservice.exception.DateException;
import com.senla.carservice.objectadjuster.dependencyinjection.annotation.Dependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

@Singleton
public class PlaceController {
    @Dependency
    private PlaceService placeService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaceController.class);


    public PlaceController() {
    }

    public String addPlace(int number) {
        LOGGER.info("Method addPlace");
        LOGGER.debug("Parameter number: {}", number);
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
        LOGGER.debug("Parameter index: {}", index);
        try {
            if (placeService.getNumberPlace() < index || index < 0) {
                return "There are no such place";
            } else {
                placeService.deletePlace(placeService.getPlaceByIndex((long) index));
                return " -delete place in service number \"" + placeService.getPlaceByIndex((long) index).getNumber() + "\"";
            }
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getFreePlacesByDate(String stringExecuteDate) {
        LOGGER.info("Method getFreePlacesByDate");
        LOGGER.debug("Parameter stringExecuteDate: {}", stringExecuteDate);
        try {
            Date executeDate = DateUtil.getDatesFromString(stringExecuteDate, true);
            return StringPlaces.getStringFromPlaces(placeService.getFreePlaceByDate(executeDate));
        } catch (BusinessException | DaoException | DateException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }
}