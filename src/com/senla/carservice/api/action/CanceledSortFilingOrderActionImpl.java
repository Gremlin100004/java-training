package com.senla.carservice.api.action;

import com.senla.carservice.api.printer.PrinterOrder;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Order;

import java.util.ArrayList;
import java.util.Scanner;

public class CanceledSortFilingOrderActionImpl implements Action {

    public CanceledSortFilingOrderActionImpl() {
    }

    @Override
    public void execute() {
        String beginningPeriodTime;
        String endPeriodTime;
        OrderController orderController = new OrderController();
        Scanner scanner = new Scanner(System.in);
        ArrayList<Order> testOrders = orderController.getOrders();
        ArrayList<Order> orders = new ArrayList<>();
        if (testOrders.size() == 0) {
            System.out.println("There are no orders in service!");
            return;
        }
        while (orders.size() == 0) {
            System.out.println("Enter the start of period in format \"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"");
            beginningPeriodTime = scanner.nextLine();
            System.out.println("Enter the end of period in format \"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"");
            endPeriodTime = scanner.nextLine();
            orders = orderController.getOrdersByPeriod(beginningPeriodTime, endPeriodTime);
            System.out.println(String.format("Period of time: %s - %s", beginningPeriodTime, endPeriodTime));
            if (orders.size() == 0) {
                System.out.println("There are no orders for this period of time!");
                return;
            }
            orders = orderController.getCanceledOrders(orders);
            if (orders.size() == 0) {
                System.out.println("There are no canceled orders for this period of time!");
                return;
            }
            orders = orderController.sortOrderByCreationTime(orders);
            PrinterOrder.printOrder(orders);
        }
    }
}