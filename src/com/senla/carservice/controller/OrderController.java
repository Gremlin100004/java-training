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
import java.util.List;

public class OrderController {
    private static OrderController instance;
    private final OrderService orderService;
    private final MasterService masterService;
    private final PlaceService placeService;
    private final CarOfficeService carOfficeService;

    private OrderController() {
        orderService = OrderServiceImpl.getInstance();
        carOfficeService = CarOfficeServiceImpl.getInstance();
        masterService = MasterServiceImpl.getInstance();
        placeService = PlaceServiceImpl.getInstance();
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
            return e.getMessage();
        }
    }

    public String addOrderDeadlines(String stringExecutionStartTime, String stringLeadTime) {
        Date executionStartTime = DateUtil.getDatesFromString(stringExecutionStartTime);
        Date leadTime = DateUtil.getDatesFromString(stringLeadTime);
        try {
            orderService.addOrderDeadlines(executionStartTime, leadTime);
            return "deadline add to order successfully";
        } catch (NullDateException | DateException | NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String addOrderMasters(int index) {
        try {
            orderService.addOrderMasters(masterService.getMasters().get(index));
            return "masters add successfully";
        } catch (NumberObjectZeroException | EqualObjectsException e) {
            return e.getMessage();
        }

    }

    public String addOrderPlace(int index, String stringExecuteDate, String stringLeadDate) {
        Date executeDate = DateUtil.getDatesFromString(stringExecuteDate);
        Date leadDate = DateUtil.getDatesFromString(stringLeadDate);
        try {
            List<Order> orders = orderService.getOrderByPeriod(executeDate, leadDate);
            orderService.addOrderPlace(placeService.getFreePlaceByDate(executeDate, leadDate, orders).get(index));
            return "place add to order successfully";
        } catch (NumberObjectZeroException | NullDateException | DateException e) {
            return e.getMessage();
        }
    }

    public String addOrderPrice(BigDecimal price) {
        try {
            orderService.addOrderPrice(price);
            return "price add to order successfully";
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getOrders() {
        try {
            return StringOrder.getStringFromOrder(orderService.getOrders());
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String completeOrder(int index) {
        try {
            orderService.completeOrder(orderService.getOrders().get(index));
            return " - the order has been transferred to execution status";
        } catch (OrderStatusException | NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String closeOrder(int index) {
        try {
            orderService.closeOrder(orderService.getOrders().get(index));
            return " -the order has been completed.";
        } catch (OrderStatusException | NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String cancelOrder(int index) {
        try {
            orderService.cancelOrder(orderService.getOrders().get(index));
            return " -the order has been canceled.";
        } catch (OrderStatusException | NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String deleteOrder(int index) {
        try {
            orderService.deleteOrder(orderService.getOrders().get(index));
            return " -the order has been deleted.";
        } catch (OrderStatusException | NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String shiftLeadTime(int index, String stringStartTime, String stringLeadTime) {
        Date executionStartTime = DateUtil.getDatesFromString(stringStartTime);
        Date leadTime = DateUtil.getDatesFromString(stringLeadTime);
        try {
            orderService.shiftLeadTime(orderService.getOrders().get(index), executionStartTime, leadTime);
            return " -the order lead time has been changed.";
        } catch (OrderStatusException | DateException | NullDateException | NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getOrdersSortByFilingDate(){
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderCreationTime(orderService.getOrders()));
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getOrdersSortByExecutionDate(){
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByLeadTime(orderService.getOrders()));
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getOrdersSortByPlannedStartDate(){
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByStartTime(orderService.getOrders()));
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String sortOrderByCreationTime() {
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderCreationTime(orderService.getOrders()));
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String sortOrderByLeadTime() {
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByLeadTime(orderService.getOrders()));
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String sortOrderByStartTime() {
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByStartTime(orderService.getOrders()));
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String sortOrderByPrice() {
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByPrice(orderService.getOrders()));
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getExecuteOrder() {
        try {
            return StringOrder.getStringFromOrder(orderService.getCurrentRunningOrders());
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getOrdersByPeriod(String startPeriod, String endPeriod) {
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod);
        try {
            return StringOrder.getStringFromOrder(orderService.getOrderByPeriod(startPeriodDate, endPeriodDate));
        } catch (NullDateException | NumberObjectZeroException | DateException e) {
            return e.getMessage();
        }
    }

    public String getCompletedOrders() {
        try {
            return StringOrder.getStringFromOrder(orderService.getCompletedOrders());
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getCanceledOrders() {
        try {
            return StringOrder.getStringFromOrder(orderService.getCanceledOrders());
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getDeletedOrders() {
        try {
            return StringOrder.getStringFromOrder(orderService.getDeletedOrders());
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getMasterOrders(Master master) {
        try {
            return StringOrder.getStringFromOrder(orderService.getMasterOrders(master));
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getOrderMasters(Order order) {
        try {
            return String.valueOf(orderService.getOrderMasters(order));
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
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