package com.senla.carservice.api.action;

import com.senla.carservice.api.printer.PrinterOrder;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Order;

import java.util.ArrayList;

public final class SortPriceOrderActionImpl implements Action {
    private static SortPriceOrderActionImpl instance;

    public SortPriceOrderActionImpl() {
    }

    public static SortPriceOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new SortPriceOrderActionImpl();
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
        sortArrayOrders = orderController.sortOrderByPrice(orders);
        PrinterOrder.printOrder(sortArrayOrders);
    }
}
