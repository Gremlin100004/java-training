package com.senla.carservice.controller;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.service.CarOfficeService;
import com.senla.carservice.service.CarOfficeServiceImpl;
import com.senla.carservice.service.GarageService;
import com.senla.carservice.service.GarageServiceImpl;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.MasterServiceImpl;
import com.senla.carservice.service.OrderService;
import com.senla.carservice.service.OrderServiceImpl;
import com.senla.carservice.util.DateUtil;

import java.text.SimpleDateFormat;
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

    // контроллер отдает модель на юай - это нежелательно, это должны быть разные приложения (бэк и юай-фронт)
    public List<Garage> getGaragesFreePlace(String stringExecuteDate, String stringLeadDate) {
        Date executeDate = DateUtil.getDatesFromString(stringExecuteDate);
        Date leadDate = DateUtil.getDatesFromString(stringLeadDate);
        List<Order> orders = this.orderService.getOrders();
        orders = this.orderService.getOrderByPeriod(orders, executeDate, leadDate);
        return this.garageService.getGaragesFreePlace(executeDate, leadDate, orders);
    }

    public List<Master> getFreeMasters(String stringExecuteDate, String stringLeadDate) {
        Date executeDate = DateUtil.getDatesFromString(stringExecuteDate);
        Date leadDate = DateUtil.getDatesFromString(stringLeadDate);
        List<Order> orders = this.orderService.getOrders();
        orders = this.orderService.getOrderByPeriod(orders, executeDate, leadDate);
        return this.masterService.getFreeMasters(executeDate, leadDate, orders);
    }

    public String getFreePlacesByDate(String date) {
        // у тебя есть утилита для работы с датами, не нужно держать тут эту логику
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
        // обычно this не пишут при обращении к полям сервиса (в контроллере) или к полю репозиторию (в сервисе) -
        // это и так понятно
        // не понимаю, зачем доставать два раза заказы и перезаписывать
        List<Order> orders = this.orderService.getOrders();
        orders = this.orderService.getOrderByPeriod(orders, dateFree, endDay);
        int numberFreeMasters = this.carOfficeService.getNumberFreeMasters(orders);
        int numberFreePlace = this.carOfficeService.getNumberFreePlaceDate(orders);
        return String.format("- number free places in service: %s\n- number free masters in service: %s", numberFreePlace, numberFreeMasters);
    }

    public String getNearestFreeDate() {
        final int startHour = 23;
        final int startMinute = 59;
        final int endHour = 23;
        final int endMinute = 59;
        // у тебя есть утилита для работы с датами
        Date dateFree = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
        // это бизнес логика, она должна быть в сервисе
        // контроллер может валидировать запрос, преобразовывать данные от ЮАЙ и преобразовывать данные
        // от сервиса для ЮАЙ, но логики здесь быть не должно
        //  кроме того, прошла тема исключений, почему бы не использовать их?
        // например, перенести все эти проверки в сервис, и в случае непрохождения проверки генерировать свое исключение
        // с каким-то месседжем, а здесь ловить и отдавать месседж на фронт
        if (this.masterService.getMasters().size() < 2 || this.garageService.getNumberPlaces() < 1) {
            return "Error!!! Add masters, garage and place to service!\n" +
                    " At least should be 2 masters, 1 garage and 1 place.";
        }
        int numberFreeMasters = 0;
        int numberFreePlace = 0;
        // бизнес логика не должна быть в контроллере
        while (numberFreeMasters == 0 && numberFreePlace == 0) {
            Date endDay = DateUtil.addHourMinutes(dateFree, endHour, endMinute);
            List<Order> orders = this.orderService.getOrders();
            orders = this.orderService.getOrderByPeriod(orders, dateFree, endDay);
            numberFreeMasters = this.carOfficeService.getNumberFreeMasters(orders);
            numberFreePlace = this.carOfficeService.getNumberFreePlaceDate(orders);
            dateFree = DateUtil.addDays(dateFree, 1);
        }
        dateFree = DateUtil.addDays(dateFree, -1);
        return String.format("Nearest free date: %s", dateFormat.format(DateUtil.addHourMinutes(dateFree, startHour, startMinute).getTime()));
    }
}