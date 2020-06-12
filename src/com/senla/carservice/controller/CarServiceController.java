package com.senla.carservice.controller;

import com.senla.carservice.repository.Garage;
import com.senla.carservice.domain.Master;
import com.senla.carservice.repository.Order;
import com.senla.carservice.repository.OrderDto;
import com.senla.carservice.service.Administrator;
import com.senla.carservice.service.IAdministrator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CarServiceController {
    private IAdministrator carService;
    private MasterController masterService;
    private OrderController orderService;
    private GarageController garageService;

    public CarServiceController() {
    }

    public String createCarService(String name) {
        this.carService = new Administrator(name);
        this.masterService = new MasterController(this.carService);
        this.orderService = new OrderController(this.carService);
        this.garageService = new GarageController(this.carService);
        return String.format("Car service named \"%s\" has been created.", carService.getCarServiceName());
    }

    public String addMaster(String name) {
        return this.masterService.addMaster(name);
    }

    public Master[] getMasters(){
        return this.masterService.getMasters();
    }

    public String deleteMaster(Master master) {
        return this.masterService.deleteMaster(master);
    }

    public Master[] sortMastersByAlphabet() {
        return this.masterService.sortMasterByAlphabet();
    }

    public Master[] sortMastersByBusy() {
        return this.masterService.sortMasterByBusy();
    }

    public String addGarage(String name) {
        return this.garageService.addGarage(name);
    }

    public Garage[] getArrayGarages(){
        return this.garageService.getArrayGarages();
    }

    public String deleteGarage(Garage garage) {
        return this.garageService.deleteGarage(garage);
    }

    public String addGaragePlace(Garage garage) {
        return this.garageService.addGaragePlace(garage);
    }

    public int getNumberGaragePlaces(Garage garage) {
        return this.garageService.getNumberGaragePlaces(garage);
    }

    public String deleteGaragePlace(Garage garage) {
        return this.garageService.deleteGaragePlace(garage);
    }

    public int getNumberFreePlaceGarage(Garage garage) {
        return this.garageService.getNumberFreePlaceGarage(garage);
    }

    public String addOrder(OrderDto order) {
        return this.orderService.addOrder(order);
    }

    public Order[] getOrders(){
        return this.orderService.getOrders();
    }

    public String completeOrder(Order order) {
        return this.orderService.completeOrder(order);
    }

    public String closeOrder(Order order) {
        return this.orderService.closeOrder(order);
    }

    public String cancelOrder(Order order) {
        return this.orderService.cancelOrder(order);
    }

    public String deleteOrder(Order order) {
        return this.orderService.deleteOrder(order);
    }

    public String shiftLeadTime(Order order, String executionStartTime, String leadTime) {
        return this.orderService.shiftLeadTime(order, executionStartTime, leadTime);
    }

    public Order[] sortOrderByCreationTime(Order[] orders) {
        return this.orderService.sortOrderByCreationTime(orders);
    }

    public Order[] sortOrderByLeadTime(Order[] orders) {
        return this.orderService.sortOrderByLeadTime(orders);
    }

    public Order[] sortOrderByStartTime(Order[] orders) {
        return this.orderService.sortOrderByStartTime(orders);
    }

    public Order[] sortOrderByPrice(Order[] orders) {
        return this.orderService.sortOrderByPrice(orders);
    }

    public Order[] getExecuteOrder() {
        return this.orderService.getExecuteOrder();
    }

    public Order[] getOrdersByPeriod(String startPeriod, String endPeriod){
        return this.orderService.getOrdersByPeriod(startPeriod, endPeriod);
    }

    public Order[] getCompletedOrders(Order[] orders) {
        return this.orderService.getCompletedOrders(orders);
    }

    public Order[] getCanceledOrders(Order[] orders) {
        return this.orderService.getCanceledOrders(orders);
    }

    public Order[] getDeletedOrders(Order[] orders) {
        return this.orderService.getDeletedOrders(orders);
    }

    public Order[] getMasterOrders(Master master){
        return this.orderService.getMasterOrders(master);
    }

    public Master[] getOrderMasters(Order order){
        return this.orderService.getOrderMasters(order);
    }

    public String getFreePlacesByDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date dateFree;
        try {
            dateFree = format.parse(date);
        } catch (ParseException e) {
            return "error date, shoud be dd.MM.yyyy";
        }
        int numberFreePlace = this.carService.getNumberFreePlaceDate(dateFree);
        int numberFreeMasters = this.carService.getNumberFreeMasters(dateFree);
        return String.format("- number free places in service: %s\n - number free masters in service: %s", numberFreePlace, numberFreeMasters);
    }

    public String getNearestFreeDate() {
        Date date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
        date = this.carService.getNearestFreeDate();
        return String.format(" - nearest free date: %s", dateFormat.format(date.getTime()));
    }
}