package com.senla.carservice.controller;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Order;
import com.senla.carservice.service.*;
import com.senla.carservice.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CarOfficeController {

    public CarOfficeController() {
    }

    public String getFreePlacesByDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        final int hour = 23;
        final int minute = 59;
        int numberGeneralPlace = 0;
        int numberMastersOrders = 0;
        Date dateFree;
        OrderService orderService = new OrderServiceImpl();
        GarageService garageService = new GarageServiceImpl();
        MasterService masterService = new MasterServiceImpl();
        try {
            dateFree = format.parse(date);
        } catch (ParseException e) {
            return "error date, shoud be dd.MM.yyyy";
        }
        Date endDay = DateUtil.addHourMinutes(dateFree, hour, minute);
        Order[] orders = orderService.getOrders();
        int numberPlaceOrders = orderService.sortOrderByPeriod(orders, dateFree, endDay).length;
        for (Garage garage : garageService.getGarages())
            numberGeneralPlace += garage.getPlaces().length;
        int numberFreePlace = numberGeneralPlace - numberPlaceOrders;
        orders = orderService.sortOrderByPeriod(orders, dateFree, endDay);
        int numberGeneralMasters = masterService.getMasters().length;
        for (Order order : orders)
            numberMastersOrders += order.getMasters().length;
        int numberFreeMasters = numberGeneralMasters - numberMastersOrders;
        return String.format("- number free places in service: %s\n - number free masters in service: %s", numberFreePlace, numberFreeMasters);
    }

    public String getNearestFreeDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        final int hour = 23;
        final int minute = 59;
        int numberGeneralPlace;
        int numberMastersOrders;
        int numberPlaceOrders;
        int numberFreePlace;
        int numberGeneralMasters;
        OrderService orderService = new OrderServiceImpl();
        GarageService garageService = new GarageServiceImpl();
        MasterService masterService = new MasterServiceImpl();
        Date dateFree = DateUtil.getDateWithoutTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
        while (true) {
            numberGeneralPlace = 0;
            numberMastersOrders = 0;
            Date endDay = DateUtil.addHourMinutes(dateFree, hour, minute);
            Order[] orders = orderService.getOrders();
            numberPlaceOrders = orderService.sortOrderByPeriod(orders, dateFree, endDay).length;
            for (Garage garage : garageService.getGarages())
                numberGeneralPlace += garage.getPlaces().length;
            numberFreePlace = numberGeneralPlace - numberPlaceOrders;
            orders = orderService.sortOrderByPeriod(orders, dateFree, endDay);
            numberGeneralMasters = masterService.getMasters().length;
            for (Order order : orders)
                numberMastersOrders += order.getMasters().length;
            int numberFreeMasters = numberGeneralMasters - numberMastersOrders;
            if (numberFreeMasters > 1 && numberFreePlace > 0) {
                return String.format(" - nearest free date: %s", dateFormat.format(dateFree.getTime()));
            }
            dateFree = DateUtil.addDays(dateFree, 1);
        }
    }
}