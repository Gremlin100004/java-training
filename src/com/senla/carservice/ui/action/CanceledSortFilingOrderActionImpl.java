package com.senla.carservice.ui.action;

import com.senla.carservice.ui.printer.PrinterOrder;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Order;
import com.senla.carservice.ui.util.OrderUtil;
import com.senla.carservice.ui.util.ScannerUtil;

import java.util.ArrayList;
import java.util.List;

public class CanceledSortFilingOrderActionImpl implements Action {

    public CanceledSortFilingOrderActionImpl() {
    }

    @Override
    public void execute() {
        List<Order> orders = new ArrayList<>();
        OrderController orderController = OrderController.getInstance();
        while (orders.isEmpty()) {
            orders = OrderUtil.getSortPeriodOrders();
            if (orders.isEmpty()){
                return;
            }
            orders = orderController.getCanceledOrders(orders);
            if (orders.isEmpty()) {
                System.out.println("There are no canceled orders for this period of time!");
                return;
            }
            orders = orderController.sortOrderByCreationTime(orders);
            PrinterOrder.printOrder(orders);
        }
    }
}