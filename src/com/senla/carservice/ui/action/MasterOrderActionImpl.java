package com.senla.carservice.ui.action;

import com.senla.carservice.controller.MasterController;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.ui.printer.PrinterMaster;
import com.senla.carservice.ui.printer.PrinterOrder;
import com.senla.carservice.ui.util.ScannerUtil;

import java.util.ArrayList;
import java.util.List;

public class MasterOrderActionImpl implements Action {

    public MasterOrderActionImpl() {
    }

    @Override
    public void execute() {
        MasterController masterController = MasterController.getInstance();
        OrderController orderController = OrderController.getInstance();
        List<Master> masters = masterController.getMasters();
        int index;
        if (masters.isEmpty()) {
            System.out.println("There are no masters.");
            return;
        }
        PrinterMaster.printMasters(masters);
        System.out.println("0. Previous menu");
        List<Order> orders = new ArrayList<>();
        while (orders.isEmpty()) {
            index = ScannerUtil.getIntUser("Enter the index number of the master to view orders:");
            if (index == 0) {
                return;
            }
            if (index > masters.size() || index < 0) {
                System.out.println("There is no such master");
                continue;
            }
            orders = orderController.getMasterOrders(masters.get(index - 1));
            if (orders.isEmpty()) {
                System.out.println("Such master has no orders!");
                continue;
            }
            PrinterOrder.printOrder(orders);
            break;
        }
    }
}