package com.senla.carservice.ui.action;

import com.senla.carservice.controller.GarageController;
import com.senla.carservice.domain.Garage;
import com.senla.carservice.ui.printer.PrinterGarages;
import com.senla.carservice.ui.util.ScannerUtil;

import java.util.List;

public class DeletePlaceActionImpl implements Action {

    public DeletePlaceActionImpl() {
    }

    @Override
    public void execute() {
        GarageController garageController = GarageController.getInstance();
        List<Garage> garages = garageController.getArrayGarages();
        if (garages.isEmpty()) {
            System.out.println("There are no garages to delete place!");
            return;
        }
        PrinterGarages.printGarages(garages);
        System.out.println("0. Previous menu");
        String message = null;
        int index;
        while (message == null) {
            index = ScannerUtil.getIntUser("Enter the index number of the garage to delete:");
            if (index == 0) {
                return;
            }
            if (index > garages.size() || index < 0) {
                System.out.println("There is no such garage");
                continue;
            }
            if (garages.get(index - 1).getPlaces().size() < 1) {
                System.out.println("There are no places in garage!");
                continue;
            }
            message = garageController.deleteGaragePlace(garages.get(index - 1));
            System.out.println(message);
        }
    }
}