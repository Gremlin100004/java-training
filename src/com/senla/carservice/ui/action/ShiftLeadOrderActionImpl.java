package com.senla.carservice.ui.action;

import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Order;
import com.senla.carservice.ui.printer.PrinterOrder;
import com.senla.carservice.ui.util.ScannerUtil;

import java.util.List;

public class ShiftLeadOrderActionImpl implements Action {

    public ShiftLeadOrderActionImpl() {
    }

    @Override
    public void execute() {
        String executionStartTime;
        String leadTime;
        String message = "";
        OrderController orderController = OrderController.getInstance();
        List<Order> orders = orderController.getOrders();
        if (orders.isEmpty()) {
            System.out.println("There are no orders!");
            return;
        }
        PrinterOrder.printOrder(orders);
        int index = 0;
        while (index == 0) {
            index = ScannerUtil.getIntUser("Enter the index number of the order to shift time:");
            if (index > orders.size() || index < 1) {
                System.out.println("There is no such master");
                continue;
            }
            break;
        }
        while (!message.equals(" -the order lead time has been changed.")) {
            executionStartTime = ScannerUtil.getStringDateUser(
                    "Enter the planing time start to execute the order in format " +
                            "\"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"");
            leadTime = ScannerUtil.getStringDateUser(
                    "Enter the lead time the order in format \"dd.MM.yyyy hh:mm\"," +
                            " example:\"10.10.2010 10:00\"");
            message = orderController.shiftLeadTime(orders.get(index - 1), executionStartTime, leadTime);
            System.out.println(message);
        }
    }
}