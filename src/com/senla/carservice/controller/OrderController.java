package com.senla.carservice.controller;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.exception.DateException;
import com.senla.carservice.exception.NullDateException;
import com.senla.carservice.exception.NumberObjectZeroException;
import com.senla.carservice.exception.OrderStatusException;
import com.senla.carservice.service.CarOfficeService;
import com.senla.carservice.service.CarOfficeServiceImpl;
import com.senla.carservice.service.OrderService;
import com.senla.carservice.service.OrderServiceImpl;
import com.senla.carservice.util.DateUtil;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderController {
    private static OrderController instance;
    private final OrderService orderService;
    private final CarOfficeService carOfficeService;

    private OrderController() {
        orderService = OrderServiceImpl.getInstance();
        carOfficeService = CarOfficeServiceImpl.getInstance();
    }

    public static OrderController getInstance() {
        if (instance == null) {
            instance = new OrderController();
        }
        return instance;
    }

    public String addOrder(String automaker, String model, String registrationNumber) {
        orderService.addOrder(automaker, model, registrationNumber);
        return "order add successfully!";
    }

    public String addOrderDeadlines(String stringExecutionStartTime, String stringLeadTime) {
        Date executionStartTime = DateUtil.getDatesFromString(stringExecutionStartTime);
        Date leadTime = DateUtil.getDatesFromString(stringLeadTime);
        try {
            orderService.addOrderDeadlines(executionStartTime, leadTime);
            return "deadline add to order successfully";
        } catch (NullDateException e) {
            return "Error date format, should be \"dd.MM.yyyy hh:mm\"";
        } catch (DateException | NumberObjectZeroException e) {
            return String.valueOf(e);
        }
    }

    public String addOrderMasters(List<Master> masters) {
        try {
            orderService.addOrderMasters(masters);
            return "masters add successfully";
        } catch (NumberObjectZeroException e) {
            return String.valueOf(e);
        }
    }

    public String addOrderPlaces(Place place) {
        orderService.addOrderPlace(place);
        return "place add to order successfully";
    }

    public String addOrderPrice(BigDecimal price) {
        orderService.addOrderPrice(price);
        return "price add to order successfully";
    }

    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    public String completeOrder(Order order) {
        try {
            orderService.completeOrder(order);
            return " - the order has been transferred to execution status";
        } catch (OrderStatusException e) {
            return String.valueOf(e);
        }
    }

    public String closeOrder(Order order) {
        try {
            orderService.closeOrder(order);
            return " -the order has been completed.";
        } catch (OrderStatusException e) {
            return String.valueOf(e);
        }
    }

    public String cancelOrder(Order order) {
        try {
            orderService.cancelOrder(order);
            return " -the order has been canceled.";
        } catch (OrderStatusException e) {
            return String.valueOf(e);
        }
    }

    public String deleteOrder(Order order) {
        try {
            orderService.deleteOrder(order);
            return " -the order has been deleted.";
        } catch (OrderStatusException e) {
            return String.valueOf(e);
        }
    }

    public String shiftLeadTime(Order order, String stringStartTime, String stringLeadTime) {
        Date executionStartTime = DateUtil.getDatesFromString(stringStartTime);
        Date leadTime = DateUtil.getDatesFromString(stringLeadTime);
        try {
            orderService.shiftLeadTime(order, executionStartTime, leadTime);
            return " -the order lead time has been changed.";
        } catch (OrderStatusException | DateException e) {
            return String.valueOf(e);
        } catch (NullDateException e) {
            return "Error date format, should be \"dd.MM.yyyy hh:mm\"";
        }
    }
    //--------------------------------------------------
    public List<Order> sortOrderByCreationTime(List<Order> orders) {
        return this.orderService.sortOrderCreationTime(orders);
    }

    public List<Order> sortOrderByLeadTime(List<Order> orders) {
        return this.orderService.sortOrderByLeadTime(orders);
    }

    public List<Order> sortOrderByStartTime(List<Order> orders) {
        return this.orderService.sortOrderByStartTime(orders);
    }

    public List<Order> sortOrderByPrice(List<Order> orders) {
        return this.orderService.sortOrderByPrice(orders);
    }

    public List<Order> getExecuteOrder() {
        return this.orderService.getCurrentRunningOrders();
    }

    public String getOrdersByPeriod(String startPeriod, String endPeriod) {
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod);
        List<Order> orders = null;
        String stringOrders = "";
        try {
            orders = this.orderService.getOrderByPeriod(startPeriodDate, endPeriodDate);
        } catch (NullDateException e) {
            return "Error date format, should be \"dd.MM.yyyy hh:mm\"";
        }
        return stringOrders;
    }

    public List<Order> getCompletedOrders(List<Order> orders) {
        return this.orderService.getCompletedOrders(orders);
    }

    public List<Order> getCanceledOrders(List<Order> orders) {
        return this.orderService.getCanceledOrders(orders);
    }

    public List<Order> getDeletedOrders(List<Order> orders) {
        return this.orderService.getDeletedOrders(orders);
    }

    public List<Order> getMasterOrders(Master master) {
        return this.orderService.getMasterOrders(master);
    }

    public List<Master> getOrderMasters(Order order) {
        return this.orderService.getOrderMasters(order);
    }

//    public String exportOrders() {
//        if (this.orderService.exportOrder().equals("save successfully")) {
//            return "Orders have been export successfully!";
//        } else {
//            return "export problem.";
//        }
//    }
//
//    public String importOrders() {
//        String message = this.orderService.importOrder();
//        if (message.equals("import successfully")) {
//            return "Orders have been import successfully!";
//        } else {
//            return message;
//        }
//    }
}