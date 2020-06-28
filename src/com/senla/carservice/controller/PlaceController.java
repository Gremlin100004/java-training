package com.senla.carservice.controller;

import com.senla.carservice.domain.Order;
import com.senla.carservice.exception.DateException;
import com.senla.carservice.exception.NullDateException;
import com.senla.carservice.exception.NumberObjectZeroException;
import com.senla.carservice.service.OrderService;
import com.senla.carservice.service.OrderServiceImpl;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.service.PlaceServiceImpl;
import com.senla.carservice.ui.string.StringPlaces;
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
            return String.valueOf(e);
        }
    }

    public String deletePlace(int index) {
        try {
            placeService.deletePlace(placeService.getPlaces().get(index));
            return String.format(" -delete place in service number \"%s\"", placeService.getPlaces().get(index).getNumber());
        } catch (NumberObjectZeroException e) {
            return String.valueOf(e);
        } catch (IndexOutOfBoundsException e){
            return "There are no such place";
        }
    }

    public String getFreePlaces() {
        try {
            return StringPlaces.getStringFromPlaces(placeService.getFreePlaces());
        } catch (NumberObjectZeroException e) {
            return String.valueOf(e);
        }
    }

    public String getFreePlacesByDate(String stringExecuteDate, String stringLeadDate) {
        Date executeDate = DateUtil.getDatesFromString(stringExecuteDate);
        Date leadDate = DateUtil.getDatesFromString(stringLeadDate);
        try {
            List<Order> orders = orderService.getOrderByPeriod(executeDate, leadDate);
            return StringPlaces.getStringFromPlaces(placeService.getFreePlaceByDate(executeDate, leadDate, orders));
        } catch (NumberObjectZeroException | DateException | NullDateException e) {
            return String.valueOf(e);
        }
    }

//    public String exportGarages() {
//        // использовать исключения
//        if (this.placeService.exportPlaces().equals("save successfully")) {
//            return "Garages have been export successfully!";
//        } else {
//            return "export problem.";
//        }
//    }
//
//    public String importGarages() {
//        if (this.placeService.importPlaces().equals("import successfully")) {
//            return "Garages have been import successfully!";
//        } else {
//            return "import problem.";
//        }
//    }
}