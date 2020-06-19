package com.senla.carservice.api.action;

import com.senla.carservice.api.printer.PrinterGarages;
import com.senla.carservice.controller.GarageController;
import com.senla.carservice.domain.Garage;

import java.util.ArrayList;

public class ListGaragesActionImpl implements Action {

    public ListGaragesActionImpl() {
    }

    @Override
    public void execute() {
        GarageController garageController = new GarageController();
        ArrayList<Garage> garages = garageController.getArrayGarages();
        if (garages.size() == 0) {
            System.out.println("There are no garages.");
            return;
        }
        PrinterGarages.printGarages(garages);
    }
}