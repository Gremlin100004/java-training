package com.senla.carservice.api.action;

import com.senla.carservice.api.printer.PrinterMaster;
import com.senla.carservice.api.printer.PrinterOrder;
import com.senla.carservice.controller.MasterController;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;

import java.util.ArrayList;
import java.util.Scanner;

public final class DeleteOrderActionImpl implements Action {
    private static DeleteOrderActionImpl instance;

    public DeleteOrderActionImpl() {
    }

    public static DeleteOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new DeleteOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        OrderController orderController = new OrderController();
        Scanner scanner = new Scanner(System.in);
        ArrayList<Order> orders = orderController.getOrders();
        if (orders.size() == 0){
            System.out.println("There are no orders to delete!");
            return;
        }
        PrinterOrder.printOrder(orders);
        System.out.println("0. Previous menu");
        String message;
        while (true){
            System.out.println("Enter the index number of the order to delete:");
            while (!scanner.hasNextInt()) {
                System.out.println("You enter wrong value!!!");
                System.out.println("Try again:");
                scanner.next();
            }
            int index = scanner.nextInt();
            if (index == 0){
                return;
            }
            if (index > orders.size() ||index < 0){
                System.out.println("There is no such order");
                continue;
            }
            message = orderController.deleteOrder(orders.get(index-1));
            System.out.println(message);
            if (message.equals(" -the order has been deleted.")){
                break;
            }
        }
    }
}
