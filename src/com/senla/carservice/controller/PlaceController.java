package com.senla.carservice.controller;

import com.senla.carservice.domain.Order;
import com.senla.carservice.exception.DateException;
import com.senla.carservice.exception.NullDateException;
import com.senla.carservice.exception.NumberObjectZeroException;
import com.senla.carservice.service.OrderService;
import com.senla.carservice.service.OrderServiceImpl;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.service.PlaceServiceImpl;
import com.senla.carservice.string.StringPlaces;
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
        placeService.addPlace(number);
        return String.format("-place \"%s\" has been added to service", number);
    }

    public String getArrayPlace() {
        try {
            return StringPlaces.getStringFromPlaces(placeService.getPlaces());
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String deletePlace(int index) {
        try {
            placeService.deletePlace(placeService.getPlaces().get(index));
            return String.format(" -delete place in service number \"%s\"", placeService.getPlaces().get(index).getNumber());
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        } catch (IndexOutOfBoundsException e){
            return "There are no such place";
        }
    }

    public String getFreePlaces() {
        try {
            return StringPlaces.getStringFromPlaces(placeService.getFreePlaces());
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getFreePlacesByDate(String stringExecuteDate, String stringLeadDate) {
        Date executeDate = DateUtil.getDatesFromString(stringExecuteDate, true);
        Date leadDate = DateUtil.getDatesFromString(stringLeadDate, true);
        try {
            List<Order> orders = orderService.getOrderByPeriod(executeDate, leadDate);
            return StringPlaces.getStringFromPlaces(placeService.getFreePlaceByDate(executeDate, leadDate, orders));
        } catch (NumberObjectZeroException | DateException | NullDateException e) {
            return e.getMessage();
        }
    }

    public String exportPlaces() {
        try {
            placeService.exportPlaces();
            return "Places have been export successfully!";
        } catch (NumberObjectZeroException e){
            return e.getMessage();
        }
    }

    public String importPlaces() {
        try {
            return placeService.importPlaces();
        } catch (NumberObjectZeroException e){
            return e.getMessage();
        }
    }
}