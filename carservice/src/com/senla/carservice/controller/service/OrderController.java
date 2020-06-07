package com.senla.carservice.controller.service;

import com.senla.carservice.controller.data.Data;
import com.senla.carservice.controller.util.Printer;
import com.senla.carservice.domain.IMaster;
import com.senla.carservice.domain.IOrder;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.services.IAdministrator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class OrderController {
    private IAdministrator carService;
    private Data data;
    private Printer printer;

    public OrderController(IAdministrator carService, Data data, Printer printer) {
        this.carService = carService;
        this.data = data;
        this.printer = printer;
    }

    public void addOrder() {
        System.out.println("Add new orders to car service:");
        int indexMaster = 0;
        int indexGarage = 0;
        int indexPlace = 0;
        for (int i = 0; i < this.data.getArrayAutomaker().length; i++) {
            IMaster[] mastersOrder = new Master[2];
            for (int j = 0; j < 2; j++) {
                mastersOrder[j] = this.carService.getMasters()[indexMaster];
                indexMaster++;
                if (indexMaster == this.carService.getMasters().length - 1) {
                    indexMaster = 0;
                }
            }
            this.carService.addOrder(this.data.getArrayExecutionStartTime()[i], this.data.getArrayLeadTime()[i],
                    mastersOrder, this.carService.getGarage()[indexGarage],
                    this.carService.getGarage()[indexGarage].getPlaces()[indexPlace],
                    this.data.getArrayAutomaker()[i], this.data.getArrayModel()[i],
                    this.data.getArrayRegistrationNumber()[i], this.data.getArrayPrice()[i]);
            indexPlace++;
            if (indexPlace == 3) {
                indexPlace = 0;
                indexGarage++;
            }
        }
        this.printer.printOrder(this.carService.getOrders());
        System.out.println("******************************************************************************************");
    }

    public void completeOrder() {
        boolean status_operation;
        IOrder[] orders = new Order[5];
        orders[0] = this.carService.getOrders()[0];
        orders[1] = this.carService.getOrders()[1];
        orders[2] = this.carService.getOrders()[7];
        orders[3] = this.carService.getOrders()[8];
        orders[4] = this.carService.getOrders()[9];
        System.out.println("Transfer an order to execution status:");
        for (IOrder order : orders) {
            status_operation = this.carService.completeOrder(order);
            if (status_operation) {
                System.out.println(" -the order has been transferred to execution status.");
                this.printer.printOrder(new IOrder[]{order});
            } else {
                System.out.println(" -the order is deleted.");
                this.printer.printOrder(new IOrder[]{order});
            }
        }
        System.out.println("******************************************************************************************");
    }

    public void deleteOrder() {
        boolean status_operation;
        IOrder[] orders = new Order[2];
        orders[0] = this.carService.getOrders()[0];
        orders[1] = this.carService.getOrders()[10];
        System.out.println("Delete order:");
        for (IOrder order : orders) {
            status_operation = this.carService.deleteOrder(order);
            if (status_operation) {
                System.out.println(" -the order has been deleted.");
                this.printer.printOrder(new IOrder[]{order});
            } else {
                System.out.println(" -the order is on a mission.");
                this.printer.printOrder(new IOrder[]{order});
            }
        }
        System.out.println("******************************************************************************************");
    }

    public void closeOrder() {
        boolean status_operation;
        IOrder[] orders = new Order[3];
        orders[0] = this.carService.getOrders()[0];
        orders[1] = this.carService.getOrders()[1];
        orders[2] = this.carService.getOrders()[9];
        System.out.println("Close order:");
        for (IOrder order : orders) {
            status_operation = this.carService.closeOrder(order);
            if (status_operation) {
                System.out.println(" -the order has been completed.");
            } else {
                System.out.println(" -the order is deleted.");
            }
            this.printer.printOrder(new IOrder[]{order});
        }
        System.out.println("******************************************************************************************");
    }

    public void cancelOrder() {
        boolean status_operation;
        IOrder[] orders = new Order[3];
        orders[0] = this.carService.getOrders()[2];
        orders[1] = this.carService.getOrders()[10];
        orders[2] = this.carService.getOrders()[11];
        System.out.println("Cancel order:");
        for (IOrder order : orders) {
            status_operation = this.carService.cancelOrder(order);
            if (status_operation) {
                System.out.println(" -the order has been canceled.");
                this.printer.printOrder(new IOrder[]{order});
            } else {
                System.out.println(" -the order is deleted.");
                this.printer.printOrder(new IOrder[]{order});
            }
        }
        System.out.println("******************************************************************************************");
    }

    public void shiftLeadTime() {
        boolean status_operation;
        IOrder order = this.carService.getOrders()[3];
        Calendar executionStartTime = new GregorianCalendar(2020, Calendar.JULY, 14, 10, 0);
        Calendar leadTime = new GregorianCalendar(2020, Calendar.JULY, 15, 10, 0);
        System.out.println("Shift lead time:");
        status_operation = this.carService.shiftLeadTime(order, executionStartTime, leadTime);
        if (status_operation) {
            System.out.println(" -the order lead time has been changed.");
            this.printer.printOrder(new IOrder[]{order});
        } else {
            System.out.println(" -the order is deleted.");
            this.printer.printOrder(new IOrder[]{order});
        }
        System.out.println("******************************************************************************************");
    }

    public void getOrderByCreationTime() {
        IOrder[] sortArrayOrder = this.carService.getOrders();
        System.out.println("Get oder sort by creation time:");
        sortArrayOrder = this.carService.sortOrderCreationTime(sortArrayOrder);
        this.printer.printOrder(sortArrayOrder);
        System.out.println("******************************************************************************************");
    }

    public void getOrderByLeadTime() {
        IOrder[] sortArrayOrder = this.carService.getOrders();
        System.out.println("Get oder sort by lead time:");
        sortArrayOrder = this.carService.sortOrderByLeadTime(sortArrayOrder);
        this.printer.printOrder(sortArrayOrder);
        System.out.println("******************************************************************************************");
    }

    public void getOrderByStartTime() {
        IOrder[] sortArrayOrder = this.carService.getOrders();
        System.out.println("Get oder sort by execution start time.");
        sortArrayOrder = this.carService.sortOrderByStartTime(sortArrayOrder);
        this.printer.printOrder(sortArrayOrder);
        System.out.println("******************************************************************************************");
    }

    public void getOrderByPrice() {
        IOrder[] sortArrayOrder = this.carService.getOrders();
        System.out.println("Get oder sort by price:");
        sortArrayOrder = this.carService.sortOrderByPrice(sortArrayOrder);
        this.printer.printOrder(sortArrayOrder);
        System.out.println("******************************************************************************************");
    }

    public void getExecutedOrderByCreationTime() {
        IOrder[] sortArrayOrder = this.carService.getCurrentRunningOrders();
        System.out.println("Get oder is being executed sort by creation time:");
        sortArrayOrder = this.carService.sortOrderCreationTime(sortArrayOrder);
        this.printer.printOrder(sortArrayOrder);
        System.out.println("******************************************************************************************");
    }

    public void getExecutedOrderByLeadTime() {
        IOrder[] sortArrayOrder = this.carService.getCurrentRunningOrders();
        System.out.println("Get oders are being executed sort by lead time:");
        sortArrayOrder = this.carService.sortOrderByLeadTime(sortArrayOrder);
        this.printer.printOrder(sortArrayOrder);
        System.out.println("******************************************************************************************");
    }

    public void getExecutedOrderByPrice() {
        IOrder[] sortArrayOrder = this.carService.getCurrentRunningOrders();
        System.out.println("Get oders are being executed sort by price:");
        sortArrayOrder = this.carService.sortOrderByPrice(sortArrayOrder);
        this.printer.printOrder(sortArrayOrder);
        System.out.println("******************************************************************************************");
    }

    public void getMasterOrder() {
        IMaster master = this.carService.getMasters()[18];
        System.out.println("Get order executed concrete master:");
        IOrder[] orders = this.carService.getMasterOrders(master);
        System.out.println(String.format(" -the master \"%s\" fulfills the order:", master.getName()));
        this.printer.printOrder(new IOrder[]{orders[0]});
        System.out.println("******************************************************************************************");
    }

    public void getOrderMasters() {
        IOrder order = this.carService.getOrders()[8];
        System.out.println("Get masters performing a specific order:");
        IMaster[] masters = this.carService.getOrderMasters(order);
        for (IMaster master : masters) {
            System.out.println(String.format(" - %s;", master.getName()));
        }
        this.printer.printOrder(new IOrder[]{order});
        System.out.println("******************************************************************************************");
    }

    public void getCompletedOrders() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
        Calendar startPeriod = new GregorianCalendar();
        startPeriod.add(Calendar.DAY_OF_MONTH, -1);
        Calendar endPeriod = new GregorianCalendar();
        endPeriod.add(Calendar.DAY_OF_MONTH, 1);
        IOrder[] completedOrders = this.carService.getCompletedOrders();
        completedOrders = this.carService.sortOrderByPeriod(completedOrders, startPeriod, endPeriod);
        System.out.println(String.format("Completed orders received for a period of time: %s - %s:",
                dateFormat.format(startPeriod.getTime()), dateFormat.format(endPeriod.getTime())));
        this.printer.printOrder(completedOrders);
        System.out.println("sorted by filing date:");
        completedOrders = this.carService.sortOrderCreationTime(completedOrders);
        this.printer.printOrder(completedOrders);
        System.out.println("sorted by execution date:");
        completedOrders = this.carService.sortOrderByStartTime(completedOrders);
        this.printer.printOrder(completedOrders);
        System.out.println("sorted by price:");
        completedOrders = this.carService.sortOrderByPrice(completedOrders);
        this.printer.printOrder(completedOrders);
        System.out.println("******************************************************************************************");
    }

    public void getCanceledOrders() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
        Calendar startPeriod = new GregorianCalendar();
        startPeriod.add(Calendar.DAY_OF_MONTH, -1);
        Calendar endPeriod = new GregorianCalendar();
        endPeriod.add(Calendar.DAY_OF_MONTH, 1);
        IOrder[] rejectedOrders = this.carService.getCanceledOrders();
        rejectedOrders = this.carService.sortOrderByPeriod(rejectedOrders, startPeriod, endPeriod);
        System.out.println(String.format("Rejected orders received for a period of time: %s - %s:",
                dateFormat.format(startPeriod.getTime()), dateFormat.format(endPeriod.getTime())));
        this.printer.printOrder(rejectedOrders);
        System.out.println("sorted by filing date:");
        rejectedOrders = this.carService.sortOrderCreationTime(rejectedOrders);
        this.printer.printOrder(rejectedOrders);
        System.out.println("sorted by execution date:");
        rejectedOrders = this.carService.sortOrderByStartTime(rejectedOrders);
        this.printer.printOrder(rejectedOrders);
        System.out.println("sorted by price:");
        rejectedOrders = this.carService.sortOrderByPrice(rejectedOrders);
        this.printer.printOrder(rejectedOrders);
        System.out.println("******************************************************************************************");
    }

    public void getDeletedOrders() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
        Calendar startPeriod = new GregorianCalendar();
        startPeriod.add(Calendar.DAY_OF_MONTH, -1);
        Calendar endPeriod = new GregorianCalendar();
        endPeriod.add(Calendar.DAY_OF_MONTH, 1);
        IOrder[] deletedOrders = this.carService.getDeletedOrders();
        deletedOrders = this.carService.sortOrderByPeriod(deletedOrders, startPeriod, endPeriod);
        System.out.println(String.format("Deleted orders received for a period of time: %s - %s:",
                dateFormat.format(startPeriod.getTime()), dateFormat.format(endPeriod.getTime())));
        this.printer.printOrder(deletedOrders);
        System.out.println("sorted by filing date:");
        deletedOrders = this.carService.sortOrderCreationTime(deletedOrders);
        this.printer.printOrder(deletedOrders);
        System.out.println("sorted by execution date:");
        deletedOrders = this.carService.sortOrderByStartTime(deletedOrders);
        this.printer.printOrder(deletedOrders);
        System.out.println("sorted by price:");
        deletedOrders = this.carService.sortOrderByPrice(deletedOrders);
        this.printer.printOrder(deletedOrders);
        System.out.println("******************************************************************************************");
    }
}