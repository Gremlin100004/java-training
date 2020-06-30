package com.senla.carservice.controller;

import com.senla.carservice.domain.Order;
import com.senla.carservice.exception.*;
import com.senla.carservice.service.*;
import com.senla.carservice.string.StringMaster;
import com.senla.carservice.string.StringOrder;
import com.senla.carservice.util.DateUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderController {
    private static OrderController instance;
    private final OrderService orderService;
    private final MasterService masterService;
    private final PlaceService placeService;


    private OrderController() {
        orderService = OrderServiceImpl.getInstance();
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
        Date executionStartTime = DateUtil.getDatesFromString(stringExecutionStartTime, true);
        Date leadTime = DateUtil.getDatesFromString(stringLeadTime, true);
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
        } catch (IndexOutOfBoundsException e){
         return "There is no such master";
        }
    }

    public String addOrderPlace(int index, String stringExecuteDate, String stringLeadDate) {
        Date executeDate = DateUtil.getDatesFromString(stringExecuteDate, true);
        Date leadDate = DateUtil.getDatesFromString(stringLeadDate, true);
        try {
            List<Order> orders = orderService.getOrderByPeriod(executeDate, leadDate);
            orderService.addOrderPlace(placeService.getFreePlaceByDate(executeDate, leadDate, orders).get(index));
            return "place add to order successfully";
        } catch (NumberObjectZeroException | NullDateException | DateException e) {
            return e.getMessage();
        } catch (IndexOutOfBoundsException e){
            return "There is no such place!";
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
        } catch (IndexOutOfBoundsException e){
            return "There are no such order";
        }
    }

    public String closeOrder(int index) {
        try {
            orderService.closeOrder(orderService.getOrders().get(index));
            return " -the order has been completed.";
        } catch (OrderStatusException | NumberObjectZeroException e) {
            return e.getMessage();
        } catch (IndexOutOfBoundsException e){
            return "There are no such order";
        }
    }

    public String cancelOrder(int index) {
        try {
            orderService.cancelOrder(orderService.getOrders().get(index));
            return " -the order has been canceled.";
        } catch (OrderStatusException | NumberObjectZeroException e) {
            return e.getMessage();
        } catch (IndexOutOfBoundsException e){
            return "There are no such order";
        }
    }

    public String deleteOrder(int index) {
        try {
            orderService.deleteOrder(orderService.getOrders().get(index));
            return " -the order has been deleted.";
        } catch (OrderStatusException | NumberObjectZeroException | BusinessException e) {
            return e.getMessage();
        } catch (IndexOutOfBoundsException e){
            return "There are no such order";
        }
    }

    public String shiftLeadTime(int index, String stringStartTime, String stringLeadTime) {
        Date executionStartTime = DateUtil.getDatesFromString(stringStartTime, true);
        Date leadTime = DateUtil.getDatesFromString(stringLeadTime, true);
        try {
            orderService.shiftLeadTime(orderService.getOrders().get(index), executionStartTime, leadTime);
            return " -the order lead time has been changed.";
        } catch (OrderStatusException | DateException | NullDateException |
                NumberObjectZeroException | BusinessException e) {
            return e.getMessage();
        } catch (IndexOutOfBoundsException e){
            return "There are no such order";
        }
    }

    public String getOrdersSortByFilingDate(){
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByCreationTime(orderService.getOrders()));
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

    public String getOrdersSortByPrice(){
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByPrice(orderService.getOrders()));
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getExecuteOrderFilingDate() {
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByCreationTime
                    (orderService.getCurrentRunningOrders()));
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getExecuteOrderExecutionDate() {
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByLeadTime
                    (orderService.getCurrentRunningOrders()));
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getCompletedOrdersFilingDate(String startPeriod, String endPeriod) {
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByCreationTime
                    (orderService.getCompletedOrders(startPeriodDate, endPeriodDate)));
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getCompletedOrdersExecutionDate(String startPeriod, String endPeriod) {
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByStartTime
                    (orderService.getCompletedOrders(startPeriodDate, endPeriodDate)));
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getCompletedOrdersPrice(String startPeriod, String endPeriod) {
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByPrice
                    (orderService.getCompletedOrders(startPeriodDate, endPeriodDate)));
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getCanceledOrdersFilingDate(String startPeriod, String endPeriod) {
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByCreationTime
                    (orderService.getCanceledOrders(startPeriodDate, endPeriodDate)));
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getCanceledOrdersExecutionDate(String startPeriod, String endPeriod) {
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByStartTime
                    (orderService.getCanceledOrders(startPeriodDate, endPeriodDate)));
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getCanceledOrdersPrice(String startPeriod, String endPeriod) {
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByPrice
                    (orderService.getCanceledOrders(startPeriodDate, endPeriodDate)));
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getDeletedOrdersFilingDate(String startPeriod, String endPeriod) {
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByCreationTime
                    (orderService.getDeletedOrders(startPeriodDate, endPeriodDate)));
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getDeletedOrdersExecutionDate(String startPeriod, String endPeriod) {
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByStartTime
                    (orderService.getDeletedOrders(startPeriodDate, endPeriodDate)));
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getDeletedOrdersPrice(String startPeriod, String endPeriod) {
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByPrice
                    (orderService.getDeletedOrders(startPeriodDate, endPeriodDate)));
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getMasterOrders(int index) {
        try {
            return StringOrder.getStringFromOrder(orderService.getMasterOrders(masterService.getMasters().get(index)));
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        } catch (IndexOutOfBoundsException e){
            return "There are no such master";
        }
    }

    public String getOrderMasters(int index) {
        try {
            return StringMaster.getStringFromMasters(orderService.getOrderMasters(orderService.getOrders().get(index)));
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        } catch (IndexOutOfBoundsException e){
            return "There are no such order";
        }
    }

    public String exportOrders() {
        try {
            orderService.exportOrder();
            return "Orders have been export successfully!";
        } catch (NumberObjectZeroException | ExportException e){
            return e.getMessage();
        }
    }

    public String importOrders() {
        try {
            return orderService.importOrder();
        } catch (NumberObjectZeroException e){
            return e.getMessage();
        }
    }

    public String serializeOrder() {
        try {
            orderService.serializeOrder();
            return "Orders have been serialize successfully!";
        } catch (SerializeException e){
            return e.getMessage();
        }
    }

    public String deserializeOrder() {
        try {
            orderService.deserializeOrder();
            return "Orders have been deserialize successfully!";
        } catch (SerializeException e){
            return e.getMessage();
        }
    }
}