package com.senla.carservice.controller;

import com.senla.carservice.factory.annotation.Dependency;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.exception.DateException;
import com.senla.carservice.service.OrderService;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.stringutil.StringPlaces;
import com.senla.carservice.util.DateUtil;

import java.util.Date;

public class PlaceController {
    @Dependency
    private PlaceService placeService;
    @Dependency
    private OrderService orderService;

    public PlaceController() {
    }

    public String addPlace(int number) {
        try {
            placeService.addPlace(number);
            return "-place \"" + number + "\" has been added to service";
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getArrayPlace() {
        try {
            return StringPlaces.getStringFromPlaces(placeService.getPlaces());
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String deletePlace(int index) {
        try {
            if (placeService.getPlaces().size() < index || index < 0) {
                return "There are no such place";
            } else {
                placeService.deletePlace(placeService.getPlaces().get(index));
                return " -delete place in service number \"" + placeService.getPlaces().get(index).getNumber() + "\"";
            }
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getFreePlacesByDate(String stringExecuteDate) {
        Date executeDate = DateUtil.getDatesFromString(stringExecuteDate, true);
        try {
            return StringPlaces.getStringFromPlaces(placeService.getFreePlaceByDate(executeDate));
        } catch (BusinessException | DateException e) {
            return e.getMessage();
        }
    }
}