//package com.senla.carservice;
//
//import com.senla.carservice.api.printer.PrinterOrder;
//import com.senla.carservice.controller.CarOfficeController;
//import com.senla.carservice.controller.GarageController;
//import com.senla.carservice.controller.MasterController;
//import com.senla.carservice.controller.OrderController;
//import com.senla.carservice.domain.Garage;
//import com.senla.carservice.domain.Master;
//import com.senla.carservice.domain.Order;
//import com.senla.carservice.dto.OrderDto;
//import com.senla.carservice.util.DateUtil;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class TEMPORARY {
//
//    public void test(){
//        CarOfficeController carOfficeController = new CarOfficeController();
//        GarageController garageController = new GarageController();
//        MasterController masterController = new MasterController();
//        OrderController orderController = new OrderController();
//        TestData testData = new TestData();
//        String message;
//        String delimiter = "***********************************************************************";
//        System.out.println(delimiter);
//
//        System.out.println("Add master:");
//        for (String masterName : testData.getArrayMasterNames()) {
//            message = masterController.addMaster(masterName);
//            System.out.println(String.format(" -master \"%s\" has been added to service.", message));
//        }
//        System.out.println(delimiter);
//
//        System.out.println("Get array masters.");
//        Master[] masters = masterController.getMasters();
//        for (Master master : masterController.getMasters()) {
//            System.out.println(String.format(" -master with name \"%s\"", master.getName()));
//        }
//        System.out.println(delimiter);
//
//        System.out.println("Delete master:");
//        message = masterController.deleteMaster(masters[1]);
//        System.out.println(String.format(" -master with name \"%s\" has been deleted", message));
//        System.out.println(delimiter);
//
//        System.out.println("Add garage to service:");
//        for (String garageName : testData.getArrayGarageNames()) {
//            message = garageController.addGarage(garageName);
//            System.out.println(String.format(" -garage \"%s\" has been added to service", message));
//        }
//        System.out.println(delimiter);
//
//        System.out.println("Get array garages:");
//        Garage[] garages = garageController.getArrayGarages();
//        for (Garage garage : garages) {
//            System.out.println(String.format(" -garage \"%s\"", garage.getName()));
//        }
//        System.out.println(delimiter);
//
//        System.out.println("Add places in garages.");
//        for (Garage garage : garages) {
//            for (int j = 0; j < 4; j++) {
//                message = garageController.addGaragePlace(garage);
//                System.out.println(String.format("Add place in garage \"%s\"", message));
//            }
//        }
//        System.out.println(delimiter);
//
//        System.out.println("Get garage places.");
//        int numberPlaces = garageController.getNumberGaragePlaces(garages[1]);
//        System.out.println(String.format(" In garage \"%s\" number place: %s", garages[1].getName(), numberPlaces));
//        System.out.println(delimiter);
//
//        System.out.println("Delete garage:");
//        message = garageController.deleteGarage(garages[2]);
//        System.out.println(String.format(" -delete garage in service with name \"%s\"", message));
//        System.out.println(delimiter);
//
//        System.out.println("Delete place in Garage:");
//        message = garageController.deleteGaragePlace(garages[1]);
//        System.out.println(String.format(" -the place in garage with name \"%s\" has been deleted successfully.", message));
//        System.out.println(delimiter);
//
//        System.out.println("Add new orders to car service:");
//        int indexMaster = 0;
//        int indexGarage = 0;
//        int indexPlace = 0;
//        for (int i = 0; i < testData.getArrayAutomaker().length; i++) {
//            Master[] mastersOrder = new Master[2];
//            for (int j = 0; j < 2; j++) {
//                mastersOrder[j] = masterController.getMasters()[indexMaster];
//                indexMaster++;
//                if (indexMaster == masterController.getMasters().length - 1) {
//                    indexMaster = 0;
//                }
//            }
//            OrderDto orderDto = new OrderDto(testData.getArrayExecutionStartTime()[i], testData.getArrayLeadTime()[i],
//                    mastersOrder, garages[indexGarage], garages[indexGarage].getPlaces()[indexPlace],
//                    testData.getArrayAutomaker()[i], testData.getArrayModel()[i],
//                    testData.getArrayRegistrationNumber()[i], testData.getArrayPrice()[i]);
//
//            message = orderController.addOrder(orderDto);
//            System.out.println(message);
//            indexPlace++;
//            if (indexPlace == 3) {
//                indexPlace = 0;
//                indexGarage++;
//            }
//        }
//        Order[] orders = orderController.getOrders();
//        PrinterOrder.printOrder(orders);
//
//        System.out.println("Transfer an order to execution status:");
//        Order[] choosedOrders = new Order[5];
//        choosedOrders[0] = orders[0];
//        choosedOrders[1] = orders[1];
//        choosedOrders[2] = orders[7];
//        choosedOrders[3] = orders[8];
//        choosedOrders[4] = orders[9];
//        for (Order order : choosedOrders) {
//            message = orderController.completeOrder(order);
//            System.out.println(message);
//            PrinterOrder.printOrder(new Order[]{order});
//        }
//        System.out.println(delimiter);
//
//        System.out.println("Get free place in garages");
//        int numberFreePlaces;
//        for (Garage garage : garages) {
//            numberFreePlaces = garageController.getNumberFreePlaceGarage(garage);
//            System.out.println(String.format(" -garage with name \"%s\" %s available places", garage.getName(),
//                    numberFreePlaces));
//        }
//        System.out.println(delimiter);
//
//        System.out.println("Close order:");
//        choosedOrders = new Order[3];
//        choosedOrders[0] = orders[0];
//        choosedOrders[1] = orders[1];
//        choosedOrders[2] = orders[9];
//        for (Order order : choosedOrders) {
//            message = orderController.closeOrder(order);
//            System.out.println(message);
//            PrinterOrder.printOrder(new Order[]{order});
//        }
//        System.out.println(delimiter);
//
//        System.out.println("Cancel order:");
//        choosedOrders = new Order[3];
//        choosedOrders[0] = orders[2];
//        choosedOrders[1] = orders[10];
//        choosedOrders[2] = orders[11];
//        for (Order order : choosedOrders) {
//            message = orderController.cancelOrder(order);
//            System.out.println(message);
//            PrinterOrder.printOrder(new Order[]{order});
//        }
//        System.out.println(delimiter);
//
//        System.out.println("Delete order:");
//        choosedOrders = new Order[2];
//        choosedOrders[0] = orders[0];
//        choosedOrders[1] = orders[10];
//        for (Order order : choosedOrders) {
//            message = orderController.deleteOrder(order);
//            System.out.println(message);
//            PrinterOrder.printOrder(new Order[]{order});
//        }
//        System.out.println(delimiter);
//
//        System.out.println("Shift lead time:");
//        String executionStartTime = "14.07.2020 10:00";
//        String leadTime = "15.07.2020 10:00";
//        message = orderController.shiftLeadTime(orders[3], executionStartTime, leadTime);
//        System.out.println(message);
//        PrinterOrder.printOrder(new Order[]{orders[3]});
//        System.out.println(delimiter);
//
//        System.out.println("Get oders sort by creation time:");
//        Order[] sortArrayOrders;
//        sortArrayOrders = orderController.sortOrderByCreationTime(orders);
//        PrinterOrder.printOrder(sortArrayOrders);
//        System.out.println(delimiter);
//
//        System.out.println("Get oders sort by lead time:");
//        sortArrayOrders = orderController.sortOrderByLeadTime(orders);
//        PrinterOrder.printOrder(sortArrayOrders);
//        System.out.println(delimiter);
//
//        System.out.println("Get oders sort by execution start time.");
//        sortArrayOrders = orderController.sortOrderByStartTime(orders);
//        PrinterOrder.printOrder(sortArrayOrders);
//        System.out.println(delimiter);
//
//        System.out.println("Get oders sort by price:");
//        sortArrayOrders = orderController.sortOrderByPrice(orders);
//        PrinterOrder.printOrder(sortArrayOrders);
//        System.out.println(delimiter);
//
//        System.out.println("Get oders are being executed:");
//        Order[] executedOrders;
//        executedOrders = orderController.getExecuteOrder();
//        PrinterOrder.printOrder(executedOrders);
//
//        System.out.println(" -sort by creation time");
//        sortArrayOrders = orderController.sortOrderByCreationTime(executedOrders);
//        PrinterOrder.printOrder(sortArrayOrders);
//
//        System.out.println(" -sort by lead time");
//        sortArrayOrders = orderController.sortOrderByLeadTime(executedOrders);
//        PrinterOrder.printOrder(sortArrayOrders);
//
//        System.out.println(" -sort by price");
//        sortArrayOrders = orderController.sortOrderByPrice(executedOrders);
//        PrinterOrder.printOrder(sortArrayOrders);
//        System.out.println(delimiter);
//
//        System.out.println("Receive orders for a period of time:");
//        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
//        Date startPeriod = new Date();
//        Date endPeriod = new Date();
//        String stringStartPeriod = format.format(DateUtil.addDays(startPeriod, -1));
//        String stringEndPeriod = format.format(DateUtil.addDays(endPeriod, 1));
//        System.out.println(String.format("%s - %s", stringStartPeriod, stringEndPeriod));
//        Order[] periodOrders = orderController.getOrdersByPeriod(stringStartPeriod, stringEndPeriod);
//        PrinterOrder.printOrder(periodOrders);
//
//        System.out.println("- completed orders");
//        sortArrayOrders = orderController.getCompletedOrders(orders);
//        PrinterOrder.printOrder(sortArrayOrders);
//        System.out.println("  sorted by filing date:");
//        sortArrayOrders = orderController.sortOrderByCreationTime(sortArrayOrders);
//        PrinterOrder.printOrder(sortArrayOrders);
//        System.out.println("  sorted by execution date:");
//        sortArrayOrders = orderController.sortOrderByStartTime(sortArrayOrders);
//        PrinterOrder.printOrder(sortArrayOrders);
//        System.out.println("  sorted by price:");
//        sortArrayOrders = orderController.sortOrderByPrice(sortArrayOrders);
//        PrinterOrder.printOrder(sortArrayOrders);
//
//        System.out.println("- cancel orders");
//        sortArrayOrders = orderController.getCanceledOrders(orders);
//        PrinterOrder.printOrder(sortArrayOrders);
//        System.out.println("  sorted by filing date:");
//        sortArrayOrders = orderController.sortOrderByCreationTime(sortArrayOrders);
//        PrinterOrder.printOrder(sortArrayOrders);
//        System.out.println("  sorted by execution date:");
//        sortArrayOrders = orderController.sortOrderByStartTime(sortArrayOrders);
//        PrinterOrder.printOrder(sortArrayOrders);
//        System.out.println("  sorted by price:");
//        sortArrayOrders = orderController.sortOrderByPrice(sortArrayOrders);
//        PrinterOrder.printOrder(sortArrayOrders);
//
//        System.out.println("- deleted orders");
//        sortArrayOrders = orderController.getDeletedOrders(orders);
//        PrinterOrder.printOrder(sortArrayOrders);
//        System.out.println("  sorted by filing date:");
//        sortArrayOrders = orderController.sortOrderByCreationTime(sortArrayOrders);
//        PrinterOrder.printOrder(sortArrayOrders);
//        System.out.println("  sorted by execution date:");
//        sortArrayOrders = orderController.sortOrderByStartTime(sortArrayOrders);
//        PrinterOrder.printOrder(sortArrayOrders);
//        System.out.println("  sorted by price:");
//        sortArrayOrders = orderController.sortOrderByPrice(sortArrayOrders);
//        PrinterOrder.printOrder(sortArrayOrders);
//        System.out.println(delimiter);
//
//        System.out.println("Sort array masters by alphabet:");
//        Master[] sortArrayMasters = masterController.sortMasterByAlphabet();
//        for (Master master : sortArrayMasters) {
//            System.out.println(String.format(" -master with name \"%s\"", master.getName()));
//        }
//        System.out.println(delimiter);
//
//        System.out.println("Sort array masters by busy:");
//        sortArrayMasters = masterController.sortMasterByBusy();
//        for (Master master : sortArrayMasters) {
//            System.out.println(String.format(" -master with name \"%s\" orders: %s", master.getName(), master.getNumberOrder()));
//        }
//        System.out.println(delimiter);
//
//        System.out.println("Get orders executed concrete master.");
//        sortArrayOrders = orderController.getMasterOrders(masters[18]);
//        System.out.println(String.format(" -the master \"%s\" fulfills the orders:", masters[18].getName()));
//        PrinterOrder.printOrder(sortArrayOrders);
//        System.out.println(delimiter);
//
//        System.out.println("Get masters performing a specific order:");
//        Master[] orderMasters = orderController.getOrderMasters(orders[8]);
//        PrinterOrder.printOrder(new Order[]{orders[8]});
//        for (Master master : orderMasters) {
//            System.out.println(String.format(" - %s;", master.getName()));
//        }
//        System.out.println(delimiter);
//
//        System.out.println("Get number of free places");
//        String anyDate = "15.07.2020";
//        System.out.println(String.format("on date %s", anyDate));
//        message = carOfficeController.getFreePlacesByDate(anyDate);
//        System.out.println(message);
//        System.out.println(delimiter);
//
//        System.out.println("Get nearest free date.");
//        message = carOfficeController.getNearestFreeDate();
//        System.out.println(message);
//        System.out.println(delimiter);
//    }
//}
//
//
