package com.senla.carservice.controller;

import com.senla.carservice.domain.Order;
import com.senla.carservice.exception.DateException;
import com.senla.carservice.exception.NullDateException;
import com.senla.carservice.exception.NumberObjectZeroException;
import com.senla.carservice.service.CarOfficeService;
import com.senla.carservice.service.CarOfficeServiceImpl;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.service.PlaceServiceImpl;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.MasterServiceImpl;
import com.senla.carservice.service.OrderService;
import com.senla.carservice.service.OrderServiceImpl;
import com.senla.carservice.ui.string.StringMaster;
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
        this.carOfficeService = CarOfficeServiceImpl.getInstance();
        this.orderService = OrderServiceImpl.getInstance();
        this.masterService = MasterServiceImpl.getInstance();
        this.placeService = PlaceServiceImpl.getInstance();
    }

    public static CarOfficeController getInstance() {
        if (instance == null) {
            instance = new CarOfficeController();
        }
        return instance;
    }

    public String f(String stringExecuteDate, String stringLeadDate) {
        Date executeDate = DateUtil.getDatesFromString(stringExecuteDate);
        Date leadDate = DateUtil.getDatesFromString(stringLeadDate);
        try {
            return StringMaster.getStringFromMasters(masterService.getFreeMastersByDate
                    (executeDate, leadDate, orderService.getOrderByPeriod(executeDate, leadDate)));
        } catch (DateException | NumberObjectZeroException | NullDateException e) {
            return e.getMessage();
        }
    }

    public String getFreePlacesByDate(String date) {
        Date dateFree = DateUtil.getDatesFromString(date);
        if (dateFree == null){
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
        } catch (NullDateException | NumberObjectZeroException | DateException e) {
            return e.getMessage();
        }
    }

    public String getNearestFreeDate() {
        try {
            return carOfficeService.getNearestFreeDate();
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }
}