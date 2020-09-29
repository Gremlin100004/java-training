package com.senla.carservice.controller;

import com.senla.carservice.controller.util.StringPlaces;
import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.dto.PlaceDto;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.util.DateUtil;
import com.senla.carservice.util.exception.DateException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@NoArgsConstructor
@Slf4j
public class PlaceController {
    @Autowired
    private PlaceService placeService;

    public String addPlace(int number) {
        log.info("Method addPlace");
        log.trace("Parameter number: {}", number);
        try {
            placeService.addPlace(number);
            return "-place \"" + number + "\" has been added to service";
        } catch (BusinessException | DaoException e) {
            log.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String checkPlaces() {
        log.info("Method checkPlaces");
        try {
            if (placeService.getNumberPlace() == 0) {
                throw new  BusinessException("There are no places");
            }
            return "verification was successfully";
        } catch (BusinessException e) {
            log.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getPlaces() {
        log.info("Method getArrayPlace");
        try {
            return StringPlaces.getStringFromPlaces(placeService.getPlaces());
        } catch (BusinessException | DaoException e) {
            log.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public List<String> getPlacesWithId() {
        log.info("Method getArrayPlace");
        List<String> stringList = new ArrayList<>();
        try {
            List<PlaceDto> placesDto = placeService.getPlaces();
            stringList.add(StringPlaces.getStringFromPlaces(placesDto));
            stringList.addAll(StringPlaces.getListId(placesDto));
            return stringList;
        } catch (BusinessException | DaoException e) {
            log.warn(e.getMessage());
            stringList.add(e.getMessage());
            return stringList;
        }
    }

    public String deletePlace(Long idPlace) {
        log.info("Method deletePlace");
        log.trace("Parameter idPlace: {}", idPlace);
        try {
            placeService.deletePlace(idPlace);
            return " -delete place in service successfully";
        } catch (BusinessException | DaoException e) {
            log.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public List<String> getFreePlacesByDate(String stringExecuteDate) {
        log.info("Method getFreePlacesByDate");
        log.trace("Parameter stringExecuteDate: {}", stringExecuteDate);
        List<String> stringList = new ArrayList<>();
        try {
            Date executeDate = DateUtil.getDatesFromString(stringExecuteDate, true);
            List<PlaceDto> placesDto = placeService.getFreePlaceByDate(executeDate);
            stringList.add(StringPlaces.getStringFromPlaces(placesDto));
            stringList.addAll(StringPlaces.getListId(placesDto));
            return stringList;
        } catch (BusinessException | DateException | DaoException e) {
            log.warn(e.getMessage());
            stringList.add(e.getMessage());
            return stringList;
        }
    }
}