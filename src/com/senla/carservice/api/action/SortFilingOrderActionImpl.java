package com.senla.carservice.api.action;

import com.senla.carservice.api.printer.PrinterOrder;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Order;

import java.util.ArrayList;

public final class SortFilingOrderActionImpl implements Action {
    private static SortFilingOrderActionImpl instance;

    public SortFilingOrderActionImpl() {
    }

    public static SortFilingOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new SortFilingOrderActionImpl();
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
        sortArrayOrders = orderController.sortOrderByCreationTime(orders);
        PrinterOrder.printOrder(sortArrayOrders);
    }
}
