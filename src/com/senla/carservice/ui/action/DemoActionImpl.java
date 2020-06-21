package com.senla.carservice.ui.action;

import com.senla.carservice.controller.GarageController;
import com.senla.carservice.controller.MasterController;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.ui.printer.PrinterOrder;
import com.senla.carservice.ui.util.TestData;

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

        addMaster(delimiter, masterController);
        addGarage(delimiter, garageController);
        addPlaceGarage(delimiter, garageController);
        System.out.println("Add new orders to car service.");
        addOrder(masterController, orderController, garageController

        );
    }

    private void addMaster(String delimiter, MasterController masterController){
        System.out.println("Add master:");
        for (String masterName : TestData.getArrayMasterNames()) {
            System.out.println(String.format(" -master \"%s\" has been added to service.",
                    masterController.addMaster(masterName)));
        }
        System.out.println(delimiter);
    }

    private void addGarage(String delimiter, GarageController garageController){
        System.out.println("Add garage to service:");
        for (String garageName : TestData.getArrayGarageNames()) {
            System.out.println(String.format(" -garage \"%s\" has been added to service",
                    garageController.addGarage(garageName)));
        }
        System.out.println(delimiter);
    }
    private  void addPlaceGarage(String delimiter, GarageController garageController){
        List<Garage> garages = garageController.getArrayGarages();
        System.out.println("Add places in garages.");
        for (Garage garage : garages) {
            for (int j = 0; j < 4; j++) {
                System.out.println(String.format("Add place in garage \"%s\"",
                        garageController.addGaragePlace(garage)));
            }
        }
        System.out.println(delimiter);
    }
    private void addOrder(MasterController masterController,
                          OrderController orderController, GarageController garageController){
        int indexMaster = 0;
        int indexGarage = 0;
        int indexPlace = 0;
        String message;
        List<Garage> garages = garageController.getArrayGarages();
        for (int i = 0; i < TestData.getArrayAutomaker().size(); i++) {
            ArrayList<Master> mastersOrder = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                mastersOrder.add(masterController.getMasters().get(indexMaster));
                indexMaster++;
                if (indexMaster == masterController.getMasters().size() - 1) {
                    indexMaster = 0;
                }
            }
            message = orderController.addOrder(TestData.getArrayAutomaker().get(i),
                    TestData.getArrayModel().get(i), TestData.getArrayRegistrationNumber().get(i));
            System.out.println(message);
            message = orderController.addOrderDeadlines(TestData.getArrayExecutionStartTime().get(i),
                    TestData.getArrayLeadTime().get(i));
            System.out.println(message);
            message = orderController.addOrderMasters(mastersOrder);
            System.out.println(message);
            message = orderController.addOrderPlaces(garages.get(indexGarage),
                    garages.get(indexGarage).getPlaces().get(indexPlace));
            System.out.println(message);
            message = orderController.addOrderPrice(TestData.getArrayPrice().get(i));
            System.out.println(message);
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