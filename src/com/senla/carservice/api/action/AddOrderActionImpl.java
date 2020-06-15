package com.senla.carservice.api.action;

import com.senla.carservice.api.action.util.Checker;
import com.senla.carservice.api.printer.PrinterGarages;
import com.senla.carservice.api.printer.PrinterMaster;
import com.senla.carservice.controller.GarageController;
import com.senla.carservice.controller.MasterController;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Place;
import com.senla.carservice.dto.OrderDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public final class AddOrderActionImpl implements Action {
    private static AddOrderActionImpl instance;

    public AddOrderActionImpl() {
    }

    public static AddOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new AddOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        OrderController orderController = new OrderController();
        MasterController masterController = new MasterController();
        GarageController garageController = new GarageController();
        ArrayList<Master> masters = masterController.getMasters();
        ArrayList<Garage> garages = garageController.getArrayGarages();
        ArrayList<Master> orderMasters = new ArrayList<>();
        Garage garage;
        Place place;
        String message;
        String automaker;
        String model;
        String registrationNumber;
        String executionStartTime;
        String leadTime;
        BigDecimal price;
        while (true) {
            System.out.println("Enter the automaker of car");
            automaker = scanner.nextLine();
            if (Checker.isSymbolsString(automaker)) {
                System.out.println("You enter wrong value!!!");
                continue;
            }
            System.out.println("Enter the model of car");
            model = scanner.nextLine();
            if (Checker.isSymbolsString(model)) {
                System.out.println("You enter wrong value!!!");
                continue;
            }
            System.out.println("Enter the registartion number of car, example: 1111 AB-7");
            registrationNumber = scanner.nextLine();
            if (Checker.isSymbolsStringNumber(registrationNumber)) {
                System.out.println("You enter wrong value!!!");
                continue;
            }
            System.out.println("Enter the model of car");
            model = scanner.nextLine();
            if (Checker.isSymbolsString(model)) {
                System.out.println("You enter wrong value!!!");
                continue;
            }
            System.out.println("Enter the planing time start to execute the order in format dd.mm.yyyy \"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"");
            executionStartTime = scanner.nextLine();
            System.out.println("Enter the lead time the order in format dd.mm.yyyy \"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"");
            leadTime = scanner.nextLine();
            addMastersOrder(masters, orderMasters, scanner);
            garage = addGarageOrder(garages, scanner, garageController);
            place = garageController.getFreePlaceGarage(garage).get(0);
            while (!scanner.hasNextInt()) {
                System.out.println("You enter wrong value!!!");
                System.out.println("Try again:");
                scanner.next();
            }
            System.out.println("Enter the price");
            price = scanner.nextBigDecimal();
            OrderDto orderDto = new OrderDto(executionStartTime, leadTime,
                    orderMasters, garage, place, automaker, model,
                    registrationNumber, price);
            message = orderController.addOrder(orderDto);
            System.out.println(message);
            if (message.equals("order add successfully!")) {
                break;
            }
        }
    }

    private ArrayList<Master> addMastersOrder(ArrayList<Master> masters, ArrayList<Master> orderMaster, Scanner scanner) {
        while (true) {
            PrinterMaster.printMasters(masters);
            System.out.println("Enter the index number of the master to add:");
            while (!scanner.hasNextInt()) {
                System.out.println("You enter wrong value!!!");
                System.out.println("Try again:");
                scanner.next();
            }
            int index = scanner.nextInt();
            if (index > masters.size() || index < 1) {
                System.out.println("There is no such master");
                continue;
            }
            orderMaster.add(masters.get(index - 1));
            if (!isAnotherMaster(scanner)) {
                return orderMaster;
            }
        }
    }

    private boolean isAnotherMaster(Scanner scanner) {
        String answer;
        while (true) {
            System.out.println("Add another master to the order? y/n");
            answer = scanner.nextLine();
            if (answer.equals("y")) {
                return true;
            } else if (answer.equals("n")) {
                return false;
            } else {
                System.out.println("You have entered wrong answer!");
            }
        }
    }

    private Garage addGarageOrder(ArrayList<Garage> garages, Scanner scanner, GarageController garageController) {
        PrinterGarages.printGarages(garages);
        while (true) {
            System.out.println("Enter the index number of the garage to add in order:");
            while (!scanner.hasNextInt()) {
                System.out.println("You enter wrong value!!!");
                System.out.println("Try again:");
                scanner.next();
            }
            int index = scanner.nextInt();
            if (index > garages.size() || index < 1) {
                System.out.println("There is no such garage");
                continue;
            }
            if (garageController.getNumberFreePlaceGarage(garages.get(index-1)) < 1) {
                System.out.println("There are no free place in garage");
                continue;
            }
            return garages.get(index);
        }
    }

    private boolean isPlace(Garage[] garages) {
        int numberPlace = 0;
        for (Garage garage : garages) {
            numberPlace += garage.getPlaces().size();
        }
        return numberPlace >= 1;
    }
}