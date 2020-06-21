package com.senla.carservice.ui.action;

import com.senla.carservice.controller.GarageController;
import com.senla.carservice.domain.Garage;
import com.senla.carservice.ui.printer.PrinterGarages;

import java.util.List;

public class ListGaragesActionImpl implements Action {

    public ListGaragesActionImpl() {
    }

    @Override
    public void execute() {
        GarageController garageController = GarageController.getInstance();
        List<Garage> garages = garageController.getArrayGarages();
        if (garages.isEmpty()) {
            System.out.println("There are no garages.");
            return;
        }
        PrinterGarages.printGarages(garages);
    }
}