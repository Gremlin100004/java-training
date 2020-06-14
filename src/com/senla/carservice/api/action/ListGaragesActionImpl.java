package com.senla.carservice.api.action;

import com.senla.carservice.api.printer.PrinterGarages;
import com.senla.carservice.controller.GarageController;
import com.senla.carservice.domain.Garage;

public final class ListGaragesActionImpl implements Action {
    private static ListGaragesActionImpl instance;

    public ListGaragesActionImpl() {
    }

    public static ListGaragesActionImpl getInstance() {
        if (instance == null) {
            instance = new ListGaragesActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        GarageController garageController = new GarageController();
        Garage[] garages = garageController.getArrayGarages();
        if (garages.length == 0) {
            System.out.println("There are no garages.");
            return;
        }
        PrinterGarages.printGarages(garages);
    }
}