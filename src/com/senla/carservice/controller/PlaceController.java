package com.senla.carservice.controller;

import com.senla.carservice.domain.Order;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.exception.DateException;
import com.senla.carservice.service.OrderService;
import com.senla.carservice.service.OrderServiceImpl;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.service.PlaceServiceImpl;
import com.senla.carservice.stringutil.StringPlaces;
import com.senla.carservice.util.DateUtil;

import java.util.Date;
import java.util.List;

public class PlaceController {
    private static PlaceController instance;
    private final PlaceService placeService;
    private final OrderService orderService;

    private PlaceController() {
        placeService = PlaceServiceImpl.getInstance();
        orderService = OrderServiceImpl.getInstance();
    }

    public static PlaceController getInstance() {
        if (instance == null) {
            instance = new PlaceController();
        }
        return instance;
    }

    public String addPlace(int number) {
        try {
            placeService.addPlace(number);
            return String.format("-place \"%s\" has been added to service", number);
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
                return String.format(" -delete place in service number \"%s\"",
                                     placeService.getPlaces().get(index).getNumber());
            }
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getFreePlacesByDate(String stringExecuteDate, String stringLeadDate) {
        Date executeDate = DateUtil.getDatesFromString(stringExecuteDate, true);
        Date leadDate = DateUtil.getDatesFromString(stringLeadDate, true);
        try {
            List<Order> orders = orderService.getOrderByPeriod(executeDate, leadDate);
            return StringPlaces.getStringFromPlaces(placeService.getFreePlaceByDate(executeDate, leadDate, orders));
        } catch (BusinessException | DateException e) {
            return e.getMessage();
        }
    }

    public String exportPlaces() {
        try {
            placeService.exportPlaces();
            return "Places have been export successfully!";
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String importPlaces() {
        try {
            placeService.importPlaces();
            return "places imported successfully";
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String serializePlace() {
        try {
            placeService.serializePlace();
            return "Places have been serialize successfully!";
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String deserializePlace() {
        try {
            placeService.deserializePlace();
            return "Masters have been deserialize successfully!";
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }
}