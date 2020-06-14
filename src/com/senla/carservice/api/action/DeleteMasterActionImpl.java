package com.senla.carservice.api.action;

import com.senla.carservice.api.printer.PrinterMaster;
import com.senla.carservice.api.printer.PrinterOrder;
import com.senla.carservice.controller.MasterController;
import com.senla.carservice.domain.Master;

import java.util.Scanner;

public final class DeleteMasterActionImpl implements Action {
    private static DeleteMasterActionImpl instance;

    public DeleteMasterActionImpl() {
    }

    public static DeleteMasterActionImpl getInstance() {
        if (instance == null) {
            instance = new DeleteMasterActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        MasterController masterController = new MasterController();
        Scanner scanner = new Scanner(System.in);
        Master[] masters = masterController.getMasters();
        if (masters.length == 0){
            System.out.println("There are no masters to delete!");
            return;
        }
        PrinterMaster.printMasters(masters);
        String message;
        while (true){
            System.out.println("Enter the index number of the master to delete:");
            while (!scanner.hasNextInt()) {
                System.out.println("You enter wrong value!!!");
                System.out.println("Try again:");
                scanner.next();
            }
            int index = scanner.nextInt();
            if (index > masters.length ||index < 1){
                System.out.println("There is no such master");
                continue;
            }
            message = masterController.deleteMaster(masters[index-1]);
            break;
        }
        System.out.println(message);


    }
}
