package com.senla.carservice.ui.action;

import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Order;
import com.senla.carservice.ui.printer.PrinterOrder;
import com.senla.carservice.ui.util.OrderUtil;

import java.util.ArrayList;
import java.util.List;

public class DeletedSortFilingOrderActionImpl implements Action {

    public DeletedSortFilingOrderActionImpl() {
    }

    @Override
    public void execute() {
        List<Order> orders = new ArrayList<>();
        OrderController orderController = OrderController.getInstance();
        while (orders.isEmpty()) {
            orders = OrderUtil.getSortPeriodOrders();
            if (orders.isEmpty()) {
                return;
            }
            orders = orderController.getDeletedOrders(orders);
            if (orders.isEmpty()) {
                System.out.println("There are no deleted orders for this period of time!");
                return;
            }
            orders = orderController.sortOrderByCreationTime(orders);
            PrinterOrder.printOrder(orders);
        }
    }
}