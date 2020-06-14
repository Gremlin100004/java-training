package com.senla.carservice.api.action;

import com.senla.carservice.api.printer.PrinterGarages;
import com.senla.carservice.controller.GarageController;
import com.senla.carservice.domain.Garage;

import java.util.Scanner;

public final class AddPlaceActionImpl implements Action {
    private static AddPlaceActionImpl instance;

    public AddPlaceActionImpl() {
    }

    public static AddPlaceActionImpl getInstance() {
        if (instance == null) {
            instance = new AddPlaceActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        GarageController garageController = new GarageController();
        Garage[] garages = garageController.getArrayGarages();
        if (garages.length == 0) {
            System.out.println("There are no garages!");
            return;
        }
        PrinterGarages.printGarages(garages);
        String message;
        while (true) {
            System.out.println("Enter the index number of the garage to add place:");
            while (!scanner.hasNextInt()) {
                System.out.println("You enter wrong value!!!");
                System.out.println("Try again:");
                scanner.next();
            }
            int index = scanner.nextInt();
            if (index > garages.length || index < 1) {
                System.out.println("There is no such garage");
                continue;
            }
            message = garageController.addGaragePlace(garages[index-1]);
            break;
        }
        System.out.println(message);
    }
}

