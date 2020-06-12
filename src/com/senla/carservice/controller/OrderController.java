package com.senla.carservice.controller;

import com.senla.carservice.domain.Car;
import com.senla.carservice.domain.Master;
import com.senla.carservice.repository.Order;
import com.senla.carservice.repository.OrderDto;
import com.senla.carservice.service.IAdministrator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderController {
    private final IAdministrator carService;

    public OrderController(IAdministrator carService) {
        this.carService = carService;
    }

    public String addOrder(OrderDto orderDto) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        Date executionStartTime;
        Date leadTime;
        try {
            executionStartTime = format.parse(orderDto.getExecutionStartTime());
            leadTime = format.parse(orderDto.getLeadTime());
        } catch (ParseException e) {
            return "error date, should be dd.MM.yyyy";
        }
        Car car = new Car(orderDto.getAutomaker(), orderDto.getModel(), orderDto.getRegistrationNumber());
        Order order = new Order(executionStartTime, leadTime, orderDto.getMasters(), orderDto.getGarage(),
                orderDto.getPlace(), car, orderDto.getPrice());
        this.carService.addOrder(order);
        return "order add successfully!";
    }

    public Order[] getOrders() {
        return this.carService.getOrders();
    }

    public String completeOrder(Order order) {
        boolean statusOperation = this.carService.completeOrder(order);
        if (statusOperation) {
            return " - the order has been transferred to execution status";
        } else {
            return " -the order is deleted.";
        }
    }

    public String closeOrder(Order order) {
        boolean statusOperation = this.carService.closeOrder(order);
        if (statusOperation) {
            return " -the order has been completed.";
        } else {
            return " -the order is deleted.";
        }
    }

    public String cancelOrder(Order order) {
        boolean statusOperation = this.carService.cancelOrder(order);
        if (statusOperation) {
            return " -the order has been canceled.";
        } else {
            return " -the order is deleted.";
        }
    }

    public String deleteOrder(Order order) {
        boolean statusOperation = this.carService.deleteOrder(order);
        if (statusOperation) {
            return " -the order has been deleted.";
        } else {
            return " -the order is on a mission.";
        }
    }

    public String shiftLeadTime(Order order, String stringStartTime, String stringLeadTime) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        Date executionStartTime;
        Date leadTime;
        try {
            executionStartTime = format.parse(stringStartTime);
            leadTime = format.parse(stringLeadTime);
        } catch (ParseException e) {
            return "error date, shoud be dd.MM.yyyy";
        }
        boolean statusOperation = this.carService.shiftLeadTime(order, executionStartTime, leadTime);
        if (statusOperation) {
            return " -the order lead time has been changed.";
        } else {
            return " -the order is deleted.";
        }
    }

    public Order[] sortOrderByCreationTime(Order[] orders) {
        return this.carService.sortOrderCreationTime(orders);
    }

    public Order[] sortOrderByLeadTime(Order[] orders) {
        return this.carService.sortOrderByLeadTime(orders);
    }

    public Order[] sortOrderByStartTime(Order[] orders) {
        return this.carService.sortOrderByStartTime(orders);
    }

    public Order[] sortOrderByPrice(Order[] orders) {
        return this.carService.sortOrderByPrice(orders);
    }

    public Order[] getExecuteOrder() {
        return this.carService.getCurrentRunningOrders();
    }

    public Order[] getOrdersByPeriod(String startPeriod, String endPeriod) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        Date startPeriodDate;
        Date endPeriodDate;
        System.out.println(startPeriod);
        System.out.println(endPeriod);
        try {
            startPeriodDate = format.parse(startPeriod);
            endPeriodDate = format.parse(endPeriod);
        } catch (ParseException e) {
            startPeriodDate = null;
            endPeriodDate = null;
        }
        System.out.println(startPeriodDate);
        System.out.println(endPeriodDate);
        Order[] orders = this.carService.getOrders();
        orders = this.carService.sortOrderByPeriod(orders, startPeriodDate, endPeriodDate);
        return orders;
    }

    public Order[] getCompletedOrders(Order[] orders) {
        return this.carService.getCompletedOrders(orders);
    }

    public Order[] getCanceledOrders(Order[] orders) {
        return this.carService.getCanceledOrders(orders);
    }

    public Order[] getDeletedOrders(Order[] orders) {
        return this.carService.getDeletedOrders(orders);
    }

    public Order[] getMasterOrders(Master master) {
        return this.carService.getMasterOrders(master);
    }

    public Master[] getOrderMasters(Order order) {
        return this.carService.getOrderMasters(order);
    }
}