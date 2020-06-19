package com.senla.carservice.api.action;

import com.senla.carservice.api.moce.TestData;
import com.senla.carservice.api.printer.PrinterOrder;
import com.senla.carservice.controller.GarageController;
import com.senla.carservice.controller.MasterController;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.dto.OrderDto;

import java.util.ArrayList;

public class DemoActionImpl implements Action {

    public DemoActionImpl() {
    }

    @Override
    public void execute() {
        GarageController garageController = new GarageController();
        MasterController masterController = new MasterController();
        OrderController orderController = new OrderController();
        TestData testData = new TestData();
        String message;
        String delimiter = "***********************************************************************";
        System.out.println(delimiter);

        System.out.println("Add master:");
        for (String masterName : testData.getArrayMasterNames()) {
            message = masterController.addMaster(masterName);
            System.out.println(String.format(" -master \"%s\" has been added to service.", message));
        }
        System.out.println(delimiter);

        System.out.println("Add garage to service:");
        for (String garageName : testData.getArrayGarageNames()) {
            message = garageController.addGarage(garageName);
            System.out.println(String.format(" -garage \"%s\" has been added to service", message));
        }
        System.out.println(delimiter);

        ArrayList<Garage> garages = garageController.getArrayGarages();
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
        for (int i = 0; i < testData.getArrayAutomaker().size(); i++) {
            ArrayList<Master> mastersOrder = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                mastersOrder.add(masterController.getMasters().get(indexMaster));
                indexMaster++;
                if (indexMaster == masterController.getMasters().size() - 1) {
                    indexMaster = 0;
                }
            }
            OrderDto orderDto = new OrderDto(testData.getArrayExecutionStartTime().get(i), testData.getArrayLeadTime().get(i),
                    mastersOrder, garages.get(indexGarage), garages.get(indexGarage).getPlaces().get(indexPlace),
                    testData.getArrayAutomaker().get(i), testData.getArrayModel().get(i),
                    testData.getArrayRegistrationNumber().get(i), testData.getArrayPrice().get(i));

            orderController.addOrder(orderDto);
            indexPlace++;
            if (indexPlace == 3) {
                indexPlace = 0;
                indexGarage++;
            }
        }
        ArrayList<Order> orders = orderController.getOrders();
        PrinterOrder.printOrder(orders);
    }
}