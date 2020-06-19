package com.senla.carservice.controller;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.service.*;
import com.senla.carservice.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public final class CarOfficeController {
    private static CarOfficeController instance;
    private final CarOfficeService carOfficeService;
    private final OrderService orderService;
    private final MasterService masterService;
    private final GarageService garageService;

    public CarOfficeController() {
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

    public ArrayList<Garage> getGarageFreePlace(String stringExecuteDate, String stringLeadDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date executeDate;
        Date leadDate;
        try {
            executeDate = format.parse(stringExecuteDate);
            leadDate = format.parse(stringLeadDate);
        } catch (ParseException e) {
            return new ArrayList<>();
        }
        ArrayList<Garage> freeGarages = new ArrayList<>(this.garageService.getGarages());
        ArrayList<Order> orders = this.orderService.getOrders();
        orders = this.orderService.sortOrderByPeriod(orders, executeDate, leadDate);
        for (Order order : orders) {
            for (Garage garage : freeGarages) {
                if (garage.equals(order.getGarage())) {
                    garage.getPlaces().remove(order.getPlace());
                    break;
                }
            }
        }
        return freeGarages;
    }

    public ArrayList<Master> getFreeMasters(String stringExecuteDate, String stringLeadDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date executeDate;
        Date leadDate;
        ArrayList<Master> freeMasters = new ArrayList<>(this.masterService.getMasters());
        try {
            executeDate = format.parse(stringExecuteDate);
            leadDate = format.parse(stringLeadDate);
        } catch (ParseException e) {
            return freeMasters;
        }
        ArrayList<Order> orders = this.orderService.getOrders();
        orders = this.orderService.sortOrderByPeriod(orders, executeDate, leadDate);
        for (Order order : orders) {
            order.getMasters().forEach(freeMasters::remove);
        }
        return freeMasters;
    }

    public String getFreePlacesByDate(String date) {
        final int endDayHour = 23;
        final int endDayMinute = 59;
        final int startDayHour = 0;
        final int startDayMinute = 0;
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date dateFree;
        try {
            dateFree = format.parse(date);
        } catch (ParseException e) {
            return "error date";
        }
        Date currentDate = DateUtil.addHourMinutes(new Date(), startDayHour, startDayMinute);
        Date endDay = DateUtil.addHourMinutes(dateFree, endDayHour, endDayMinute);
        if (currentDate.compareTo(endDay) > 0) {
            return "past date";
        }
        ArrayList<Order> orders = this.orderService.getOrders();
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
        while (true) {
            Date endDay = DateUtil.addHourMinutes(dateFree, hour, minute);
            ArrayList<Order> orders = this.orderService.getOrders();
            orders = this.orderService.sortOrderByPeriod(orders, dateFree, endDay);
            int numberFreeMasters = this.carOfficeService.getNumberFreeMasters(orders);
            int numberFreePlace = this.carOfficeService.getNumberFreePlaceDate(orders);
            if (numberFreeMasters > 1 && numberFreePlace > 0) {
                break;
            }
            dateFree = DateUtil.addDays(dateFree, 1);
        }
        return String.format("Nearest free date: %s", dateFormat.format(dateFree.getTime()));
    }
}