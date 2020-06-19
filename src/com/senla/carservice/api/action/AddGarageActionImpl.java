package com.senla.carservice.api.action;

import com.senla.carservice.api.util.Checker;
import com.senla.carservice.controller.GarageController;

import java.util.Scanner;

public class AddGarageActionImpl implements Action {

    public AddGarageActionImpl() {
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        GarageController garageController = new GarageController();
        String message;
        String name;
        while (true) {
            System.out.println("Enter the name of garage");
            name = scanner.nextLine();
            if (Checker.isSymbolsString(name)) {
                System.out.println("You enter wrong value!!!");
                continue;
            }
            message = garageController.addGarage(name);
            break;
        }
        System.out.println(message);
    }
}