package com.senla.carservice.api.action;

import com.senla.carservice.controller.CarOfficeController;

public class FreeDateActionImpl implements Action {

    public FreeDateActionImpl() {
    }

    @Override
    public void execute() {
        CarOfficeController carOfficeController = new CarOfficeController();
        String message;
        message = carOfficeController.getNearestFreeDate();
        System.out.println(message);
    }
}