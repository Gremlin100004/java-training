package com.senla.carservice;

import com.senla.carservice.controller.CarServiceController;
import com.senla.carservice.domain.*;
import com.senla.carservice.repository.Garage;
import com.senla.carservice.repository.Order;
import com.senla.carservice.repository.OrderDto;
import com.senla.carservice.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws ParseException {
        CarServiceController carServiceController = new CarServiceController();
        TestData testData = new TestData();
        String message;
        String delimiter = "***********************************************************************";
        message = carServiceController.createCarService(testData.getNameCarService());
        System.out.println(message);
        System.out.println(delimiter);

        System.out.println("Add master:");
        for (String masterName:testData.getArrayMasterNames()){
            message = carServiceController.addMaster(masterName);
            System.out.println(String.format(" -master \"%s\" has been added to service.", message));
        }
        System.out.println(delimiter);

        System.out.println("Get array masters.");
        Master[] masters = carServiceController.getMasters();
        for (Master master: carServiceController.getMasters()) {
            System.out.println(String.format(" -master with name \"%s\"", master.getName()));
        }
        System.out.println(delimiter);

        System.out.println("Delete master:");
        message = carServiceController.deleteMaster(masters[1]);
        System.out.println(String.format(" -master with name \"%s\" has been deleted", message));
        System.out.println(delimiter);

        System.out.println("Add garage to service:");
        for (String garageName:testData.getArrayGarageNames()){
            message = carServiceController.addGarage(garageName);
            System.out.println(String.format(" -garage \"%s\" has been added to service", message));
        }
        System.out.println(delimiter);

        System.out.println("Get array garages:");
        Garage[] garages = carServiceController.getArrayGarages();
        for (Garage garage:garages) {
            System.out.println(String.format(" -garage \"%s\"", garage.getName()));
        }
        System.out.println(delimiter);

        System.out.println("Add places in garages.");
        for (Garage garage : garages) {
            for (int j = 0; j < 4; j++) {
                message = carServiceController.addGaragePlace(garage);
                System.out.println(String.format("Add place in garage \"%s\"", message));
            }
        }
        System.out.println(delimiter);

        System.out.println("Get garage places.");
        int numberPlaces = carServiceController.getNumberGaragePlaces(garages[1]);
        System.out.println(String.format(" In garage \"%s\" number place: %s", garages[1].getName(), numberPlaces));
        System.out.println(delimiter);

        System.out.println("Delete garage:");
        message = carServiceController.deleteGarage(garages[2]);
        System.out.println(String.format(" -delete garage in service with name \"%s\"", message));
        System.out.println(delimiter);

        System.out.println("Delete place in Garage:");
        message = carServiceController.deleteGaragePlace(garages[1]);
        System.out.println(String.format(" -the place in garage with name \"%s\" has been deleted successfully.", message));
        System.out.println(delimiter);

        System.out.println("Add new orders to car service:");
        int indexMaster = 0;
        int indexGarage = 0;
        int indexPlace = 0;
        for (int i = 0; i < testData.getArrayAutomaker().length; i++) {
            Master[] mastersOrder = new Master[2];
            for (int j = 0; j < 2; j++) {
                mastersOrder[j] = carServiceController.getMasters()[indexMaster];
                indexMaster++;
                if (indexMaster == carServiceController.getMasters().length - 1) {
                    indexMaster = 0;
                }
            }
            OrderDto orderDto = new OrderDto(testData.getArrayExecutionStartTime()[i], testData.getArrayLeadTime()[i],
                    mastersOrder, garages[indexGarage], garages[indexGarage].getPlaces()[indexPlace],
                    testData.getArrayAutomaker()[i], testData.getArrayModel()[i],
                    testData.getArrayRegistrationNumber()[i], testData.getArrayPrice()[i]);

            message = carServiceController.addOrder(orderDto);
            System.out.println(message);
            indexPlace++;
            if (indexPlace == 3) {
                indexPlace = 0;
                indexGarage++;
            }
        }
        Order[] orders = carServiceController.getOrders();
        Printer.printOrder(orders);

        System.out.println("Transfer an order to execution status:");
        Order[] choosedOrders = new Order[5];
        choosedOrders[0] = orders[0];
        choosedOrders[1] = orders[1];
        choosedOrders[2] = orders[7];
        choosedOrders[3] = orders[8];
        choosedOrders[4] = orders[9];
        for (Order order: choosedOrders){
            message = carServiceController.completeOrder(order);
            System.out.println(message);
            Printer.printOrder(new Order[]{order});
        }
        System.out.println(delimiter);

        System.out.println("Get free place in garages");
        int numberFreePlaces = 0;
        for (Garage garage: garages){
            numberFreePlaces = carServiceController.getNumberFreePlaceGarage(garage);
            System.out.println(String.format(" -garage with name \"%s\" %s available places", garage.getName(),
                    numberFreePlaces));
        }
        System.out.println(delimiter);

        System.out.println("Close order:");
        choosedOrders = new Order[3];
        choosedOrders[0] = orders[0];
        choosedOrders[1] = orders[1];
        choosedOrders[2] = orders[9];
        for (Order order : choosedOrders) {
            message = carServiceController.closeOrder(order);
            System.out.println(message);
            Printer.printOrder(new Order[]{order});
        }
        System.out.println(delimiter);

        System.out.println("Cancel order:");
        choosedOrders = new Order[3];
        choosedOrders[0] = orders[2];
        choosedOrders[1] = orders[10];
        choosedOrders[2] = orders[11];
        for (Order order : choosedOrders) {
            message = carServiceController.cancelOrder(order);
            System.out.println(message);
            Printer.printOrder(new Order[]{order});
        }
        System.out.println(delimiter);

        System.out.println("Delete order:");
        choosedOrders = new Order[2];
        choosedOrders[0] = orders[0];
        choosedOrders[1] = orders[10];
        for (Order order : choosedOrders) {
            message = carServiceController.deleteOrder(order);
            System.out.println(message);
            Printer.printOrder(new Order[]{order});
        }
        System.out.println(delimiter);

        System.out.println("Shift lead time:");
        String executionStartTime = "14.07.2020 10:00";
        String leadTime = "15.07.2020 10:00";
        message = carServiceController.shiftLeadTime(orders[3], executionStartTime, leadTime);
        System.out.println(message);
        Printer.printOrder(new Order[]{orders[3]});
        System.out.println(delimiter);

        System.out.println("Get oders sort by creation time:");
        Order[] sortArrayOrders;
        sortArrayOrders = carServiceController.sortOrderByCreationTime(orders);
        Printer.printOrder(sortArrayOrders);
        System.out.println(delimiter);

        System.out.println("Get oders sort by lead time:");
        sortArrayOrders = carServiceController.sortOrderByLeadTime(orders);
        Printer.printOrder(sortArrayOrders);
        System.out.println(delimiter);

        System.out.println("Get oders sort by execution start time.");
        sortArrayOrders = carServiceController.sortOrderByStartTime(orders);
        Printer.printOrder(sortArrayOrders);
        System.out.println(delimiter);

        System.out.println("Get oders sort by price:");
        sortArrayOrders = carServiceController.sortOrderByPrice(orders);
        Printer.printOrder(sortArrayOrders);
        System.out.println(delimiter);

        System.out.println("Get oders are being executed:");
        Order[] executedOrders;
        executedOrders = carServiceController.getExecuteOrder();
        Printer.printOrder(executedOrders);

        System.out.println(" -sort by creation time");
        sortArrayOrders = carServiceController.sortOrderByCreationTime(executedOrders);
        Printer.printOrder(sortArrayOrders);

        System.out.println(" -sort by lead time");
        sortArrayOrders = carServiceController.sortOrderByLeadTime(executedOrders);
        Printer.printOrder(sortArrayOrders);

        System.out.println(" -sort by price");
        sortArrayOrders = carServiceController.sortOrderByPrice(executedOrders);
        Printer.printOrder(sortArrayOrders);
        System.out.println(delimiter);

        System.out.println("Receive orders for a period of time:");
        Printer.printOrder(executedOrders);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        Date startPeriod = new Date();
        Date endPeriod = new Date();
        String stringStartPeriod = format.format(DateUtil.addDays(startPeriod, 1));
        String stringEndPeriod = format.format(DateUtil.addDays(endPeriod, -1));
        System.out.println(String.format("%s - %s", stringStartPeriod, stringEndPeriod));
        Order[] periodOrders = carServiceController.getOrdersByPeriod(stringStartPeriod, stringEndPeriod);
        Printer.printOrder(periodOrders);

        System.out.println("- completed orders");
        sortArrayOrders = carServiceController.getCompletedOrders(orders);
        Printer.printOrder(sortArrayOrders);
        System.out.println("  sorted by filing date:");
        sortArrayOrders = carServiceController.sortOrderByCreationTime(sortArrayOrders);
        Printer.printOrder(sortArrayOrders);
        System.out.println("  sorted by execution date:");
        sortArrayOrders = carServiceController.sortOrderByStartTime(sortArrayOrders);
        Printer.printOrder(sortArrayOrders);
        System.out.println("  sorted by price:");
        sortArrayOrders = carServiceController.sortOrderByPrice(sortArrayOrders);
        Printer.printOrder(sortArrayOrders);

        System.out.println("- cancel orders");
        sortArrayOrders = carServiceController.getCanceledOrders(orders);
        Printer.printOrder(sortArrayOrders);
        System.out.println("  sorted by filing date:");
        sortArrayOrders = carServiceController.sortOrderByCreationTime(sortArrayOrders);
        Printer.printOrder(sortArrayOrders);
        System.out.println("  sorted by execution date:");
        sortArrayOrders = carServiceController.sortOrderByStartTime(sortArrayOrders);
        Printer.printOrder(sortArrayOrders);
        System.out.println("  sorted by price:");
        sortArrayOrders = carServiceController.sortOrderByPrice(sortArrayOrders);
        Printer.printOrder(sortArrayOrders);

        System.out.println("- deleted orders");
        sortArrayOrders = carServiceController.getDeletedOrders(orders);
        Printer.printOrder(sortArrayOrders);
        System.out.println("  sorted by filing date:");
        sortArrayOrders = carServiceController.sortOrderByCreationTime(sortArrayOrders);
        Printer.printOrder(sortArrayOrders);
        System.out.println("  sorted by execution date:");
        sortArrayOrders = carServiceController.sortOrderByStartTime(sortArrayOrders);
        Printer.printOrder(sortArrayOrders);
        System.out.println("  sorted by price:");
        sortArrayOrders = carServiceController.sortOrderByPrice(sortArrayOrders);
        Printer.printOrder(sortArrayOrders);
        System.out.println(delimiter);

        System.out.println("Sort array masters by alphabet:");
        Master[] sortArrayMasters = carServiceController.sortMastersByAlphabet();
        for (Master master: sortArrayMasters) {
            System.out.println(String.format(" -master with name \"%s\"", master.getName()));
        }
        System.out.println(delimiter);

        System.out.println("Sort array masters by busy:");
        sortArrayMasters = carServiceController.sortMastersByBusy();
        for (Master master: sortArrayMasters) {
            System.out.println(String.format(" -master with name \"%s\" orders: %s", master.getName(), master.getNumberOrder()));
        }
        System.out.println(delimiter);

        System.out.println("Get orders executed concrete master.");
        sortArrayOrders = carServiceController.getMasterOrders(masters[18]);
        System.out.println(String.format(" -the master \"%s\" fulfills the orders:", masters[18].getName()));
        Printer.printOrder(sortArrayOrders);
        System.out.println(delimiter);

        System.out.println("Get masters performing a specific order:");
        Master[] orderMasters = carServiceController.getOrderMasters(orders[8]);
        Printer.printOrder(new Order[]{orders[8]});
        for (Master master : orderMasters) {
            System.out.println(String.format(" - %s;", master.getName()));
        }
        System.out.println(delimiter);

        System.out.println("Get number of free places");
        String anyDate = "15.07.2020";
        System.out.println(String.format("on date %s", anyDate));
        message = carServiceController.getFreePlacesByDate(anyDate);
        System.out.println(message);
        System.out.println(delimiter);

        System.out.println("Get nearest free date.");
        message = carServiceController.getNearestFreeDate();
        System.out.println(message);
        System.out.println(delimiter);
    }
}