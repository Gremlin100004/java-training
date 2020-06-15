package com.senla.carservice.api.action;

import com.senla.carservice.api.printer.PrinterOrder;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Order;

import java.util.ArrayList;

public final class ShowOrderActionImpl implements Action {
    private static ShowOrderActionImpl instance;

    public ShowOrderActionImpl() {
    }

    public static ShowOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new ShowOrderActionImpl();
        }
        return instance;
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
