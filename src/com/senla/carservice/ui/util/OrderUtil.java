package com.senla.carservice.ui.util;

import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderUtil {
    public static List<Order> getSortPeriodOrders(){
        String beginningPeriodTime;
        String endPeriodTime;
        OrderController orderController = OrderController.getInstance();
        List<Order> testOrders = orderController.getOrders();
        List<Order> orders = new ArrayList<>();
        if (testOrders.isEmpty()) {
            System.out.println("There are no orders in service!");
            return orders;
        }
        beginningPeriodTime = ScannerUtil.getStringDateUser(
                "Enter the start of period in format \"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"");
        endPeriodTime = ScannerUtil.getStringDateUser(
                "Enter the end of period in format \"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"");
        orders = orderController.getOrdersByPeriod(beginningPeriodTime, endPeriodTime);
        System.out.println(String.format("Period of time: %s - %s", beginningPeriodTime, endPeriodTime));
        if (orders.isEmpty()) {
            System.out.println("There are no orders for this period of time!");
        }
        return orders;
    }
}
