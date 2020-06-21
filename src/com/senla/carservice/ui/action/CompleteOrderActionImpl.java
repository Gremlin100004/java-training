package com.senla.carservice.ui.action;

import com.senla.carservice.ui.printer.PrinterOrder;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Order;
import com.senla.carservice.ui.util.ScannerUtil;

import java.util.List;

public class CompleteOrderActionImpl implements Action {

    public CompleteOrderActionImpl() {
    }

    @Override
    public void execute() {
        OrderController orderController = OrderController.getInstance();
        List<Order> orders = orderController.getOrders();
        if (orders.isEmpty()) {
            System.out.println("There are no orders!");
            return;
        }
        PrinterOrder.printOrder(orders);
        System.out.println("0. Previous menu");
        String message = "";
        int index;
        do {
            index = ScannerUtil.getIntUser("Enter the index number of the order to change status:");
            if (index == 0) {
                return;
            }
            if (index > orders.size() || index < 0) {
                System.out.println("There is no such order");
                continue;
            }
            message = orderController.completeOrder(orders.get(index - 1));
            System.out.println(message);
        } while (!message.equals(" - the order has been transferred to execution status"));
    }
}