package com.senla.carservice.ui.action;

import com.senla.carservice.controller.CarOfficeController;

public class FreeDateActionImpl implements Action {

    public FreeDateActionImpl() {
    }

    @Override
    public void execute() {
        CarOfficeController carOfficeController = CarOfficeController.getInstance();
        String message;
        message = carOfficeController.getNearestFreeDate();
        System.out.println(message);
    }
}