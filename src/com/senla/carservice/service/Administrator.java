package com.senla.carservice.service;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Place;
import com.senla.carservice.repository.CarService;
import com.senla.carservice.repository.Garage;
import com.senla.carservice.repository.Order;
import com.senla.carservice.util.DateUtil;

import java.util.Date;

public class Administrator implements IAdministrator {
    private final CarService carService;
    private final IMasterService masterService;
    private final IGarageService garageService;
    private final IOrderService orderService;

    public Administrator(String name) {
        this.carService = new CarService(name);
        this.masterService = new MasterService(this.carService);
        this.garageService = new GarageService(this.carService);
        this.orderService = new OrderService(this.carService);
    }

    @Override
    public String getCarServiceName() {
        return this.carService.getName();
    }

    @Override
    public Order[] getOrders() {
        return this.orderService.getOrders();
    }

    @Override
    public void addOrder(Order order) {
        this.orderService.addOrder(order);
    }

    @Override
    public boolean completeOrder(Order order) {
        return this.orderService.completeOrder(order);
    }

    @Override
    public boolean deleteOrder(Order order) {
        return this.orderService.deleteOrder(order);
    }

    @Override
    public boolean cancelOrder(Order order) {
        return this.orderService.cancelOrder(order);
    }

    @Override
    public boolean closeOrder(Order order) {
        return this.orderService.closeOrder(order);
    }

    @Override
    public boolean shiftLeadTime(Order order, Date executionStartTime, Date leadTime) {
        return this.orderService.shiftLeadTime(order, executionStartTime, leadTime);
    }

    @Override
    public Order[] sortOrderCreationTime(Order[] order) {
        return this.orderService.sortOrderCreationTime(order);
    }

    @Override
    public Order[] sortOrderByLeadTime(Order[] order) {
        return this.orderService.sortOrderByLeadTime(order);
    }

    @Override
    public Order[] sortOrderByStartTime(Order[] order) {
        return this.orderService.sortOrderByStartTime(order);
    }

    @Override
    public Order[] sortOrderByPrice(Order[] order) {
        return this.orderService.sortOrderByPrice(order);
    }

    @Override
    public Order[] sortOrderByPeriod(Order[] orders, Date startPeriod, Date endPeriod) {
        return this.orderService.sortOrderByPeriod(orders, startPeriod, endPeriod);
    }

    @Override
    public Order[] getCurrentRunningOrders() {
        return this.orderService.getCurrentRunningOrders();
    }

    @Override
    public Order[] getMasterOrders(Master master) {
        return this.orderService.getMasterOrders(master);
    }

    @Override
    public Master[] getOrderMasters(Order order) {
        return this.orderService.getOrderMasters(order);
    }

    @Override
    public Order[] getCompletedOrders(Order[] orders) {
        return this.orderService.getCompletedOrders(orders);
    }

    @Override
    public Order[] getCanceledOrders(Order[] orders) {
        return this.orderService.getCanceledOrders(orders);
    }

    @Override
    public Order[] getDeletedOrders(Order[] orders) {
        return this.orderService.getDeletedOrders(orders);
    }

    @Override
    public Master[] getMasters() {
        return this.masterService.getMasters();
    }

    @Override
    public void addMaster(String name) {
        this.masterService.addMaster(name);
    }

    @Override
    public void deleteMaster(Master master) {
        this.masterService.deleteMaster(master);
    }

    @Override
    public Master[] sortMasterByAlphabet(Master[] masters) {
        return this.masterService.sortMasterByAlphabet(masters);
    }

    @Override
    public Master[] sortMasterByBusy(Master[] masters) {
        return this.masterService.sortMasterByBusy(masters);
    }

    @Override
    public Garage[] getGarage() {
        return this.garageService.getGarage();
    }

    @Override
    public void addGarage(String name) {
        this.garageService.addGarage(name);
    }

    @Override
    public void deleteGarage(Garage garage) {
        this.garageService.deleteGarage(garage);
    }

    @Override
    public void addGaragePlace(Garage garage) {
        this.garageService.addGaragePlace(garage);
    }

    @Override
    public int getNumberGaragePlaces(Garage garage) {
        return this.garageService.getNumberGaragePlaces(garage);
    }

    @Override
    public void deleteGaragePlace(Garage garage) {
        this.garageService.deleteGaragePlace(garage);
    }

    @Override
    public Place[] getFreePlaceGarage(Garage garage) {
        return this.garageService.getFreePlaceGarage(garage);
    }

    @Override
    public int getNumberFreePlaceDate(Date date) {
        final int hour = 23;
        final int minute = 59;
        int numberGeneralPlace = 0;
        Date endDay = DateUtil.addHourMinutes(date, hour, minute);
        Order[] orders = this.orderService.getOrders();
        int numberPlaceOrders = this.orderService.sortOrderByPeriod(orders, date, endDay).length;
        for (Garage garage : this.carService.getGarages())
            numberGeneralPlace += garage.getPlaces().length;
        return numberGeneralPlace - numberPlaceOrders;
    }

    @Override
    public int getNumberFreeMasters(Date date) {
        final int hour = 23;
        final int minute = 59;
        int numberMastersOrders = 0;
        Date endDay = DateUtil.addHourMinutes(date, hour, minute);
        Order[] orders = this.orderService.getOrders();
        orders = this.orderService.sortOrderByPeriod(orders, date, endDay);
        int numberGeneralMasters = this.carService.getMasters().length;
        for (Order order : orders)
            numberMastersOrders += order.getMasters().length;
        return numberGeneralMasters - numberMastersOrders;
    }

    @Override
    public Date getNearestFreeDate() {
        Date date = DateUtil.getDateWithoutTime();
        while (true) {
            if (this.getNumberFreeMasters(date) > 1 && this.getNumberFreePlaceDate(date) > 0) {
                return date;
            }
            date = DateUtil.addDays(date, 1);
        }
    }
}