package com.senla.carservice.ui.action;

import com.senla.carservice.ui.printer.PrinterMaster;
import com.senla.carservice.controller.CarOfficeController;
import com.senla.carservice.controller.GarageController;
import com.senla.carservice.controller.MasterController;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Place;
import com.senla.carservice.ui.util.ScannerUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddOrderActionImpl implements Action {

    public AddOrderActionImpl() {
    }

    @Override
    public void execute() {
        OrderController orderController = OrderController.getInstance();
        MasterController masterController = MasterController.getInstance();
        GarageController garageController = GarageController.getInstance();
        CarOfficeController carOfficeController = CarOfficeController.getInstance();
        BigDecimal price;
        if (masterController.getMasters().isEmpty()) {
            System.out.println("There are no masters!");
            return;
        }
        if (!isPlace(garageController.getArrayGarages())) {
            System.out.println("There are no Places!");
            return;
        }
        String automaker = ScannerUtil.getStringUser("Enter the automaker of car");
        String model = ScannerUtil.getStringUser("Enter the model of car");
        String registrationNumber = ScannerUtil.getStringUser("Enter the registration number of car, example: 1111 AB-7");
        String message = orderController.addOrder(automaker, model, registrationNumber);
        System.out.println(message);
        List<String> deadline = addOrderDeadline();
        String executionStartTime = deadline.get(0);
        String leadTime = deadline.get(1);
        addMastersOrder(executionStartTime, leadTime);
        List<Garage> freeGarages = carOfficeController.getGaragesFreePlace(executionStartTime, leadTime);
        System.out.println("Garage with free places:");
        for (int i = 0; i < freeGarages.size(); i++) {
            System.out.println(String.format("%s. %s", i + 1, freeGarages.get(i).getName()));
        }
        Garage garage = addGarageOrder(freeGarages, garageController);
        Place place = garageController.getFreePlaceGarage(garage).get(0);
        message = orderController.addOrderPlaces(garage, place);
        System.out.println(message);
        price = ScannerUtil.getBigDecimalUser("Enter the price");
        message = orderController.addOrderPrice(price);
        System.out.println(message);
    }

    private List<String> addOrderDeadline() {
        String message = "";
        String leadTime = "";
        String executionStartTime = "";
        OrderController orderController = OrderController.getInstance();
        while (!message.equals("deadline add to order successfully")) {
            System.out.println(message);
            executionStartTime = ScannerUtil.getStringDateUser(
                    "Enter the planing time start to execute the order in " +
                            "format \"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"");
            leadTime = ScannerUtil.getStringDateUser(
                    "Enter the lead time the order in format " +
                            "\"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"");
            message = orderController.addOrderDeadlines(executionStartTime, leadTime);
        }
        return Arrays.asList(executionStartTime, leadTime);
    }

    private void addMastersOrder(String executionStartTime, String leadTime) {
        String message = "";
        CarOfficeController carOfficeController = CarOfficeController.getInstance();
        OrderController orderController = OrderController.getInstance();
        List<Master> freeMaster = carOfficeController.getFreeMasters(executionStartTime, leadTime);
        List<Master> orderMasters = new ArrayList<>();
        PrinterMaster.printMasters(freeMaster);
        System.out.println("0. Stop adding");
        while (!message.equals("masters add successfully")) {
            addMasters(freeMaster, orderMasters);
            message = orderController.addOrderMasters(orderMasters);
        }
    }

    private void addMasters(List<Master> masters, List<Master> orderMaster) {
        boolean userAnswer = false;
        while (!userAnswer) {
            int index = ScannerUtil.getIntUser("Enter the index number of the master to add:");
            if (index == 0 && orderMaster.size() > 0) {
                return;
            }
            if (index == 0) {
                System.out.println("Add at least one master!");
                continue;
            }
            if (index > masters.size() || index < 0) {
                System.out.println("There is no such master");
                continue;
            }
            if (orderMaster.contains(masters.get(index - 1))) {
                System.out.println("You have already add such master!");
                continue;
            }
            orderMaster.add(masters.get(index - 1));
            userAnswer = isAnotherMaster();
        }
    }

    private boolean isAnotherMaster() {
        String answer = "";
        while (!answer.equals("y") && !answer.equals("n")) {
            answer = ScannerUtil.getStringUser("Add another master to the order? y/n");
            if (!answer.equals("y") && !answer.equals("n")) {
                System.out.println("You have entered wrong answer!");
            }
        }
        return answer.equals("n");
    }

    private Garage addGarageOrder(List<Garage> garages, GarageController garageController) {
        boolean isFreePlaceGarage = false;
        int index = 0;
        while (!isFreePlaceGarage) {
            System.out.println();
            index = ScannerUtil.getIntUser("Enter the index number of the garage to add in order:");
            if (garageController.getNumberFreePlaceGarage(garages.get(index - 1)) < 1) {
                System.out.println("There are no free place in garage");
                continue;
            }
            isFreePlaceGarage = true;
        }
        return garages.get(index - 1);
    }

    private boolean isPlace(List<Garage> garages) {
        for (Garage garage : garages) {
            if (garage.getPlaces().size() > 0) {
                return true;
            }
        }
        return false;
    }
}