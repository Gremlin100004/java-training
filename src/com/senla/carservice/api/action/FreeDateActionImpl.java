package com.senla.carservice.api.action;

import com.senla.carservice.controller.CarOfficeController;

import java.util.Scanner;

public final class FreeDateActionImpl implements Action {
    private static FreeDateActionImpl instance;

    public FreeDateActionImpl() {
    }

    public static FreeDateActionImpl getInstance() {
        if (instance == null) {
            instance = new FreeDateActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        CarOfficeController carOfficeController = new CarOfficeController();
        String message;
        message = carOfficeController.getNearestFreeDate();
        System.out.println(message);
    }
}
