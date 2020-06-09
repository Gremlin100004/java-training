package com.senla.carservice.controller;

import com.senla.carservice.controller.data.Data;
import com.senla.carservice.controller.service.GarageController;
import com.senla.carservice.controller.service.MasterController;
import com.senla.carservice.controller.service.OrderController;
import com.senla.carservice.controller.util.Printer;
import com.senla.carservice.services.Administrator;
import com.senla.carservice.services.IAdministrator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

// так модель или контроллер?
public class CarServiceModel {
    private Data data;
    private Printer printer;
    private IAdministrator carService;
    private MasterController masterService;
    private OrderController orderService;
    private GarageController garageService;

    public CarServiceModel() {
        this.data = new Data();
        this.printer = new Printer();
    }

    public void init() {
        this.carService = new Administrator(data.getNameCarService());
        this.masterService = new MasterController(this.carService, data);
        this.orderService = new OrderController(this.carService, data, printer);
        this.garageService = new GarageController(this.carService, data);
        System.out.println(String.format("Car service named \"%s\" has been created.", carService.getCarServiceName()));
        System.out.println("******************************");
    }

    public void addMaster() {
        this.masterService.addMaster();
    }

    public void deleteMaster() {
        this.masterService.deleteMaster();
    }

    public void getMasterByAlphabet() {
        this.masterService.getMasterByAlphabet();
    }

    public void AddGarage() {
        this.garageService.AddGarage();
    }

    public void deleteGarage() {
        this.garageService.deleteGarage();
    }

    public void addGaragePlace() {
        this.garageService.addGaragePlace();
    }

    public void deleteGaragePlace() {
        this.garageService.deleteGaragePlace();
    }

    public void getFreePlaceGarage() {
        this.garageService.getFreePlaceGarage();
    }

    public void addOrder() {
        this.orderService.addOrder();
    }

    public void completeOrder() {
        this.orderService.completeOrder();
    }

    public void deleteOrder() {
        this.orderService.deleteOrder();
    }

    public void closeOrder() {
        this.orderService.closeOrder();
    }

    public void cancelOrder() {
        this.orderService.cancelOrder();
    }

    public void shiftLeadTime() {
        this.orderService.shiftLeadTime();
    }

    public void getOrderByCreationTime() {
        this.orderService.getOrderByCreationTime();
    }

    public void getOrderByLeadTime() {
        this.orderService.getOrderByLeadTime();
    }

    public void getOrderByStartTime() {
        this.orderService.getOrderByStartTime();
    }

    public void getOrderByPrice() {
        this.orderService.getOrderByPrice();
    }

    public void getExecutedOrderByCreationTime() {
        this.orderService.getExecutedOrderByCreationTime();
    }

    public void getExecutedOrderByLeadTime() {
        this.orderService.getExecutedOrderByLeadTime();
    }

    public void getExecutedOrderByPrice() {
        this.orderService.getExecutedOrderByPrice();
    }

    public void getMasterOrder() {
        this.orderService.getMasterOrder();
    }

    public void getOrderMasters() {
        this.orderService.getOrderMasters();
    }

    public void getCompletedOrders() {
        this.orderService.getCompletedOrders();
    }

    public void getCanceledOrders() {
        this.orderService.getCanceledOrders();
    }

    public void getDeletedOrders() {
        this.orderService.getDeletedOrders();
    }

    public void getFreePlacesByDate() {
        this.printer.printOrder(this.carService.getOrders());
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
        Calendar date = new GregorianCalendar(2020, Calendar.JULY, 15);
        int numberFreePlace = this.carService.getNumberFreePlaceDate(date);
        int numberFreeMasters = this.carService.getNumberFreeMasters(date);
        System.out.println(String.format("Get number of free places on date: %s", dateFormat.format(date.getTime())));
        System.out.println(String.format("- number free places in service: %s", numberFreePlace));
        System.out.println(String.format("- number free masters in service: %s", numberFreeMasters));
        System.out.println("******************************************************************************************");
    }

    public void getNearestFreeDate() {
        Calendar date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
        date = this.carService.getNearestFreeDate();
        System.out.println(String.format("Get nearest free date: %s", dateFormat.format(date.getTime())));
        System.out.println("******************************************************************************************");
    }
}