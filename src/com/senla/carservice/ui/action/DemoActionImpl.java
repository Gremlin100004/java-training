package com.senla.carservice.ui.action;

import com.senla.carservice.ui.util.TestData;
import com.senla.carservice.ui.printer.PrinterOrder;
import com.senla.carservice.controller.GarageController;
import com.senla.carservice.controller.MasterController;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.dto.OrderDto;

import java.util.ArrayList;
import java.util.List;

public class DemoActionImpl implements Action {

    public DemoActionImpl() {
    }

    @Override
    public void execute() {
        GarageController garageController = GarageController.getInstance();
        MasterController masterController = MasterController.getInstance();
        OrderController orderController = OrderController.getInstance();
        String message;
        String delimiter = "***********************************************************************";
        System.out.println(delimiter);

        System.out.println("Add master:");
        for (String masterName : TestData.getArrayMasterNames()) {
            message = masterController.addMaster(masterName);
            System.out.println(String.format(" -master \"%s\" has been added to service.", message));
        }
        System.out.println(delimiter);

        System.out.println("Add garage to service:");
        for (String garageName : TestData.getArrayGarageNames()) {
            message = garageController.addGarage(garageName);
            System.out.println(String.format(" -garage \"%s\" has been added to service", message));
        }
        System.out.println(delimiter);

        List<Garage> garages = garageController.getArrayGarages();
        System.out.println("Add places in garages.");
        for (Garage garage : garages) {
            for (int j = 0; j < 4; j++) {
                message = garageController.addGaragePlace(garage);
                System.out.println(String.format("Add place in garage \"%s\"", message));
            }
        }
        System.out.println(delimiter);

        System.out.println("Add new orders to car service.");
        int indexMaster = 0;
        int indexGarage = 0;
        int indexPlace = 0;
        for (int i = 0; i < TestData.getArrayAutomaker().size(); i++) {
            ArrayList<Master> mastersOrder = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                mastersOrder.add(masterController.getMasters().get(indexMaster));
                indexMaster++;
                if (indexMaster == masterController.getMasters().size() - 1) {
                    indexMaster = 0;
                }
            }
            OrderDto orderDto = new OrderDto(TestData.getArrayExecutionStartTime().get(i), TestData.getArrayLeadTime().get(i),
                    mastersOrder, garages.get(indexGarage), garages.get(indexGarage).getPlaces().get(indexPlace),
                    TestData.getArrayAutomaker().get(i), TestData.getArrayModel().get(i),
                    TestData.getArrayRegistrationNumber().get(i), TestData.getArrayPrice().get(i));

            orderController.addOrder(orderDto);
            indexPlace++;
            if (indexPlace == 3) {
                indexPlace = 0;
                indexGarage++;
            }
        }
        List<Order> orders = orderController.getOrders();
        PrinterOrder.printOrder(orders);
    }
}