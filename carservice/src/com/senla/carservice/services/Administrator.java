// пакеты именуются в ед. числе
package com.senla.carservice.services;

import com.senla.carservice.domain.CarService;
import com.senla.carservice.domain.ICarService;
import com.senla.carservice.domain.IGarage;
import com.senla.carservice.domain.IMaster;
import com.senla.carservice.domain.IOrder;
import com.senla.carservice.domain.IPlace;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Administrator implements IAdministrator {
    private ICarService carService;
    private IMasterService masterService;
    private IGarageService garageService;
    private IOrderService orderService;

    public Administrator(String name) {
        this.carService = new CarService(name);
        this.masterService = new MasterService(this.carService);
        this.garageService = new GarageService(this.carService);
        this.orderService = new OrderService(this.carService);
    }

    @Override
    public String getCarServiceName() {
        // можно объединить в одну строку
        String carServiceName = this.carService.getName();
        return carServiceName;
    }

    @Override
    public IOrder[] getOrders() {
        return this.orderService.getOrders();
    }

    // оптимальное количество параметров у метода - до 3, максимальное - до 5, больше не рекомендуется
    @Override
    public void addOrder(Calendar executionStartTime,
                         Calendar leadTime,
                         IMaster[] masters,
                         IGarage garage,
                         IPlace place,
                         String automaker,
                         String model,
                         String registrationNumber,
                         BigDecimal price) {
        this.orderService.addOrder(executionStartTime, leadTime, masters, garage, place, automaker, model,
                registrationNumber, price);
    }

    @Override
    public boolean completeOrder(IOrder order) {
        return this.orderService.completeOrder(order);
    }

    @Override
    public boolean deleteOrder(IOrder order) {
        return this.orderService.deleteOrder(order);
    }

    @Override
    public boolean cancelOrder(IOrder order) {
        return this.orderService.cancelOrder(order);
    }

    @Override
    public boolean closeOrder(IOrder order) {
        return this.orderService.closeOrder(order);
    }

    @Override
    public boolean shiftLeadTime(IOrder order, Calendar executionStartTime, Calendar leadTime) {
        return this.orderService.shiftLeadTime(order, executionStartTime, leadTime);
    }

    @Override
    public IOrder[] sortOrderCreationTime(IOrder[] order) {
        return this.orderService.sortOrderCreationTime(order);
    }

    @Override
    public IOrder[] sortOrderByLeadTime(IOrder[] order) {
        return this.orderService.sortOrderByLeadTime(order);
    }

    @Override
    public IOrder[] sortOrderByStartTime(IOrder[] order) {
        return this.orderService.sortOrderByStartTime(order);
    }

    @Override
    public IOrder[] sortOrderByPrice(IOrder[] order) {
        return this.orderService.sortOrderByPrice(order);
    }

    @Override
    public IOrder[] sortOrderByPeriod(IOrder[] orders, Calendar startPeriod, Calendar endPeriod) {
        return this.orderService.sortOrderByPeriod(orders, startPeriod, endPeriod);
    }

    @Override
    public IOrder[] getCurrentRunningOrders() {
        return this.orderService.getCurrentRunningOrders();
    }

    @Override
    public IOrder[] getMasterOrders(IMaster master) {
        return this.orderService.getMasterOrders(master);
    }

    @Override
    public IMaster[] getOrderMasters(IOrder order) {
        return this.orderService.getOrderMasters(order);
    }

    @Override
    public IOrder[] getCompletedOrders() {
        return this.orderService.getCompletedOrders();
    }

    @Override
    public IOrder[] getCanceledOrders() {
        return this.orderService.getCanceledOrders();
    }

    @Override
    public IOrder[] getDeletedOrders() {
        return this.orderService.getDeletedOrders();
    }

    @Override
    public IMaster[] getMasters() {
        return this.masterService.getMasters();
    }

    @Override
    public void addMaster(String name) {
        this.masterService.addMaster(name);
    }

    @Override
    public void deleteMaster(IMaster master) {
        this.masterService.deleteMaster(master);
    }

    @Override
    public IMaster[] getMasterByAlphabet() {
        return this.masterService.getMasterByAlphabet();
    }

    @Override
    public IMaster[] getMasterByBusy() {
        return this.masterService.getMasterByBusy();
    }

    @Override
    public IGarage[] getGarage() {
        return this.garageService.getGarage();
    }

    @Override
    public void addGarage(String name) {
        this.garageService.addGarage(name);
    }

    @Override
    public void deleteGarage(IGarage garage) {
        this.garageService.deleteGarage(garage);
    }

    @Override
    public void addGaragePlace(IGarage garage) {
        this.garageService.addGaragePlace(garage);
    }

    @Override
    public void deleteGaragePlace(IGarage garage, IPlace place) {
        this.garageService.deleteGaragePlace(garage, place);
    }

    @Override
    public IPlace[] getFreePlaceGarage(IGarage garage) {
        return this.garageService.getFreePlaceGarage(garage);
    }

    @Override
    public int getNumberFreePlaceDate(Calendar date) {
        // так объявлять переменные в джава не принято,
        int numberFreePlace, numberPlaceOrders;
        int numberGeneralPlace = 0;
        Calendar dateEnd = new GregorianCalendar();
        // вместо этого проще использовать для хранения даты класс Date
        // чтобы задать дату, использовать ее строковое представление типа "18.02.2020" или любой другой
        // удобный формат, описать формат паттерном и использовать SimpleDateFormat
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        dateEnd.set(Calendar.YEAR, date.get(Calendar.YEAR));
        dateEnd.set(Calendar.MONTH, date.get(Calendar.MONTH));
        dateEnd.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
        dateEnd.set(Calendar.HOUR_OF_DAY, 23);
        dateEnd.set(Calendar.MINUTE, 59);
        dateEnd.set(Calendar.SECOND, 59);
        IOrder[] orders = this.orderService.getOrders();
        numberPlaceOrders = this.orderService.sortOrderByPeriod(orders, date, dateEnd).length;
        for (IGarage garage : this.carService.getGarages())
            numberGeneralPlace += garage.getPlaces().length;
        numberFreePlace = numberGeneralPlace - numberPlaceOrders;
        return numberFreePlace;
    }

    @Override
    public int getNumberFreeMasters(Calendar date) {
        int numberFreeMasters, numberGeneralMasters;
        int numberMastersOrders = 0;
        Calendar dateEnd = new GregorianCalendar();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        // почему администратор назначает эти даты? это тестовые данные? почему они в администраторе?
        dateEnd.set(Calendar.YEAR, date.get(Calendar.YEAR));
        dateEnd.set(Calendar.MONTH, date.get(Calendar.MONTH));
        dateEnd.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
        dateEnd.set(Calendar.HOUR_OF_DAY, 23);
        dateEnd.set(Calendar.MINUTE, 59);
        dateEnd.set(Calendar.SECOND, 59);
        IOrder[] orders = this.orderService.getOrders();
        orders = this.orderService.sortOrderByPeriod(orders, date, dateEnd);
        numberGeneralMasters = this.carService.getMasters().length;
        for (IOrder order : orders)
            numberMastersOrders += order.getMasters().length;
        numberFreeMasters = numberGeneralMasters - numberMastersOrders;
        return numberFreeMasters;
    }

    @Override
    public Calendar getNearestFreeDate() {
        Calendar date = Calendar.getInstance();
        int numberFreeMasters = 0;
        int numberFreePlaces = 0;
        while (numberFreeMasters < 2 && numberFreePlaces < 1) {
            numberFreeMasters = this.getNumberFreeMasters(date);
            numberFreePlaces = this.getNumberFreePlaceDate(date);
            date.add(Calendar.DATE, 1);
        }
        return date;
    }
}