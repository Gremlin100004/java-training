package com.senla.carservice.api.action;

import com.senla.carservice.api.printer.PrinterMaster;
import com.senla.carservice.api.printer.PrinterOrder;
import com.senla.carservice.controller.MasterController;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;

import java.util.ArrayList;
import java.util.Scanner;

public final class OrderMastersActionImpl implements Action {
    private static OrderMastersActionImpl instance;

    public OrderMastersActionImpl() {
    }

    public static OrderMastersActionImpl getInstance() {
        if (instance == null) {
            instance = new OrderMastersActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        MasterController masterController = new MasterController();
        OrderController orderController = new OrderController();
        ArrayList<Order> orders = orderController.getOrders();
        Scanner scanner = new Scanner(System.in);
        int index;
        if (orders.size() == 0) {
            System.out.println("There are no orders.");
            return;
        }
        PrinterOrder.printOrder(orders);
        System.out.println("0. Previous menu");
        ArrayList<Master> masters;
        while (true) {
            System.out.println("Enter the index number of the order to view masters:");
            while (!scanner.hasNextInt()) {
                System.out.println("You enter wrong value!!!");
                System.out.println("Try again:");
                scanner.next();
            }
            index = scanner.nextInt();
            if (index == 0){
                return;
            }
            if (index > orders.size() || index < 0) {
                System.out.println("There is no such order");
                continue;
            }
            masters = orders.get(index - 1).getMasters();
            for (Master master : masters) {
                System.out.println(String.format(" - %s;", master.getName()));
            }
            break;
        }
    }
}