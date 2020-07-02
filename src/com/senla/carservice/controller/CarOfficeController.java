package com.senla.carservice.controller;

import com.senla.carservice.domain.Order;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.exception.DateException;
import com.senla.carservice.service.CarOfficeService;
import com.senla.carservice.service.CarOfficeServiceImpl;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.MasterServiceImpl;
import com.senla.carservice.service.OrderService;
import com.senla.carservice.service.OrderServiceImpl;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.service.PlaceServiceImpl;
import com.senla.carservice.util.DateUtil;

import java.util.Date;
import java.util.List;

public class CarOfficeController {
    private static CarOfficeController instance;
    private final CarOfficeService carOfficeService;
    private final OrderService orderService;
    private final MasterService masterService;
    private final PlaceService placeService;

    private CarOfficeController() {
        carOfficeService = CarOfficeServiceImpl.getInstance();
        orderService = OrderServiceImpl.getInstance();
        masterService = MasterServiceImpl.getInstance();
        placeService = PlaceServiceImpl.getInstance();
    }

    public static CarOfficeController getInstance() {
        if (instance == null) {
            instance = new CarOfficeController();
        }
        return instance;
    }

    public String getFreePlacesByDate(String date) {
        Date dateFree = DateUtil.getDatesFromString(date, false);
        if (dateFree == null) {
            return "error date";
        }
        Date startDayDate = DateUtil.bringStartOfDayDate(dateFree);
        Date endDayDate = DateUtil.bringStartOfDayDate(dateFree);
        try {
            List<Order> orders = orderService.getOrderByPeriod(startDayDate, endDayDate);
            int numberFreeMasters = masterService.getNumberFreeMastersByDate(startDayDate, endDayDate, orders);
            int numberFreePlace = placeService.getNumberFreePlaceByDate(startDayDate, endDayDate, orders);
            return String.format("- number free places in service: %s\n- number free masters in service: %s",
                                 numberFreePlace, numberFreeMasters);
        } catch (BusinessException | DateException e) {
            return e.getMessage();
        }
    }

    public String getNearestFreeDate() {
        try {
            return String
                .format("Nearest free date: %s", DateUtil.getStringFromDate(carOfficeService.getNearestFreeDate()));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }
}