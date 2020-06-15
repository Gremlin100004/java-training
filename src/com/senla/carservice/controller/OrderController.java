package com.senla.carservice.controller;

import com.senla.carservice.domain.Car;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.dto.OrderDto;
import com.senla.carservice.service.OrderService;
import com.senla.carservice.service.OrderServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrderController {
    private final OrderService orderService;

    public OrderController() {
        this.orderService = new OrderServiceImpl();
    }

    public String addOrder(OrderDto orderDto) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        Date executionStartTime;
        Date leadTime;
        try {
            executionStartTime = format.parse(orderDto.getExecutionStartTime());
            leadTime = format.parse(orderDto.getLeadTime());
        } catch (ParseException e) {
            return "Error date, should be \"dd.MM.yyyy hh:mm\"";
        }
        if (executionStartTime.compareTo(leadTime) > 0){
            return "Error!!!, Lead time can't be early then planning time to start working!";
        }
        if (executionStartTime.compareTo(new Date()) < 1){
            return "Error!!!, You can't start work at past!";
        }
        Car car = new Car(orderDto.getAutomaker(), orderDto.getModel(), orderDto.getRegistrationNumber());
        Order order = new Order(executionStartTime, leadTime, orderDto.getMasters(), orderDto.getGarage(),
                orderDto.getPlace(), car, orderDto.getPrice());
        this.orderService.addOrder(order);
        return "order add successfully!";
    }

    public ArrayList<Order> getOrders() {
        return this.orderService.getOrders();
    }

    public String completeOrder(Order order) {
        boolean statusOperation = this.orderService.completeOrder(order);
        if (statusOperation) {
            return " - the order has been transferred to execution status";
        } else {
            return " -the order is deleted.";
        }
    }

    public String closeOrder(Order order) {
        boolean statusOperation = this.orderService.closeOrder(order);
        if (statusOperation) {
            return " -the order has been completed.";
        } else {
            return " -the order is deleted.";
        }
    }

    public String cancelOrder(Order order) {
        boolean statusOperation = this.orderService.cancelOrder(order);
        if (statusOperation) {
            return " -the order has been canceled.";
        } else {
            return " -the order is deleted.";
        }
    }

    public String deleteOrder(Order order) {
        boolean statusOperation = this.orderService.deleteOrder(order);
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
        boolean statusOperation = this.orderService.shiftLeadTime(order, executionStartTime, leadTime);
        if (statusOperation) {
            return " -the order lead time has been changed.";
        } else {
            return " -the order is deleted.";
        }
    }

    public ArrayList<Order> sortOrderByCreationTime(ArrayList<Order> orders) {
        return this.orderService.sortOrderCreationTime(orders);
    }

    public ArrayList<Order> sortOrderByLeadTime(ArrayList<Order> orders) {
        return this.orderService.sortOrderByLeadTime(orders);
    }

    public ArrayList<Order> sortOrderByStartTime(ArrayList<Order> orders) {
        return this.orderService.sortOrderByStartTime(orders);
    }

    public ArrayList<Order> sortOrderByPrice(ArrayList<Order> orders) {
        return this.orderService.sortOrderByPrice(orders);
    }

    public ArrayList<Order> getExecuteOrder() {
        return this.orderService.getCurrentRunningOrders();
    }

    public ArrayList<Order> getOrdersByPeriod(String startPeriod, String endPeriod) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        Date startPeriodDate;
        Date endPeriodDate;
        try {
            startPeriodDate = format.parse(startPeriod);
            endPeriodDate = format.parse(endPeriod);
        } catch (ParseException e) {
            startPeriodDate = null;
            endPeriodDate = null;
        }
        ArrayList<Order> orders = this.orderService.getOrders();
        orders = this.orderService.sortOrderByPeriod(orders, startPeriodDate, endPeriodDate);
        return orders;
    }

    public ArrayList<Order> getCompletedOrders(ArrayList<Order> orders) {
        return this.orderService.getCompletedOrders(orders);
    }

    public ArrayList<Order> getCanceledOrders(ArrayList<Order> orders) {
        return this.orderService.getCanceledOrders(orders);
    }

    public ArrayList<Order> getDeletedOrders(ArrayList<Order> orders) {
        return this.orderService.getDeletedOrders(orders);
    }

    public ArrayList<Order> getMasterOrders(Master master) {
        return this.orderService.getMasterOrders(master);
    }

    public ArrayList<Master> getOrderMasters(Order order) {
        return this.orderService.getOrderMasters(order);
    }
}