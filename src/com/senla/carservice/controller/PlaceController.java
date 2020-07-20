package com.senla.carservice.controller;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.dependencyinjection.annotation.Dependency;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.service.OrderService;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.util.DateUtil;
import com.senla.carservice.util.stringutil.StringPlaces;

import java.util.Date;

@Singleton
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
        try {
            Date executeDate = DateUtil.getDatesFromString(stringExecuteDate, true);
            return StringPlaces.getStringFromPlaces(placeService.getFreePlaceByDate(executeDate));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }
}