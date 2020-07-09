package com.senla.carservice.ui.menu;

import com.senla.carservice.factory.annotation.Dependency;
import com.senla.carservice.controller.CarOfficeController;
import com.senla.carservice.controller.MasterController;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.controller.PlaceController;
import com.senla.carservice.ui.util.Printer;
import com.senla.carservice.ui.util.ScannerUtil;
import com.senla.carservice.ui.util.TestData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Builder {
    private Menu rootMenu;
    @Dependency
    private CarOfficeController carOfficeController;
    @Dependency
    private MasterController masterController;
    @Dependency
    private OrderController orderController;
    @Dependency
    private PlaceController placeController;

    public Builder() {
    }

    public void buildMenu() {
        this.rootMenu = new Menu("Car Service Menu");
        Menu mastersMenu = new Menu("Masters");
        Menu ordersMenu = new Menu("Orders");
        Menu placesMenu = new Menu("Places");
        Menu listOrderMenu = new Menu("Orders list");
        Menu executedOrderMenu = new Menu("list of orders currently being executed");
        Menu periodOrderMenu = new Menu("Orders list for a period of time");
        Menu completedOrderMenu = new Menu("Completed orders");
        Menu deletedOrderMenu = new Menu("Deleted orders");
        Menu canceledOrderMenu = new Menu("Canceled orders");
        setMenuItemRootMenu(mastersMenu, ordersMenu, placesMenu);
        setMenuItemRootMenuPart();
        createItemMastersMenu(mastersMenu);
        createItemPlacesMenu(placesMenu);
        createItemOrderMenu(new ArrayList<>(Arrays.asList(ordersMenu, listOrderMenu,
                                                          executedOrderMenu, periodOrderMenu)));
        createItemListOrderMenu(listOrderMenu, ordersMenu);
        createItemExecutedOrderMenu(executedOrderMenu, ordersMenu);
        createItemPeriodOrderMenu(new ArrayList<>(Arrays.asList(periodOrderMenu, completedOrderMenu,
                                                                deletedOrderMenu, canceledOrderMenu, ordersMenu)));
        createItemCompletedOrderMenu(completedOrderMenu, periodOrderMenu);
        createItemDeletedOrderMenu(deletedOrderMenu, periodOrderMenu);
        createItemCanceledOrderMenu(canceledOrderMenu, periodOrderMenu);
    }

    public Menu getRootMenu() {
        return this.rootMenu;
    }

    private void setMenuItemRootMenu(Menu mastersMenu, Menu ordersMenu, Menu placesMenu) {
        this.rootMenu.getMenuItems().add(new MenuItem
                                             ("Masters", () -> Printer.printInfo("Go to menu"), mastersMenu));
        this.rootMenu.getMenuItems().add(new MenuItem
                                             ("Orders", () -> Printer.printInfo("Go to menu"), ordersMenu));
        this.rootMenu.getMenuItems().add(new MenuItem
                                             ("Places", () -> Printer.printInfo("Go to menu"), placesMenu));
        this.rootMenu.getMenuItems().add(new MenuItem
                                             ("Get the number of available seats at the car service", () -> {
                                                 String date = ScannerUtil.getStringDateUser(
                                                     "Enter the date in format dd.mm.yyyy, example:\"10.10.2010\"",
                                                     false);
                                                 Printer
                                                     .printInfo(carOfficeController.getFreePlacesMastersByDate(date));
                                             }, this.rootMenu));
    }

    private void setMenuItemRootMenuPart() {
        this.rootMenu.getMenuItems().add(new MenuItem("Get the closest free date",
                                                      () -> Printer.printInfo(carOfficeController.getNearestFreeDate()),
                                                      this.rootMenu));
        this.rootMenu.getMenuItems().add(new MenuItem("Fill in com.senla.carservice.test data", () -> {
            String delimiter = "***********************************************************************";
            Printer.printInfo(delimiter);
            addMaster(delimiter, masterController);
            addPlace(delimiter, placeController);
            Printer.printInfo("Add new orders to car service.");
            addOrderDate(orderController);
        }, this.rootMenu));
        this.rootMenu.getMenuItems().add(
            new MenuItem("Export of all entities", () -> Printer.printInfo(carOfficeController.exportEntities()),
                         this.rootMenu));
        this.rootMenu.getMenuItems().add(
            new MenuItem("Import of all entities", () -> Printer.printInfo(carOfficeController.importEntities()),
                         this.rootMenu));
    }

    private void createItemMastersMenu(Menu mastersMenu) {
        mastersMenu.setMenuItems(new ArrayList<>(Arrays.asList(
            new MenuItem("Add master", () -> Printer.printInfo(masterController.
                addMaster(ScannerUtil.getStringUser("Enter the name of master"))), mastersMenu),
            new MenuItem("Delete Master", () -> {
                if (isCheckMaster()) {
                    return;
                }
                deleteMaster();
            }, mastersMenu),
            new MenuItem("Show a list of masters sorted alphabetically",
                         () -> Printer.printInfo(masterController.getMasterByAlphabet()), mastersMenu),
            new MenuItem("Show list of masters sorted by busy",
                         () -> Printer.printInfo(masterController.getMasterByBusy()), mastersMenu),
            new MenuItem("Previous menu", () -> Printer.printInfo("Go to menu"), this.rootMenu)
                                                              )));
    }

    private void createItemPlacesMenu(Menu placesMenu) {
        placesMenu.getMenuItems().add(new MenuItem("Show list of places",
                                                   () -> Printer.printInfo(placeController.getArrayPlace()),
                                                   placesMenu));
        placesMenu.getMenuItems().add(new MenuItem("Add place",
                                                   () -> Printer.printInfo(placeController
                                                                               .addPlace(ScannerUtil.getIntUser(
                                                                                   "Enter the number of place"))),
                                                   placesMenu));
        placesMenu.getMenuItems().add(new MenuItem("Delete place", () -> {
            if (isCheckPlace()) {
                return;
            }
            deleteGarage();
        }, placesMenu));
        placesMenu.getMenuItems().add(new MenuItem("Previous menu",
                                                   () -> Printer.printInfo("Go to menu"), this.rootMenu));
    }

    private void createItemOrderMenu(List<Menu> menus) {
        menus.get(0).setMenuItems(new ArrayList<>(Arrays.asList(
            new MenuItem("List of orders", () -> Printer.printInfo("Go to menu"), menus.get(1)),
            new MenuItem("List of orders executed at a given time",
                         () -> Printer.printInfo("Go to menu"), menus.get(2)),
            new MenuItem("List of orders for a period of time",
                         () -> Printer.printInfo("Go to menu"), menus.get(3)),
            new MenuItem("Previous menu", () -> Printer.printInfo("Go to menu"), this.rootMenu)
                                                               )));
    }

    private void createItemListOrderMenu(Menu listOrderMenu, Menu ordersMenu) {
        addItemListOrderMenuPartOne(listOrderMenu);
        addItemListOrderMenuPartTwo(listOrderMenu);
        addItemListOrderMenuPartThree(listOrderMenu);
        addItemListOrderMenuPartFour(listOrderMenu);
        addItemListOrderMenuPartFive(listOrderMenu);
        addItemListOrderMenuPartSix(listOrderMenu);
        addItemListOrderMenuPartSeven(listOrderMenu, ordersMenu);
    }

    private void addItemListOrderMenuPartOne(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(new MenuItem("Show orders", this::isCheckOrder, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Add order", this::addOrder, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Delete the order", () -> {
            if (isCheckOrder()) {
                return;
            }
            Printer.printInfo("0. Previous menu");
            deleteOrder();
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartTwo(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(new MenuItem("Close the order", () -> {
            if (isCheckOrder()) {
                return;
            }
            Printer.printInfo("0. Previous menu");
            int index;
            String message = "";
            while (!message.equals(" -the order has been completed.")) {
                index = ScannerUtil.getIntUser("Enter the index number of the order to close:");
                if (index == 0) {
                    return;
                }
                message = orderController.closeOrder(index - 1);
                Printer.printInfo(message);
            }
        }, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Cancel the order", () -> {
            if (isCheckOrder()) {
                return;
            }
            Printer.printInfo("0. Previous menu");
            cancelOrder();
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartThree(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(new MenuItem("Transfer the order to execution status", () -> {
            if (isCheckOrder()) {
                return;
            }
            Printer.printInfo("0. Previous menu");
            completeOrder();
        }, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Shift the lead time", () -> {
            if (isCheckOrder()) {
                return;
            }
            Printer.printInfo("0. Previous menu");
            String message = "";
            while (!message.equals(" -the order lead time has been changed.")) {
                int index = ScannerUtil.getIntUser("Enter the index number of the order to shift time:");
                if (index == 0) {
                    return;
                }
                message = orderController.shiftLeadTime(index - 1, ScannerUtil.getStringDateUser(
                    "Enter the planing time start to execute the order in format " +
                    "\"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"", true),
                                                        ScannerUtil.getStringDateUser(
                                                            "Enter the lead time the order in format" +
                                                            " \"dd.MM.yyyy hh:mm\"," + " example:\"10.10.2010 10:00\"",
                                                            true));
                Printer.printInfo(message);
            }
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartFour(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(new MenuItem("Show orders sort by filing date",
                                                      () -> Printer
                                                          .printInfo(orderController.getOrdersSortByFilingDate()),
                                                      listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Show orders sort by execution date",
                                                      () -> Printer
                                                          .printInfo(orderController.getOrdersSortByExecutionDate()),
                                                      listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Show orders sort by planned start date",
                                                      () -> Printer
                                                          .printInfo(orderController.getOrdersSortByPlannedStartDate()),
                                                      listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Show orders sort by price",
                                                      () -> Printer.printInfo(orderController.getOrdersSortByPrice()),
                                                      listOrderMenu));
    }

    private void addItemListOrderMenuPartFive(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(new MenuItem("Get orders executed concrete master.", () -> {
            int index;
            if (isCheckMaster()) {
                return;
            }
            index = ScannerUtil.getIntUser("Enter the index number of the master to view orders:");
            String message = orderController.getMasterOrders(index - 1);
            Printer.printInfo(message);
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartSix(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(new MenuItem("Get a master performing a specific order", () -> {
            int index;
            if (isCheckOrder()) {
                return;
            }
            index = ScannerUtil.getIntUser("Enter the index number of the order to view masters:");
            Printer.printInfo(orderController.getOrderMasters(index - 1));
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartSeven(Menu listOrderMenu, Menu ordersMenu) {
        listOrderMenu.getMenuItems().add(new MenuItem("Previous menu",
                                                      () -> Printer.printInfo("Go to menu"), ordersMenu));
    }

    private void createItemExecutedOrderMenu(Menu executedOrderMenu, Menu ordersMenu) {
        executedOrderMenu.setMenuItems(new ArrayList<>(Arrays.asList(
            new MenuItem("Get list of orders executed at a given time sort by filing date",
                         () -> Printer.printInfo(orderController.getExecuteOrderFilingDate()),
                         executedOrderMenu),
            new MenuItem("Get list of orders executed at a given time sort by execution date",
                         () -> Printer.printInfo(orderController.getExecuteOrderExecutionDate()),
                         executedOrderMenu),
            new MenuItem("Get list of orders executed at a given time sort by price",
                         () -> Printer.printInfo(orderController.getExecuteOrderExecutionDate()),
                         executedOrderMenu),
            new MenuItem("Previous menu", () -> Printer.printInfo("Go to menu"), ordersMenu)
                                                                    )));
    }

    private void createItemPeriodOrderMenu(List<Menu> menus) {
        menus.get(0).setMenuItems(new ArrayList<>(Arrays.asList(
            new MenuItem("Completed orders", () -> Printer.printInfo("Go to menu"), menus.get(1)),
            new MenuItem("Deleted orders", () -> Printer.printInfo("Go to menu"), menus.get(2)),
            new MenuItem("Canceled orders", () -> Printer.printInfo("Go to menu"), menus.get(3)),
            new MenuItem("Previous menu", () -> Printer.printInfo("Go to menu"), menus.get(4))
                                                               )));
    }

    private void createItemCompletedOrderMenu(Menu completedOrderMenu, Menu periodOrderMenu) {
        addItemCompletedOrderPartOne(completedOrderMenu);
        addItemCompletedOrderPartTwo(completedOrderMenu, periodOrderMenu);
    }

    private void addItemCompletedOrderPartOne(Menu completedOrderMenu) {
        completedOrderMenu.getMenuItems().add(new MenuItem("Sort by filing date",
                                                           () -> Printer
                                                               .printInfo(orderController.getCompletedOrdersFilingDate(
                                                                   ScannerUtil.getStringDateUser
                                                                       ("Enter the beginning date of period in format dd.mm.yyyy," +
                                                                        " example:\"10.10.2010 10:00\"", true),
                                                                   ScannerUtil.getStringDateUser
                                                                       ("Enter the end date of period in format dd.mm.yyyy," +
                                                                        " example:\"10.10.2010 10:00\"", true)
                                                                                                                      )),
                                                           completedOrderMenu));
        completedOrderMenu.getMenuItems().add(new MenuItem("Sort by execution date",
                                                           () -> Printer.printInfo(
                                                               orderController.getCompletedOrdersExecutionDate(
                                                                   ScannerUtil.getStringDateUser
                                                                       ("Enter the beginning date of period in format dd.mm.yyyy," +
                                                                        " example:\"10.10.2010 10:00\"", true),
                                                                   ScannerUtil.getStringDateUser
                                                                       ("Enter the end date of period in format dd.mm.yyyy," +
                                                                        " example:\"10.10.2010 10:00\"", true)
                                                                                                              )),
                                                           completedOrderMenu));
    }

    private void addItemCompletedOrderPartTwo(Menu completedOrderMenu, Menu periodOrderMenu) {
        completedOrderMenu.getMenuItems().add(new MenuItem("Sort by price",
                                                           () -> Printer
                                                               .printInfo(orderController.getCompletedOrdersPrice(
                                                                   ScannerUtil.getStringDateUser
                                                                       ("Enter the beginning date of period in format dd.mm.yyyy," +
                                                                        " example:\"10.10.2010 10:00\"", true),
                                                                   ScannerUtil.getStringDateUser
                                                                       ("Enter the end date of period in format dd.mm.yyyy," +
                                                                        " example:\"10.10.2010 10:00\"", true)
                                                                                                                 )),
                                                           completedOrderMenu));
        completedOrderMenu.getMenuItems().add(new MenuItem("Previous menu",
                                                           () -> Printer.printInfo("Go to menu"), periodOrderMenu));
    }

    private void createItemDeletedOrderMenu(Menu deletedOrderMenu, Menu periodOrderMenu) {
        addItemDeletedOrderPartOne(deletedOrderMenu);
        addItemDeletedOrderPartTwo(deletedOrderMenu, periodOrderMenu);
    }

    private void addItemDeletedOrderPartOne(Menu deletedOrderMenu) {
        deletedOrderMenu.getMenuItems().add(new MenuItem("Sort by filing date",
                                                         () -> Printer
                                                             .printInfo(orderController.getDeletedOrdersFilingDate(
                                                                 ScannerUtil.getStringDateUser
                                                                     ("Enter the beginning date of period in format dd.mm.yyyy," +
                                                                      " example:\"10.10.2010 10:00\"", true),
                                                                 ScannerUtil.getStringDateUser
                                                                     ("Enter the end date of period in format dd.mm.yyyy," +
                                                                      " example:\"10.10.2010 10:00\"", true)
                                                                                                                  )),
                                                         deletedOrderMenu));
        deletedOrderMenu.getMenuItems().add(new MenuItem("Sort by execution date",
                                                         () -> Printer
                                                             .printInfo(orderController.getDeletedOrdersExecutionDate(
                                                                 ScannerUtil.getStringDateUser
                                                                     ("Enter the beginning date of period in format dd.mm.yyyy," +
                                                                      " example:\"10.10.2010 10:00\"", true),
                                                                 ScannerUtil.getStringDateUser
                                                                     ("Enter the end date of period in format dd.mm.yyyy," +
                                                                      " example:\"10.10.2010 10:00\"", true)
                                                                                                                     )),
                                                         deletedOrderMenu));
    }

    private void addItemDeletedOrderPartTwo(Menu deletedOrderMenu, Menu periodOrderMenu) {
        deletedOrderMenu.getMenuItems().add(new MenuItem("Sort by price",
                                                         () -> Printer.printInfo(orderController.getDeletedOrdersPrice(
                                                             ScannerUtil.getStringDateUser
                                                                 ("Enter the beginning date of period in format dd.mm.yyyy," +
                                                                  " example:\"10.10.2010 10:00\"", true),
                                                             ScannerUtil.getStringDateUser
                                                                 ("Enter the end date of period in format dd.mm.yyyy," +
                                                                  " example:\"10.10.2010 10:00\"", true)
                                                                                                                      )),
                                                         deletedOrderMenu));
        deletedOrderMenu.getMenuItems().add(new MenuItem("Previous menu",
                                                         () -> Printer.printInfo("Go to menu"), periodOrderMenu));
    }

    private void createItemCanceledOrderMenu(Menu canceledOrderMenu, Menu periodOrderMenu) {
        addItemCanceledOrderPartOne(canceledOrderMenu);
        addItemCanceledOrderPartTwo(canceledOrderMenu, periodOrderMenu);
    }

    private void addItemCanceledOrderPartOne(Menu canceledOrderMenu) {
        canceledOrderMenu.getMenuItems().add(new MenuItem("Sort by filing date",
                                                          () -> Printer
                                                              .printInfo(orderController.getCanceledOrdersFilingDate(
                                                                  ScannerUtil.getStringDateUser
                                                                      ("Enter the beginning date of period in format dd.mm.yyyy," +
                                                                       " example:\"10.10.2010 10:00\"", true),
                                                                  ScannerUtil.getStringDateUser
                                                                      ("Enter the end date of period in format dd.mm.yyyy," +
                                                                       " example:\"10.10.2010 10:00\"", true)
                                                                                                                    )),
                                                          canceledOrderMenu));
        canceledOrderMenu.getMenuItems().add(new MenuItem("Sort by execution date",
                                                          () -> Printer
                                                              .printInfo(orderController.getCanceledOrdersExecutionDate(
                                                                  ScannerUtil.getStringDateUser
                                                                      ("Enter the beginning date of period in format dd.mm.yyyy," +
                                                                       " example:\"10.10.2010 10:00\"", true),
                                                                  ScannerUtil.getStringDateUser
                                                                      ("Enter the end date of period in format dd.mm.yyyy," +
                                                                       " example:\"10.10.2010 10:00\"", true)
                                                                                                                       )),
                                                          canceledOrderMenu));
    }

    private void addItemCanceledOrderPartTwo(Menu canceledOrderMenu, Menu periodOrderMenu) {
        canceledOrderMenu.getMenuItems().add(new MenuItem("Sort by price",
                                                          () -> Printer
                                                              .printInfo(orderController.getCanceledOrdersPrice(
                                                                  ScannerUtil.getStringDateUser
                                                                      ("Enter the beginning date of period in " +
                                                                       "format dd.mm.yyyy, example:\"10.10.2010 10:00\"",
                                                                       true),
                                                                  ScannerUtil.getStringDateUser
                                                                      ("Enter the end date of period in format dd.mm.yyyy, " +
                                                                       "example:\"10.10.2010 10:00\"", true)
                                                                                                               )),
                                                          canceledOrderMenu));
        canceledOrderMenu.getMenuItems().add(new MenuItem("Previous menu",
                                                          () -> Printer.printInfo("Go to menu"), periodOrderMenu));
    }

    private void addMaster(String delimiter, MasterController masterController) {
        Printer.printInfo("Add master:");
        for (String masterName : TestData.getArrayMasterNames()) {
            Printer.printInfo(masterController.addMaster(masterName));
        }
        Printer.printInfo(delimiter);
    }

    private void addPlace(String delimiter, PlaceController placeController) {
        Printer.printInfo("Add place to service:");
        for (int placeNumber : TestData.getArrayPlaceNumber()) {
            Printer.printInfo(placeController.addPlace(placeNumber));
        }
        Printer.printInfo(delimiter);
    }

    private void addOrderDate(OrderController orderController) {
        int indexMaster = 0;
        for (int i = 0; i < TestData.getArrayAutomaker().size(); i++) {
            Printer.printInfo(orderController.addOrder(TestData.getArrayAutomaker().get(i),
                                                       TestData.getArrayModel().get(i),
                                                       TestData.getArrayRegistrationNumber().get(i)));
            for (int j = 0; j < 2; j++) {
                Printer.printInfo(orderController.addOrderMasters(indexMaster));
                indexMaster++;
                if (indexMaster == TestData.getArrayMasterNames().size()) {
                    indexMaster = 0;
                }
            }
            Printer.printInfo(orderController.addOrderDeadlines(TestData.getArrayExecutionStartTime().get(i),
                                                                TestData.getArrayLeadTime().get(i)));
            Printer.printInfo(orderController.addOrderPlace(0, TestData.getArrayExecutionStartTime().get(i)));
            Printer.printInfo(orderController.addOrderPrice(TestData.getArrayPrice().get(i)));
        }
    }

    private void deleteOrder() {
        String message = "";
        int index;
        while (!message.equals(" -the order has been deleted.")) {
            index = ScannerUtil.getIntUser("Enter the index number of the order to delete:");
            if (index == 0) {
                return;
            }
            message = orderController.deleteOrder(index - 1);
            Printer.printInfo(message);
        }
    }

    private void deleteMaster() {
        int index = ScannerUtil.getIntUser("Enter the index number of the master to delete:");
        Printer.printInfo(masterController.deleteMaster(index - 1));
    }

    private void deleteGarage() {
        int index = ScannerUtil.getIntUser("Enter the index number of the place to delete:");
        Printer.printInfo(placeController.deletePlace(index - 1));
    }

    private void completeOrder() {
        String message = "";
        int index;
        while (!message.equals(" - the order has been transferred to execution status")) {
            index = ScannerUtil.getIntUser("Enter the index number of the order to change status:");
            if (index == 0) {
                return;
            }
            message = orderController.completeOrder(index - 1);
            Printer.printInfo(message);
        }
    }

    private void cancelOrder() {
        String message = "";
        int index;
        while (!message.equals(" -the order has been canceled.")) {
            index = ScannerUtil.getIntUser("Enter the index number of the order to cancel:");
            if (index == 0) {
                return;
            }
            message = orderController.cancelOrder(index - 1);
            Printer.printInfo(message);
        }
    }

    private void addOrder() {
        String message = "";
        while (!message.equals("order add successfully!")) {
            if (isCheckMaster() || isCheckPlace()) {
                return;
            }
            String automaker = ScannerUtil.getStringUser("Enter the automaker of car");
            String model = ScannerUtil.getStringUser("Enter the model of car");
            String registrationNumber = ScannerUtil.getStringUser
                ("Enter the registration number of car, example: 1111 AB-7");
            message = orderController.addOrder(automaker, model, registrationNumber);
            Printer.printInfo(message);
        }
        String executionStartTime = addOrderDeadline();
        addMastersOrder(executionStartTime);
        addPlaceOrder(executionStartTime);
        Printer.printInfo(orderController.addOrderPrice(ScannerUtil.getBigDecimalUser("Enter the price")));
    }

    private String addOrderDeadline() {
        String message = "";
        String leadTime;
        String executionStartTime = "";
        while (!message.equals("deadline add to order successfully")) {
            executionStartTime = ScannerUtil.getStringDateUser(
                "Enter the planing time start to execute the order in " +
                "format \"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"", true);
            leadTime = ScannerUtil.getStringDateUser(
                "Enter the lead time the order in format " +
                "\"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"", true);
            message = orderController.addOrderDeadlines(executionStartTime, leadTime);
            Printer.printInfo(message);
        }
        return executionStartTime;
    }

    private void addMastersOrder(String executionStartTime) {
        Printer.printInfo(masterController.getFreeMasters(executionStartTime));
        Printer.printInfo("0. Stop adding");
        int quit = 0;
        int index = 999;
        while (quit == 0 && index == 999) {
            List<Integer> statusInt = addMasters(quit);
            quit = statusInt.get(0);
            index = statusInt.get(1);
            Printer.printInfo("You must add at least one master!!!");
        }
    }

    private List<Integer> addMasters(int quit) {
        String message = "";
        int index = 999;
        boolean userAnswer;
        while (!message.equals("masters add successfully")) {
            index = ScannerUtil.getIntUser("Enter the index number of the master to add:");
            if (index == 0) {
                break;
            }
            message = orderController.addOrderMasters(index - 1);
            Printer.printInfo(message);
            userAnswer = ScannerUtil.isAnotherMaster();
            if (userAnswer) {
                index = 0;
                break;
            }
        }
        if (index != 0) {
            quit++;
        }
        return new ArrayList<>(Arrays.asList(quit, index));
    }

    private void addPlaceOrder(String executionStartTime) {
        String message = "";
        int index;
        Printer.printInfo(placeController.getFreePlacesByDate(executionStartTime));
        Printer.printInfo("0. Stop adding");
        while (!message.equals("place add to order successfully")) {
            index = ScannerUtil.getIntUser("Enter the index number of the place to add in order:");
            message = orderController.addOrderPlace(index - 1, executionStartTime);
            Printer.printInfo(message);
        }
    }

    private boolean isCheckOrder() {
        String message = orderController.getOrders();
        Printer.printInfo(message);
        return message.equals("There are no orders");
    }

    private boolean isCheckMaster() {
        String message = masterController.getMasters();
        Printer.printInfo(message);
        return message.equals("There are no masters");
    }

    private boolean isCheckPlace() {
        String message = placeController.getArrayPlace();
        Printer.printInfo(message);
        return message.equals("There are no places");
    }
}