package com.senla.carservice.ui.action;

import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Order;
import com.senla.carservice.ui.printer.PrinterOrder;

import java.util.List;

public class SortPlannedOrderActionImpl implements Action {

    public SortPlannedOrderActionImpl() {
    }

    @Override
    public void execute() {
        OrderController orderController = OrderController.getInstance();
        List<Order> sortArrayOrders;
        List<Order> orders = orderController.getOrders();
        if (orders.isEmpty()) {
            System.out.println("There are no orders.");
            return;
        }
        sortArrayOrders = orderController.sortOrderByStartTime(orders);
        PrinterOrder.printOrder(sortArrayOrders);
    }
}