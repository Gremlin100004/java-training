package com.senla.carservice.api.action;

import com.senla.carservice.api.printer.PrinterOrder;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Order;

import java.util.ArrayList;

public class ExecutedSortPriceOrderActionImpl implements Action {

    public ExecutedSortPriceOrderActionImpl() {
    }

    @Override
    public void execute() {
        OrderController orderController = new OrderController();
        ArrayList<Order> executedOrders = orderController.getExecuteOrder();
        executedOrders = orderController.sortOrderByPrice(executedOrders);
        if (executedOrders.size() == 0) {
            System.out.println("There are no orders!");
            return;
        }
        PrinterOrder.printOrder(executedOrders);
    }
}