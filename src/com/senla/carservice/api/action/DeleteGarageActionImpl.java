package com.senla.carservice.api.action;

import com.senla.carservice.api.printer.PrinterGarages;
import com.senla.carservice.controller.GarageController;
import com.senla.carservice.domain.Garage;

import java.util.ArrayList;
import java.util.Scanner;

public final class DeleteGarageActionImpl implements Action {
    private static DeleteGarageActionImpl instance;

    public DeleteGarageActionImpl() {
    }

    public static DeleteGarageActionImpl getInstance() {
        if (instance == null) {
            instance = new DeleteGarageActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        GarageController garageController = new GarageController();
        Scanner scanner = new Scanner(System.in);
        ArrayList<Garage> garages = garageController.getArrayGarages();
        if (garages.size() == 0) {
            System.out.println("There are no garages to delete!");
            return;
        }
        PrinterGarages.printGarages(garages);
        String message;
        while (true) {
            System.out.println("Enter the index number of the garage to delete:");
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
            message = garageController.deleteGarage(garages.get(index - 1));
            break;
        }
        System.out.println(message);
    }
}