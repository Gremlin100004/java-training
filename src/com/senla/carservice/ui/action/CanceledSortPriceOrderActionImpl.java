package com.senla.carservice.ui.action;

import com.senla.carservice.ui.printer.PrinterOrder;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Order;
import com.senla.carservice.ui.util.OrderUtil;

import java.util.ArrayList;
import java.util.List;

public class CanceledSortPriceOrderActionImpl implements Action {

    public CanceledSortPriceOrderActionImpl() {
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
            orders = orderController.sortOrderByPrice(orders);
            PrinterOrder.printOrder(orders);
        }
    }
}