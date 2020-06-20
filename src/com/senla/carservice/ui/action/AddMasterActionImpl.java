package com.senla.carservice.ui.action;

import com.senla.carservice.ui.util.Checker;
import com.senla.carservice.controller.MasterController;

import java.util.Scanner;

public class AddMasterActionImpl implements Action {

    public AddMasterActionImpl() {
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        MasterController masterController = MasterController.getInstance();
        String message;
        String name;
        while (true) {
            System.out.println("Enter the name of master");
            name = scanner.nextLine();
            if (Checker.isSymbolsString(name)) {
                System.out.println("You enter wrong value!!!");
                continue;
            }
            message = masterController.addMaster(name);
            break;
        }
        System.out.println(message);
    }
}