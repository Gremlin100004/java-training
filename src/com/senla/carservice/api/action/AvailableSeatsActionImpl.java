package com.senla.carservice.api.action;

import com.senla.carservice.controller.CarOfficeController;

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
        String message;
        String date;
        while (true){
            System.out.println("Enter the date in format dd.mm.yyyy, example:\"10.10.2010\"");
            date = scanner.nextLine();
            message = carOfficeController.getFreePlacesByDate(date);
            if (message.equals("error date")){
                System.out.println("You enter wrong value!!!");
                continue;
            }
            break;
        }
        System.out.println(message);
    }
}