package com.senla.carservice.ui.action;

import com.senla.carservice.controller.CarOfficeController;
import com.senla.carservice.controller.GarageController;
import com.senla.carservice.controller.MasterController;

import java.util.Scanner;

public class AvailableSeatsActionImpl implements Action {

    public AvailableSeatsActionImpl() {
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        CarOfficeController carOfficeController = CarOfficeController.getInstance();
        GarageController garageController = GarageController.getInstance();
        MasterController masterController = MasterController.getInstance();
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