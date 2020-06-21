package com.senla.carservice.ui.action;

import com.senla.carservice.controller.GarageController;
import com.senla.carservice.controller.MasterController;
import com.senla.carservice.controller.OrderController;

public class ExportAllActionImpl implements Action {
    public ExportAllActionImpl() {
    }

    @Override
    public void execute() {
        MasterController masterController = MasterController.getInstance();
        GarageController garageController = GarageController.getInstance();
        OrderController orderController = OrderController.getInstance();
        System.out.println(masterController.exportMasters());
        System.out.println(garageController.exportGarages());
        System.out.println(orderController.exportOrders());
    }
}
