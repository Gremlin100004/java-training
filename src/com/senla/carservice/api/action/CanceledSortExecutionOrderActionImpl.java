package com.senla.carservice.api.action;

import com.senla.carservice.api.printer.PrinterOrder;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Order;

import java.util.ArrayList;
import java.util.Scanner;

public final class CanceledSortExecutionOrderActionImpl implements Action {
    private static CanceledSortExecutionOrderActionImpl instance;

    public CanceledSortExecutionOrderActionImpl() {
    }

    public static CanceledSortExecutionOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new CanceledSortExecutionOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        String beginningPeriodTime;
        String endPeriodTime;
        OrderController orderController = new OrderController();
        Scanner scanner = new Scanner(System.in);
        ArrayList<Order> testOrders = orderController.getOrders();
        ArrayList<Order> orders;
        if (testOrders.size() == 0){
            System.out.println("There are no orders in service!");
            return;
        }
        while (true) {
            System.out.println("Enter the start of period in format \"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"");
            beginningPeriodTime = scanner.nextLine();
            System.out.println("Enter the end of period in format \"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"");
            endPeriodTime = scanner.nextLine();
            orders = orderController.getOrdersByPeriod(beginningPeriodTime, endPeriodTime);
            System.out.println(String.format("Period of time: %s - %s", beginningPeriodTime, endPeriodTime));
            if (orders.size() == 0){
                System.out.println("There are no orders for this period of time!");
                return;
            }
            orders = orderController.getCanceledOrders(orders);
            if (orders.size() == 0){
                System.out.println("There are no canceled orders for this period of time!");
                return;
            }
            orders = orderController.sortOrderByStartTime(orders);
            PrinterOrder.printOrder(orders);
        }
    }
}