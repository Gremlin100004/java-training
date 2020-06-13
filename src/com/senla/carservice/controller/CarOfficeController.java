package com.senla.carservice.controller;

import com.senla.carservice.domain.Order;
import com.senla.carservice.service.*;
import com.senla.carservice.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CarOfficeController {
    private final CarOfficeService carOfficeService;
    private final OrderService orderService;

    public CarOfficeController() {
        this.carOfficeService = CarOfficeServiceImpl.getInstance();
        this.orderService = OrderServiceImpl.getInstance();
    }

    public String getFreePlacesByDate(String date) {
        final int hour = 23;
        final int minute = 59;
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date dateFree;
        try {
            dateFree = format.parse(date);
        } catch (ParseException e) {
            return "error date, shoud be dd.MM.yyyy";
        }
        Date endDay = DateUtil.addHourMinutes(dateFree, hour, minute);
        Order[] orders = this.orderService.getOrders();
        orders = this.orderService.sortOrderByPeriod(orders, dateFree, endDay);
        int numberFreeMasters = this.carOfficeService.getNumberFreeMasters(orders);
        int numberFreePlace = this.carOfficeService.getNumberFreePlaceDate(orders);
        return String.format("- number free places in service: %s\n - number free masters in service: %s", numberFreePlace, numberFreeMasters);
    }

    public String getNearestFreeDate() {
        final int hour = 23;
        final int minute = 59;
        Date dateFree = DateUtil.getDateWithoutTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
        while (true) {
            Date endDay = DateUtil.addHourMinutes(dateFree, hour, minute);
            Order[] orders = this.orderService.getOrders();
            orders = this.orderService.sortOrderByPeriod(orders, dateFree, endDay);
            int numberFreeMasters = this.carOfficeService.getNumberFreeMasters(orders);
            int numberFreePlace = this.carOfficeService.getNumberFreePlaceDate(orders);
            if (numberFreeMasters > 1 && numberFreePlace > 0) {
                break;
            }
            dateFree = DateUtil.addDays(dateFree, 1);
        }
        return String.format(" - nearest free date: %s", dateFormat.format(dateFree.getTime()));
    }
}