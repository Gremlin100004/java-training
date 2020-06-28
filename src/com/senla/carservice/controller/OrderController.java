package com.senla.carservice.controller;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.exception.*;
import com.senla.carservice.service.*;
import com.senla.carservice.ui.string.StringOrder;
import com.senla.carservice.util.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

public class OrderController {
    private static OrderController instance;
    private final OrderService orderService;
    private final MasterService masterService;
    private final CarOfficeService carOfficeService;

    private OrderController() {
        orderService = OrderServiceImpl.getInstance();
        carOfficeService = CarOfficeServiceImpl.getInstance();
        masterService = MasterServiceImpl.getInstance();
    }

    public static OrderController getInstance() {
        if (instance == null) {
            instance = new OrderController();
        }
        return instance;
    }

    public String addOrder(String automaker, String model, String registrationNumber) {
        try {
            orderService.addOrder(automaker, model, registrationNumber);
            return "order add successfully!";
        } catch (NumberObjectZeroException e) {
            return String.valueOf(e);
        }
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

    public String addOrderMasters(int index) {
        try {
            orderService.addOrderMasters(masterService.getMasters().get(index));
            return "masters add successfully";
        } catch (NumberObjectZeroException | EqualObjectsException e) {
            return String.valueOf(e);
        }
    }

    public String addOrderPlaces(Place place) {
        try {
            orderService.addOrderPlace(place);
            return "place add to order successfully";
        } catch (NumberObjectZeroException e) {
            return String.valueOf(e);
        }
    }

    public String addOrderPrice(BigDecimal price) {
        try {
            orderService.addOrderPrice(price);
            return "price add to order successfully";
        } catch (NumberObjectZeroException e) {
            return String.valueOf(e);
        }
    }

    public String getOrders() {
        try {
            return StringOrder.getStringFromOrder(orderService.getOrders());
        } catch (NumberObjectZeroException e) {
            return String.valueOf(e);
        }
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

    public String sortOrderByCreationTime() {
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderCreationTime(orderService.getOrders()));
        } catch (NumberObjectZeroException e) {
            return String.valueOf(e);
        }
    }

    public String sortOrderByLeadTime() {
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByLeadTime(orderService.getOrders()));
        } catch (NumberObjectZeroException e) {
            return String.valueOf(e);
        }
    }

    public String sortOrderByStartTime() {
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByStartTime(orderService.getOrders()));
        } catch (NumberObjectZeroException e) {
            return String.valueOf(e);
        }
    }

    public String sortOrderByPrice() {
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByPrice(orderService.getOrders()));
        } catch (NumberObjectZeroException e) {
            return String.valueOf(e);
        }
    }

    public String getExecuteOrder() {
        try {
            return StringOrder.getStringFromOrder(orderService.getCurrentRunningOrders());
        } catch (NumberObjectZeroException e) {
            return String.valueOf(e);
        }
    }

    public String getOrdersByPeriod(String startPeriod, String endPeriod) {
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod);
        try {
            return StringOrder.getStringFromOrder(orderService.getOrderByPeriod(startPeriodDate, endPeriodDate));
        } catch (NullDateException e) {
            return "Error date format, should be \"dd.MM.yyyy hh:mm\"";
        } catch (NumberObjectZeroException | DateException e) {
            return String.valueOf(e);
        }
    }

    public String getCompletedOrders() {
        try {
            return StringOrder.getStringFromOrder(orderService.getCompletedOrders());
        } catch (NumberObjectZeroException e) {
            return String.valueOf(e);
        }
    }

    public String getCanceledOrders() {
        try {
            return StringOrder.getStringFromOrder(orderService.getCanceledOrders());
        } catch (NumberObjectZeroException e) {
            return String.valueOf(e);
        }
    }

    public String getDeletedOrders() {
        try {
            return StringOrder.getStringFromOrder(orderService.getDeletedOrders());
        } catch (NumberObjectZeroException e) {
            return String.valueOf(e);
        }
    }

    public String getMasterOrders(Master master) {
        try {
            return StringOrder.getStringFromOrder(orderService.getMasterOrders(master));
        } catch (NumberObjectZeroException e) {
            return String.valueOf(e);
        }
    }

    public String getOrderMasters(Order order) {
        try {
            return String.valueOf(orderService.getOrderMasters(order));
        } catch (NumberObjectZeroException e) {
            return String.valueOf(e);
        }
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