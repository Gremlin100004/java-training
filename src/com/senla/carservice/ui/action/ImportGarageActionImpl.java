package com.senla.carservice.ui.action;

import com.senla.carservice.controller.GarageController;

public class ImportGarageActionImpl implements Action{
    public ImportGarageActionImpl() {
    }

    @Override
    public void execute() {
        GarageController garageController = GarageController.getInstance();
        System.out.println(garageController.importGarages());
    }
}
