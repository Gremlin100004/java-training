package com.senla.carservice.api.action;

import com.senla.carservice.TestData;
import com.senla.carservice.api.printer.PrinterOrder;
import com.senla.carservice.controller.GarageController;
import com.senla.carservice.controller.MasterController;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.dto.OrderDto;

import java.util.ArrayList;

public final class DemoActionImpl implements Action {
    private static DemoActionImpl instance;

    public DemoActionImpl() {
    }

    public static DemoActionImpl getInstance() {
        if (instance == null) {
            instance = new DemoActionImpl();
        }
        return instance;
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

        Garage[] garages = garageController.getArrayGarages();
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
        for (int i = 0; i < testData.getArrayAutomaker().length; i++) {
            ArrayList<Master> mastersOrder = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                mastersOrder.add(masterController.getMasters()[indexMaster]);
                indexMaster++;
                if (indexMaster == masterController.getMasters().length - 1) {
                    indexMaster = 0;
                }
            }
            OrderDto orderDto = new OrderDto(testData.getArrayExecutionStartTime()[i], testData.getArrayLeadTime()[i],
                    mastersOrder, garages[indexGarage], garages[indexGarage].getPlaces()[indexPlace],
                    testData.getArrayAutomaker()[i], testData.getArrayModel()[i],
                    testData.getArrayRegistrationNumber()[i], testData.getArrayPrice()[i]);

            orderController.addOrder(orderDto);
            indexPlace++;
            if (indexPlace == 3) {
                indexPlace = 0;
                indexGarage++;
            }
        }
        Order[] orders = orderController.getOrders();
        PrinterOrder.printOrder(orders);
    }
}
