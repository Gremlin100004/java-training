package com.senla.carservice.ui.action;

import com.senla.carservice.ui.printer.PrinterMaster;
import com.senla.carservice.controller.MasterController;
import com.senla.carservice.domain.Master;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeleteMasterActionImpl implements Action {

    public DeleteMasterActionImpl() {
    }

    @Override
    public void execute() {
        MasterController masterController = MasterController.getInstance();
        Scanner scanner = new Scanner(System.in);
        List<Master> masters = masterController.getMasters();
        if (masters.size() == 0){
            System.out.println("There are no masters to delete!");
            return;
        }
        PrinterMaster.printMasters(masters);
        System.out.println("0. Previous menu");
        String message;
        while (true){
            System.out.println("Enter the index number of the master to delete:");
            while (!scanner.hasNextInt()) {
                System.out.println("You enter wrong value!!!");
                System.out.println("Try again:");
                scanner.next();
            }
            int index = scanner.nextInt();
            if (index == 0){
                return;
            }
            if (index > masters.size() ||index < 0){
                System.out.println("There is no such master");
                continue;
            }
            message = masterController.deleteMaster(masters.get(index-1));
            break;
        }
        System.out.println(message);


    }
}