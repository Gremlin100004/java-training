package com.senla.carservice.api.action;

import com.senla.carservice.api.printer.PrinterOrder;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Order;

import java.util.ArrayList;
import java.util.Scanner;

public class ShiftLeadOrderActionImpl implements Action {

    public ShiftLeadOrderActionImpl() {
    }

    @Override
    public void execute() {
        String executionStartTime;
        String leadTime;
        String message = "";
        OrderController orderController = new OrderController();
        Scanner scanner = new Scanner(System.in);
        ArrayList<Order> orders = orderController.getOrders();
        if (orders.size() == 0) {
            System.out.println("There are no orders!");
            return;
        }
        PrinterOrder.printOrder(orders);
        System.out.println("Enter the index number of the order to shift time:");
        int index;
        while (true) {
            while (!scanner.hasNextInt()) {
                System.out.println("You enter wrong value!!!");
                System.out.println("Try again:");
                scanner.next();
            }
            index = scanner.nextInt();
            if (index > orders.size() || index < 1) {
                System.out.println("There is no such master");
                continue;
            }
            break;
        }
        while (!message.equals(" -the order lead time has been changed.")) {
            System.out.println("Enter the planing time start to execute the order in format \"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"");
            executionStartTime = scanner.nextLine();
            System.out.println("Enter the lead time the order in format \"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"");
            leadTime = scanner.nextLine();
            message = orderController.shiftLeadTime(orders.get(index - 1), executionStartTime, leadTime);
            System.out.println(message);
        }
    }
}