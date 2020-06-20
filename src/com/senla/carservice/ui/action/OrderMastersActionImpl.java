package com.senla.carservice.ui.action;

import com.senla.carservice.ui.printer.PrinterMaster;
import com.senla.carservice.ui.printer.PrinterOrder;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderMastersActionImpl implements Action {

    public OrderMastersActionImpl() {
    }

    @Override
    public void execute() {
        OrderController orderController = OrderController.getInstance();
        List<Order> orders = orderController.getOrders();
        Scanner scanner = new Scanner(System.in);
        int index;
        if (orders.size() == 0) {
            System.out.println("There are no orders.");
            return;
        }
        PrinterOrder.printOrder(orders);
        System.out.println("0. Previous menu");
        List<Master> masters = new ArrayList<>();
        while (masters.size() == 0) {
            System.out.println("Enter the index number of the order to view masters:");
            while (!scanner.hasNextInt()) {
                System.out.println("You enter wrong value!!!");
                System.out.println("Try again:");
                scanner.next();
            }
            index = scanner.nextInt();
            if (index == 0) {
                return;
            }
            if (index > orders.size() || index < 0) {
                System.out.println("There is no such order");
                continue;
            }
            masters = orderController.getOrderMasters(orders.get(index - 1));
            PrinterMaster.printMasters(masters);
        }
    }
}