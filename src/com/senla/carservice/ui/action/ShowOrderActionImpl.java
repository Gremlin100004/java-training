package com.senla.carservice.ui.action;

import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Order;
import com.senla.carservice.ui.printer.PrinterOrder;

import java.util.List;

public class ShowOrderActionImpl implements Action {

    public ShowOrderActionImpl() {
    }

    @Override
    public void execute() {
        OrderController orderController = OrderController.getInstance();
        List<Order> orders = orderController.getOrders();
        if (orders.isEmpty()) {
            System.out.println("There are no orders.");
            return;
        }
        PrinterOrder.printOrder(orders);
    }
}