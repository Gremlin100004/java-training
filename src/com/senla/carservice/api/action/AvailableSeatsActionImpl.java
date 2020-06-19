package com.senla.carservice.api.action;

import com.senla.carservice.controller.CarOfficeController;
import com.senla.carservice.controller.GarageController;
import com.senla.carservice.controller.MasterController;

import java.util.Scanner;

public final class AvailableSeatsActionImpl implements Action {
    private static AvailableSeatsActionImpl instance;

    public AvailableSeatsActionImpl() {
    }

    public static AvailableSeatsActionImpl getInstance() {
        if (instance == null) {
            instance = new AvailableSeatsActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        CarOfficeController carOfficeController = new CarOfficeController();
        GarageController garageController = new GarageController();
        MasterController masterController = new MasterController();
        String message;
        String date;
        if (masterController.getMasters().size() == 0) {
            System.out.println("Add masters to service!!!");
            return;
        }
        if (garageController.getNumberFreePlaces() == 0) {
            System.out.println("Add places to garages!!!");
            return;
        }
        while (true) {
            System.out.println("Enter the date in format dd.mm.yyyy, example:\"10.10.2010\"");
            date = scanner.nextLine();
            message = carOfficeController.getFreePlacesByDate(date);
            if (message.equals("error date")) {
                System.out.println("You enter wrong value!!!");
                continue;
            }
            if (message.equals("past date")) {
                System.out.println("Entered date cannot be in the past!!!");
                continue;
            }
            break;
        }
        System.out.println(message);
    }
}