package com.senla.carservice.controller;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.service.*;
import com.senla.carservice.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CarOfficeController {
    private static CarOfficeController instance;
    private final CarOfficeService carOfficeService;
    private final OrderService orderService;
    private final MasterService masterService;
    private final GarageService garageService;

    private CarOfficeController() {
        this.carOfficeService = CarOfficeServiceImpl.getInstance();
        this.orderService = OrderServiceImpl.getInstance();
        this.masterService = MasterServiceImpl.getInstance();
        this.garageService = GarageServiceImpl.getInstance();
    }

    public static CarOfficeController getInstance() {
        if (instance == null) {
            instance = new CarOfficeController();
        }
        return instance;
    }

    public List<Garage> getGaragesFreePlace(String stringExecuteDate, String stringLeadDate) {
        Date executeDate = DateUtil.getDatesFromString(stringExecuteDate);
        Date leadDate = DateUtil.getDatesFromString(stringLeadDate);
        List<Order> orders = this.orderService.getOrders();
        orders = this.orderService.sortOrderByPeriod(orders, executeDate, leadDate);
        return this.garageService.getGaragesFreePlace(executeDate, leadDate, orders);
    }

    public List<Master> getFreeMasters(String stringExecuteDate, String stringLeadDate) {
        Date executeDate = DateUtil.getDatesFromString(stringExecuteDate);
        Date leadDate = DateUtil.getDatesFromString(stringLeadDate);
        List<Order> orders = this.orderService.getOrders();
        orders = this.orderService.sortOrderByPeriod(orders, executeDate, leadDate);
        return this.masterService.getFreeMasters(executeDate, leadDate, orders);
    }

    public String getFreePlacesByDate(String date) {
        final int endDayHour = 23;
        final int endDayMinute = 59;
        final int startDayHour = 0;
        final int startDayMinute = 0;
        Date dateFree = DateUtil.getDatesFromString(date);
        if (dateFree == null){
            return "error date";
        }
        Date currentDate = DateUtil.addHourMinutes(new Date(), startDayHour, startDayMinute);
        Date endDay = DateUtil.addHourMinutes(dateFree, endDayHour, endDayMinute);
        if (currentDate.compareTo(endDay) > 0) {
            return "past date";
        }
        List<Order> orders = this.orderService.getOrders();
        orders = this.orderService.sortOrderByPeriod(orders, dateFree, endDay);
        int numberFreeMasters = this.carOfficeService.getNumberFreeMasters(orders);
        int numberFreePlace = this.carOfficeService.getNumberFreePlaceDate(orders);
        return String.format("- number free places in service: %s\n- number free masters in service: %s", numberFreePlace, numberFreeMasters);
    }

    public String getNearestFreeDate() {
        final int hour = 23;
        final int minute = 59;
        Date dateFree = DateUtil.getDateWithoutTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
        if (this.masterService.getMasters().size() < 2 || this.garageService.getNumberPlaces() < 1) {
            return "Error!!! Add masters, garage and place to service!\n" +
                    " At least should be 2 masters, 1 garage and 1 place.";
        }
        int numberFreeMasters = 0;
        int numberFreePlace = 0;
        while (numberFreeMasters == 0 && numberFreePlace == 0) {
            Date endDay = DateUtil.addHourMinutes(dateFree, hour, minute);
            List<Order> orders = this.orderService.getOrders();
            orders = this.orderService.sortOrderByPeriod(orders, dateFree, endDay);
            numberFreeMasters = this.carOfficeService.getNumberFreeMasters(orders);
            numberFreePlace = this.carOfficeService.getNumberFreePlaceDate(orders);
            dateFree = DateUtil.addDays(dateFree, 1);
        }
        return String.format("Nearest free date: %s", dateFormat.format(dateFree.getTime()));
    }
}