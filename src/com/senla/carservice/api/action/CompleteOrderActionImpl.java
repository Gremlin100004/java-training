package com.senla.carservice.api.action;

import com.senla.carservice.api.printer.PrinterOrder;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Order;

import java.util.ArrayList;
import java.util.Scanner;

public final class CompleteOrderActionImpl implements Action {
    private static CompleteOrderActionImpl instance;

    public CompleteOrderActionImpl() {
    }

    public static CompleteOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new CompleteOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        OrderController orderController = new OrderController();
        Scanner scanner = new Scanner(System.in);
        ArrayList<Order> orders = orderController.getOrders();
        if (orders.size() == 0) {
            System.out.println("There are no orders!");
            return;
        }
        PrinterOrder.printOrder(orders);
        System.out.println("0. Previous menu");
        String message;
        while (true) {
            System.out.println("Enter the index number of the order to change status:");
            while (!scanner.hasNextInt()) {
                System.out.println("You enter wrong value!!!");
                System.out.println("Try again:");
                scanner.next();
            }
            int index = scanner.nextInt();
            if (index == 0) {
                return;
            }
            if (index > orders.size() || index < 0) {
                System.out.println("There is no such order");
                continue;
            }
            message = orderController.completeOrder(orders.get(index - 1));
            System.out.println(message);
            if (message.equals(" - the order has been transferred to execution status")) {
                break;
            }
        }
    }
}