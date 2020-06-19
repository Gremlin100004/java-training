package com.senla.carservice.api.action;

import com.senla.carservice.api.printer.PrinterOrder;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Order;

import java.util.ArrayList;

public final class SortPlannedOrderActionImpl implements Action {
    private static SortPlannedOrderActionImpl instance;

    public SortPlannedOrderActionImpl() {
    }

    public static SortPlannedOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new SortPlannedOrderActionImpl();
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
        sortArrayOrders = orderController.sortOrderByStartTime(orders);
        PrinterOrder.printOrder(sortArrayOrders);
    }
}