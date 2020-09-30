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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
@NoArgsConstructor
@Slf4j
public class PlaceController {
    @Autowired
    private PlaceService placeService;

    @PostMapping("places/add")
    //ToDo add PlaceDto
    public String addPlace(@RequestBody int number) {
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

    @GetMapping("places/check")
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

    @GetMapping("places/get")
    public List<PlaceDto> getPlaces() {
        log.info("Method getArrayPlace");
        try {
            return placeService.getPlaces();
        } catch (BusinessException | DaoException e) {
            log.warn(e.getMessage());
            return null;
        }
    }

    @GetMapping("places/get/id")
    //ToDo delete this method in ui and controller
    public List<PlaceDto> getPlacesWithId() {
        log.info("Method getArrayPlace");
        try {
            return placeService.getPlaces();
        } catch (BusinessException | DaoException e) {
            log.warn(e.getMessage());
            return null;
        }
    }

    @DeleteMapping("places/delete/{idPlace}")
    public String deletePlace(@PathVariable Long idPlace) {
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

    @GetMapping("places/get/free/by-date")
    public List<PlaceDto> getFreePlacesByDate(@RequestHeader String stringExecuteDate) {
        log.info("Method getFreePlacesByDate");
        log.trace("Parameter stringExecuteDate: {}", stringExecuteDate);
        try {
            Date executeDate = DateUtil.getDatesFromString(stringExecuteDate, true);
            return placeService.getFreePlaceByDate(executeDate);
        } catch (BusinessException | DateException | DaoException e) {
            log.warn(e.getMessage());
            return null;
        }
    }

    //ToDo all method crud
}