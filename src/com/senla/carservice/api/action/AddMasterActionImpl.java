package com.senla.carservice.api.action;

import com.senla.carservice.api.Checker;
import com.senla.carservice.controller.CarOfficeController;
import com.senla.carservice.controller.MasterController;

import java.util.Scanner;

public final class AddMasterActionImpl implements Action {
    private static AddMasterActionImpl instance;

    public AddMasterActionImpl() {
    }

    public static AddMasterActionImpl getInstance() {
        if (instance == null) {
            instance = new AddMasterActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        MasterController masterController = new MasterController();
        String message;
        String name;
        while (true) {
            System.out.println("Enter the name of master");
            name = scanner.nextLine();
            if (Checker.isSymbolsString(name)){
                System.out.println("You enter wrong value!!!");
                continue;
            }
            message = masterController.addMaster(name);
            break;
        }
        System.out.println(message);
    }
}
