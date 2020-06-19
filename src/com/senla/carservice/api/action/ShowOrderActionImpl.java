package com.senla.carservice.api.action;

import com.senla.carservice.api.printer.PrinterOrder;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Order;

import java.util.ArrayList;

public class ShowOrderActionImpl implements Action {

    public ShowOrderActionImpl() {
    }

    @Override
    public void execute() {
        OrderController orderController = new OrderController();
        ArrayList<Order> orders = orderController.getOrders();
        if (orders.size() == 0) {
            System.out.println("There are no orders.");
            return;
        }
        PrinterOrder.printOrder(orders);
    }
}