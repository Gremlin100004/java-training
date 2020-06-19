package com.senla.carservice.api.action;

import com.senla.carservice.api.printer.PrinterOrder;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Order;

import java.util.ArrayList;

public final class SortExecutionOrderActionImpl implements Action {
    private static SortExecutionOrderActionImpl instance;

    public SortExecutionOrderActionImpl() {
    }

    public static SortExecutionOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new SortExecutionOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        OrderController orderController = new OrderController();
        ArrayList<Order> sortArrayOrders;
        ArrayList<Order> orders = orderController.getOrders();
        if (orders.size() == 0) {
            System.out.println("There are no orders.");
            return;
        }
        sortArrayOrders = orderController.sortOrderByLeadTime(orders);
        PrinterOrder.printOrder(sortArrayOrders);
    }
}