package com.senla.carservice.ui.action;

import com.senla.carservice.controller.GarageController;
import com.senla.carservice.ui.util.ScannerUtil;

public class AddGarageActionImpl implements Action {

    public AddGarageActionImpl() {
    }

    @Override
    public void execute() {
        GarageController garageController = GarageController.getInstance();
        String name = ScannerUtil.getStringUser("Enter the name of garage");
        System.out.println(garageController.addGarage(name));
    }
}