package com.senla.carservice.api.action;

import com.senla.carservice.api.action.util.Checker;
import com.senla.carservice.controller.GarageController;

import java.util.Scanner;

public final class AddGarageActionImpl implements Action {
    private static AddGarageActionImpl instance;

    public AddGarageActionImpl() {
    }

    public static AddGarageActionImpl getInstance() {
        if (instance == null) {
            instance = new AddGarageActionImpl();
        }
        return instance;
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
            if (Checker.isSymbolsString(name)){
                System.out.println("You enter wrong value!!!");
                continue;
            }
            message = garageController.addGarage(name);
            break;
        }
        System.out.println(message);
    }
}