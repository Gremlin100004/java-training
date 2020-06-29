package com.senla.carservice.ui.menu;

import com.senla.carservice.controller.CarOfficeController;
import com.senla.carservice.controller.PlaceController;
import com.senla.carservice.controller.MasterController;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.ui.string.StringMaster;
import com.senla.carservice.ui.string.StringOrder;
import com.senla.carservice.ui.util.Printer;
import com.senla.carservice.ui.util.ScannerUtil;
import com.senla.carservice.ui.util.TestData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Builder {
    private static Builder instance;
    private Menu rootMenu;

    private Builder() {
    }

    public static Builder getInstance() {
        if (instance == null) {
            instance = new Builder();
        }
        return instance;
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
        createItemPlacesMenu(placesMenu, this.rootMenu);
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
                    String date = ScannerUtil.getStringDateUser
                            ("Enter the date in format dd.mm.yyyy, example:\"10.10.2010\"");
                    Printer.printInfo(CarOfficeController.getInstance().getFreePlacesByDate(date));
                }, this.rootMenu));
    }

    private void setMenuItemRootMenuPart() {
        this.rootMenu.getMenuItems().add(new MenuItem("Get the closest free date",
                () -> Printer.printInfo(CarOfficeController.getInstance().getNearestFreeDate()), this.rootMenu));
        this.rootMenu.getMenuItems().add(new MenuItem("Fill in test data", () -> {
            String delimiter = "***********************************************************************";
            Printer.printInfo(delimiter);
            addMaster(delimiter, MasterController.getInstance());
            addPlace(delimiter, PlaceController.getInstance());
            Printer.printInfo("Add new orders to car service.");
            addOrder(MasterController.getInstance(), OrderController.getInstance(), PlaceController.getInstance());
        }, this.rootMenu));
//        this.rootMenu.getMenuItems().add(new MenuItem("Export of all entities", () -> {
//            Printer.printInfo(MasterController.getInstance().exportMasters());
//            Printer.printInfo(PlaceController.getInstance().exportGarages());
//            Printer.printInfo(OrderController.getInstance().exportOrders());
//        }, this.rootMenu));
//        this.rootMenu.getMenuItems().add(new MenuItem("Import of all entities", () -> {
//            Printer.printInfo(MasterController.getInstance().importMasters());
//            Printer.printInfo(PlaceController.getInstance().importGarages());
//            Printer.printInfo(OrderController.getInstance().importOrders());
//        }, this.rootMenu));
    }

    private void createItemMastersMenu(Menu mastersMenu) {
        mastersMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Add master", () -> Printer.printInfo(MasterController.getInstance().
                        addMaster(ScannerUtil.getStringUser("Enter the name of master"))), mastersMenu),
                new MenuItem("Delete Master", () -> {
                    Printer.printInfo(MasterController.getInstance().getMasters());
                    deleteMaster();
                }, mastersMenu),
                new MenuItem("Show a list of masters sorted alphabetically", () -> {
                    Printer.printInfo(MasterController.getInstance().getMasterByAlphabet());
                }, mastersMenu),
                new MenuItem("Show list of masters sorted by busy", () -> {
                    Printer.printInfo(MasterController.getInstance().getMasterByBusy());
                }, mastersMenu),
//                new MenuItem("Export masters",
//                        () -> Printer.printInfo(MasterController.getInstance().exportMasters()), mastersMenu),
//                new MenuItem("Import masters",
//                        () -> Printer.printInfo(MasterController.getInstance().importMasters()), mastersMenu),
                new MenuItem("Previous menu", () -> Printer.printInfo("Go to menu"), this.rootMenu)
        )));
    }

    private void createItemPlacesMenu(Menu placesMenu, Menu rootMenu) {
        placesMenu.getMenuItems().add(new MenuItem("Show list of places",
                () -> Printer.printInfo(PlaceController.getInstance().getArrayPlace()), placesMenu));
        placesMenu.getMenuItems().add(new MenuItem("Add place",
                () -> Printer.printInfo(PlaceController.getInstance()
                        .addPlace(ScannerUtil.getIntUser("Enter the number of place"))), placesMenu));
        placesMenu.getMenuItems().add(new MenuItem("Delete place", () -> {
            Printer.printInfo(PlaceController.getInstance().getArrayPlace());
            deleteGarage();
        }, placesMenu));
    }

    private void createItemOrderMenu(List<Menu> menus) {
        menus.get(0).setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("List of orders", () -> Printer.printInfo("Go to menu"), menus.get(1)),
                new MenuItem("List of orders executed at a given time",
                        () -> Printer.printInfo("Go to menu"), menus.get(2)),
                new MenuItem("List of orders for a period of time",
                        () -> Printer.printInfo("Go to menu"), menus.get(3)),
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
        listOrderMenu.getMenuItems().add(new MenuItem("Show orders", () -> {
            Printer.printInfo(OrderController.getInstance().getOrders());
        }, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Add order", this::addOrder, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Delete the order", () -> {
            Printer.printInfo(OrderController.getInstance().getOrders());
            Printer.printInfo("0. Previous menu");
            deleteOrder();
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartTwo(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(new MenuItem("Close the order", () -> {
            Printer.printInfo(OrderController.getInstance().getOrders());
            Printer.printInfo("0. Previous menu");
            String message = "";
            int index;
            while (!message.equals(" -the order has been completed.")) {
                index = ScannerUtil.getIntUser("Enter the index number of the order to close:");
                if (index == 0) {
                    return;
                }
                message = OrderController.getInstance()
                        .closeOrder(index - 1);
                Printer.printInfo(message);
            }
        }, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Cancel the order", () -> {
            Printer.printInfo(OrderController.getInstance().getOrders());
            Printer.printInfo("0. Previous menu");
            cancelOrder();
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartThree(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(new MenuItem("Transfer the order to execution status", () -> {
            Printer.printInfo(OrderController.getInstance().getOrders());
            Printer.printInfo("0. Previous menu");
            completeOrder();
        }, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Shift the lead time", () -> {
            String message = "";
            while (!message.equals("There are no orders")) {
                message = OrderController.getInstance().getOrders();
                Printer.printInfo(message);
            }
            Printer.printInfo("0. Previous menu");
            while (!message.equals(" -the order lead time has been changed.")) {
                int index = ScannerUtil.getIntUser("Enter the index number of the order to shift time:");
                if (index == 0){
                    return;
                }
                message = OrderController.getInstance().shiftLeadTime(index - 1, ScannerUtil.getStringDateUser(
                        "Enter the planing time start to execute the order in format " +
                                "\"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\""),
                        ScannerUtil.getStringDateUser("Enter the lead time the order in format" +
                                " \"dd.MM.yyyy hh:mm\"," + " example:\"10.10.2010 10:00\""));
                Printer.printInfo(message);
            }
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartFour(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(new MenuItem("Show orders sort by filing date",
                () -> Printer.printInfo(OrderController.getInstance().getOrdersSortByFilingDate()), listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Show orders sort by execution date",
                () -> Printer.printInfo(OrderController.getInstance().getOrdersSortByExecutionDate()), listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Show orders sort by planned start date", () -> {
            Printer.printInfo(OrderController.getInstance().getOrdersSortByPlannedStartDate());
        }, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Show orders sort by price", () -> {
            List<Order> orders = OrderController.getInstance().getOrders();
            if (orders.isEmpty()) {
                Printer.printInfo("There are no orders.");
                return;
            }
            List<Order> sortArrayOrders = OrderController.getInstance().sortOrderByPrice(orders);
            StringOrder.getStringFromOrder(sortArrayOrders);
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartFive(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(new MenuItem("Get orders executed concrete master.", () -> {
            int index;
            if (MasterController.getInstance().getMasters().isEmpty()) {
                Printer.printInfo("There are no masters.");
                return;
            }
            StringMaster.getStringFromMasters(MasterController.getInstance().getMasters());
            Printer.printInfo("0. Previous menu");
            List<Order> orders = new ArrayList<>();
            while (orders.isEmpty()) {
                index = ScannerUtil.getIntUser("Enter the index number of the master to view orders:");
                if (index == 0) {
                    return;
                }
                if (index > MasterController.getInstance().getMasters().size() || index < 0) {
                    Printer.printInfo("There is no such master");
                    continue;
                }
                orders = OrderController.getInstance()
                        .getMasterOrders(MasterController.getInstance().getMasters().get(index - 1));
                if (OrderController.getInstance()
                        .getMasterOrders(MasterController.getInstance().getMasters().get(index - 1)).isEmpty()) {
                    Printer.printInfo("Such master has no orders!");
                    continue;
                }
                StringOrder.getStringFromOrder(OrderController
                        .getInstance().getMasterOrders(MasterController.getInstance().getMasters().get(index - 1)));
                break;
            }
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartSix(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(new MenuItem("Get a master performing a specific order", () -> {
            int index;
            if (OrderController.getInstance().getOrders().isEmpty()) {
                Printer.printInfo("There are no orders.");
                return;
            }
            StringOrder.getStringFromOrder(OrderController.getInstance().getOrders());
            Printer.printInfo("0. Previous menu");
            List<Master> masters = new ArrayList<>();
            while (masters.isEmpty()) {
                index = ScannerUtil.getIntUser("Enter the index number of the order to view masters:");
                if (index == 0) {
                    return;
                }
                if (index > OrderController.getInstance().getOrders().size() || index < 0) {
                    Printer.printInfo("There is no such order");
                    continue;
                }
                masters = OrderController.getInstance().getOrderMasters
                        (OrderController.getInstance().getOrders().get(index - 1));
                StringMaster.getStringFromMasters(masters);
            }
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartSeven(Menu listOrderMenu, Menu ordersMenu) {
        listOrderMenu.getMenuItems().add(new MenuItem("Export orders", () -> {
            OrderController orderController = OrderController.getInstance();
            if (isContinue()) {
                Printer.printInfo(orderController.exportOrders());
            }
        }, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Import orders",
                () -> Printer.printInfo(OrderController.getInstance().importOrders()), listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Previous menu",
                () -> Printer.printInfo("Go to menu"), ordersMenu));
    }

    private void createItemExecutedOrderMenu(Menu executedOrderMenu, Menu ordersMenu) {
        executedOrderMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Get list of orders executed at a given time sort by filing date", () -> {
                    List<Order> executedOrders = OrderController.getInstance().
                            sortOrderByCreationTime(OrderController.getInstance().getExecuteOrder());
                    if (executedOrders.isEmpty()) {
                        Printer.printInfo("There are no orders!");
                        return;
                    }
                    StringOrder.getStringFromOrder(executedOrders);
                }, executedOrderMenu),
                new MenuItem("Get list of orders executed at a given time sort by execution date",
                        () -> {
                            List<Order> executedOrders = OrderController
                                    .getInstance().sortOrderByLeadTime(OrderController.getInstance().getExecuteOrder());
                            if (executedOrders.isEmpty()) {
                                Printer.printInfo("There are no orders!");
                                return;
                            }
                            StringOrder.getStringFromOrder(executedOrders);
                        }, executedOrderMenu),
                new MenuItem("Get list of orders executed at a given time sort by price", () -> {
                    List<Order> executedOrders = OrderController.getInstance().
                            sortOrderByPrice(OrderController.getInstance().getExecuteOrder());
                    if (executedOrders.isEmpty()) {
                        Printer.printInfo("There are no orders!");
                        return;
                    }
                    StringOrder.getStringFromOrder(executedOrders);
                }, executedOrderMenu),
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
        addItemCompletedOrderPartOne(completedOrderMenu, periodOrderMenu);
        addItemCompletedOrderPartTwo(completedOrderMenu, periodOrderMenu);
    }

    private void addItemCompletedOrderPartOne(Menu completedOrderMenu, Menu periodOrderMenu) {
        completedOrderMenu.getMenuItems().add(new MenuItem("Sort by filing date", () -> {
            List<Order> orders = new ArrayList<>();
            while (orders.isEmpty()) {
                orders = getSortPeriodOrders();
                if (orders.isEmpty()) {
                    return;
                }
                orders = OrderController.getInstance().getCompletedOrders(orders);
                if (orders.isEmpty()) {
                    Printer.printInfo("There are no completed orders for this period of time!");
                    return;
                }
                orders = OrderController.getInstance().sortOrderByCreationTime(orders);
                StringOrder.getStringFromOrder(orders);
            }
        }, completedOrderMenu));
        completedOrderMenu.getMenuItems().add(new MenuItem("Sort by execution date", () -> {
            List<Order> orders = new ArrayList<>();
            while (orders.isEmpty()) {
                orders = getSortPeriodOrders();
                if (orders.isEmpty()) {
                    return;
                }
                orders = OrderController.getInstance().getCompletedOrders(orders);
                if (orders.isEmpty()) {
                    Printer.printInfo("There are no completed orders for this period of time!");
                    return;
                }
                orders = OrderController.getInstance().sortOrderByStartTime(orders);
                StringOrder.getStringFromOrder(orders);
            }
        }, completedOrderMenu));
    }

    private void addItemCompletedOrderPartTwo(Menu completedOrderMenu, Menu periodOrderMenu) {
        completedOrderMenu.getMenuItems().add(new MenuItem("Sort by price", () -> {
            List<Order> orders = new ArrayList<>();
            while (orders.isEmpty()) {
                orders = getSortPeriodOrders();
                if (orders.isEmpty()) {
                    return;
                }
                orders = OrderController.getInstance().getCompletedOrders(orders);
                if (orders.isEmpty()) {
                    Printer.printInfo("There are no completed orders for this period of time!");
                    return;
                }
                orders = OrderController.getInstance().sortOrderByPrice(orders);
                StringOrder.getStringFromOrder(orders);
            }
        }, completedOrderMenu));
        completedOrderMenu.getMenuItems().add(new MenuItem("Previous menu",
                () -> Printer.printInfo("Go to menu"), periodOrderMenu));
    }

    private void createItemDeletedOrderMenu(Menu deletedOrderMenu, Menu periodOrderMenu) {
        addItemDeletedOrderPartOne(deletedOrderMenu, periodOrderMenu);
        addItemDeletedOrderPartTwo(deletedOrderMenu, periodOrderMenu);
    }

    private void addItemDeletedOrderPartOne(Menu deletedOrderMenu, Menu periodOrderMenu) {
        deletedOrderMenu.getMenuItems().add(new MenuItem("Sort by filing date", () -> {
            List<Order> orders = new ArrayList<>();
            while (orders.isEmpty()) {
                orders = getSortPeriodOrders();
                if (orders.isEmpty()) {
                    return;
                }
                orders = OrderController.getInstance().getDeletedOrders(orders);
                if (orders.isEmpty()) {
                    Printer.printInfo("There are no deleted orders for this period of time!");
                    return;
                }
                orders = OrderController.getInstance().sortOrderByCreationTime(orders);
                StringOrder.getStringFromOrder(orders);
            }
        }, deletedOrderMenu));
        deletedOrderMenu.getMenuItems().add(new MenuItem("Sort by execution date", () -> {
            List<Order> orders = new ArrayList<>();
            while (orders.isEmpty()) {
                orders = getSortPeriodOrders();
                if (orders.isEmpty()) {
                    return;
                }
                orders = OrderController.getInstance().getDeletedOrders(orders);
                if (orders.isEmpty()) {
                    Printer.printInfo("There are no deleted orders for this period of time!");
                    return;
                }
                orders = OrderController.getInstance().sortOrderByStartTime(orders);
                StringOrder.getStringFromOrder(orders);
            }
        }, deletedOrderMenu));
    }

    private void addItemDeletedOrderPartTwo(Menu deletedOrderMenu, Menu periodOrderMenu) {
        deletedOrderMenu.getMenuItems().add(new MenuItem("Sort by price", () -> {
            List<Order> orders = new ArrayList<>();
            while (orders.isEmpty()) {
                orders = getSortPeriodOrders();
                if (orders.isEmpty()) {
                    return;
                }
                orders = OrderController.getInstance().getDeletedOrders(orders);
                if (orders.isEmpty()) {
                    Printer.printInfo("There are no deleted orders for this period of time!");
                    return;
                }
                orders = OrderController.getInstance().sortOrderByPrice(orders);
                StringOrder.getStringFromOrder(orders);
            }
        }, deletedOrderMenu));
        deletedOrderMenu.getMenuItems().add(new MenuItem("Previous menu",
                () -> Printer.printInfo("Go to menu"), periodOrderMenu));
    }

    private void createItemCanceledOrderMenu(Menu canceledOrderMenu, Menu periodOrderMenu) {
        addItemCanceledOrderPartOne(canceledOrderMenu, periodOrderMenu);
        addItemCanceledOrderPartTwo(canceledOrderMenu, periodOrderMenu);
    }

    private void addItemCanceledOrderPartOne(Menu canceledOrderMenu, Menu periodOrderMenu) {
        canceledOrderMenu.getMenuItems().add(new MenuItem("Sort by filing date", () -> {
            List<Order> orders = new ArrayList<>();
            while (orders.isEmpty()) {
                orders = getSortPeriodOrders();
                if (orders.isEmpty()) {
                    return;
                }
                orders = OrderController.getInstance().getCanceledOrders(orders);
                if (orders.isEmpty()) {
                    Printer.printInfo("There are no canceled orders for this period of time!");
                    return;
                }
                orders = OrderController.getInstance().sortOrderByCreationTime(orders);
                StringOrder.getStringFromOrder(orders);
            }
        }, canceledOrderMenu));
        canceledOrderMenu.getMenuItems().add(new MenuItem("Sort by execution date", () -> {
            List<Order> orders = new ArrayList<>();
            while (orders.isEmpty()) {
                orders = getSortPeriodOrders();
                if (orders.isEmpty()) {
                    return;
                }
                orders = OrderController.getInstance().getCanceledOrders(orders);
                if (orders.isEmpty()) {
                    Printer.printInfo("There are no canceled orders for this period of time!");
                    return;
                }
                orders = OrderController.getInstance().sortOrderByStartTime(orders);
                StringOrder.getStringFromOrder(orders);
            }
        }, canceledOrderMenu));
    }

    private void addItemCanceledOrderPartTwo(Menu canceledOrderMenu, Menu periodOrderMenu) {
        canceledOrderMenu.getMenuItems().add(new MenuItem("Sort by price", () -> {
            List<Order> orders = new ArrayList<>();
            while (orders.isEmpty()) {
                orders = getSortPeriodOrders();
                if (orders.isEmpty()) {
                    return;
                }
                orders = OrderController.getInstance().getCanceledOrders(orders);
                if (orders.isEmpty()) {
                    Printer.printInfo("There are no canceled orders for this period of time!");
                    return;
                }
                orders = OrderController.getInstance().sortOrderByPrice(orders);
                StringOrder.getStringFromOrder(orders);
            }
        }, canceledOrderMenu));
        canceledOrderMenu.getMenuItems().add(new MenuItem("Previous menu",
                () -> Printer.printInfo("Go to menu"), periodOrderMenu));
    }

    private boolean isContinue() {
        String answer = "";
        while (!answer.equals("y") && !answer.equals("n")) {
            answer = ScannerUtil.getStringUser(
                    "For proper import, do not forget to export \"masters\" and \" " +
                            "garages\"!\n Would you like to continue export? y/n");
            if (!answer.equals("y") && !answer.equals("n")) {
                Printer.printInfo("You have entered wrong answer!");
            }
        }
        return answer.equals("y");
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

    private void addOrder(MasterController masterController,
                          OrderController orderController, PlaceController placeController) {
        int indexMaster = 0;
        int indexGarage = 0;
        int indexPlace = 0;
        String message;
        for (int i = 0; i < TestData.getArrayAutomaker().size(); i++) {
            ArrayList<Master> mastersOrder = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                orderController.addOrderMasters()
                mastersOrder.add(indexMaster);
                indexMaster++;
                if (indexMaster == masterController.getMasters().size() - 1) {
                    indexMaster = 0;
                }
            }
            message = orderController.addOrder(TestData.getArrayAutomaker().get(i),
                    TestData.getArrayModel().get(i), TestData.getArrayRegistrationNumber().get(i));
            Printer.printInfo(message);
            message = orderController.addOrderDeadlines(TestData.getArrayExecutionStartTime().get(i),
                    TestData.getArrayLeadTime().get(i));
            Printer.printInfo(message);
            message = orderController.addOrderMasters(mastersOrder);
            Printer.printInfo(message);
            message = orderController.addOrderPlace(garages.(indexGarage),
                    garages.(indexGarage).getPlaces().(indexPlace));
            Printer.printInfo(message);
            message = orderController.addOrderPrice(TestData.getArrayPrice().get(i));
            Printer.printInfo(message);
            indexPlace++;
            if (indexPlace == 3) {
                indexPlace = 0;
                indexGarage++;
            }
        }
        List<Order> orders = orderController.getOrders();
        StringOrder.getStringFromOrder(orders);
    }

    private void deletePlace() {
        String message = null;
        int index;
        while (message == null) {
            index = ScannerUtil.getIntUser("Enter the index number of the garage to delete:");
            if (index == 0) {
                return;
            }
            if (index > PlaceController.getInstance().getArrayPlace().size() || index < 0) {
                Printer.printInfo("There is no such garage");
                continue;
            }
            if (PlaceController.getInstance().getArrayPlace().get(index - 1).getPlaces().size() < 1) {
                Printer.printInfo("There are no places in garage!");
                continue;
            }
            message = PlaceController.getInstance().deleteGaragePlace
                    (PlaceController.getInstance().getArrayPlace().get(index - 1));
            Printer.printInfo(message);
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
            message = OrderController.getInstance().deleteOrder(index-1);
            Printer.printInfo(message);
        }
    }

    private void deleteMaster() {
        int index = ScannerUtil.getIntUser("Enter the index number of the master to delete:");
        Printer.printInfo(MasterController.getInstance().deleteMaster(index - 1));
    }

    private void deleteGarage() {
        int index = ScannerUtil.getIntUser("Enter the index number of the place to delete:");
        Printer.printInfo(PlaceController.getInstance().deletePlace(index - 1));
    }

    private void completeOrder() {
        String message = "";
        int index;
        while (!message.equals(" - the order has been transferred to execution status")) {
            index = ScannerUtil.getIntUser("Enter the index number of the order to change status:");
            if (index == 0) {
                return;
            }
            message = OrderController.getInstance().completeOrder(index - 1);
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
            message = OrderController.getInstance().cancelOrder(index - 1);
            Printer.printInfo(message);
        }
    }

    private void addPlace() {
        String message = null;
        int index;
        while (message == null) {
            index = ScannerUtil.getIntUser("Enter the index number of the garage to add place:");
            if (index == 0) {
                return;
            }
            if (index > PlaceController.getInstance().getArrayPlace().size() || index < 0) {
                Printer.printInfo("There is no such garage!");
                continue;
            }
            message = PlaceController.getInstance().addGaragePlace
                    (PlaceController.getInstance().getArrayPlace().get(index - 1));
        }
        Printer.printInfo(message);
    }

    private void addOrder() {
        String automaker = ScannerUtil.getStringUser("Enter the automaker of car");
        String model = ScannerUtil.getStringUser("Enter the model of car");
        String registrationNumber = ScannerUtil.getStringUser
                ("Enter the registration number of car, example: 1111 AB-7");
        Printer.printInfo(OrderController.getInstance().addOrder(automaker, model, registrationNumber));
        List<String> deadline = addOrderDeadline();
        String executionStartTime = deadline.get(0);
        String leadTime = deadline.get(1);
        addMastersOrder(executionStartTime, leadTime);
//        Printer.printInfo(PlaceController.getInstance().getFreePlacesByDate(executionStartTime, leadTime));
        addPlaceOrder(executionStartTime, leadTime);
        Printer.printInfo(OrderController.getInstance()
                .addOrderPrice(ScannerUtil.getBigDecimalUser("Enter the price")));
    }

    private List<String> addOrderDeadline() {
        String message = "";
        String leadTime = "";
        String executionStartTime = "";
        OrderController orderController = OrderController.getInstance();
        while (!message.equals("deadline add to order successfully")) {
            executionStartTime = ScannerUtil.getStringDateUser(
                    "Enter the planing time start to execute the order in " +
                            "format \"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"");
            leadTime = ScannerUtil.getStringDateUser(
                    "Enter the lead time the order in format " +
                            "\"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"");
            message = orderController.addOrderDeadlines(executionStartTime, leadTime);
            Printer.printInfo(message);
        }
        return Arrays.asList(executionStartTime, leadTime);
    }

    private void addMastersOrder(String executionStartTime, String leadTime) {
        String message = "";
        Printer.printInfo(MasterController.getInstance().getFreeMasters(executionStartTime, leadTime));
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
            if (index == 0){
                break;
            }
            message = OrderController.getInstance().addOrderMasters(index);
            Printer.printInfo(message);
            userAnswer = isAnotherMaster();
            if (userAnswer){
                index = 0;
                break;
            }
        }
        if (index != 0){
            quit++;
        }
        return new ArrayList<>(Arrays.asList(quit, index));
    }

    private boolean isAnotherMaster() {
        String answer = "";
        while (!answer.equals("y") && !answer.equals("n")) {
            answer = ScannerUtil.getStringUser("Add another master to the order? y/n");
            if (!answer.equals("y") && !answer.equals("n")) {
                Printer.printInfo("You have entered wrong answer!");
            }
        }
        return answer.equals("n");
    }

    private void addPlaceOrder(String executionStartTime, String leadTime) {
        String message = "";
        int index = 0;
        Printer.printInfo(PlaceController.getInstance().getFreePlacesByDate(executionStartTime, leadTime));
        Printer.printInfo("0. Stop adding");
        while (!message.equals("place add to order successfully")) {
            index = ScannerUtil.getIntUser("Enter the index number of the place to add in order:");
            message = OrderController.getInstance().addOrderPlace(index, executionStartTime, leadTime);
            Printer.printInfo(message);
        }
    }

    private List<Order> getSortPeriodOrders() {
        String beginningPeriodTime;
        String endPeriodTime;
        OrderController orderController = OrderController.getInstance();
        List<Order> testOrders = orderController.getOrders();
        List<Order> orders = new ArrayList<>();
        if (testOrders.isEmpty()) {
            Printer.printInfo("There are no orders in service!");
            return orders;
        }
        beginningPeriodTime = ScannerUtil.getStringDateUser(
                "Enter the start of period in format \"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"");
        endPeriodTime = ScannerUtil.getStringDateUser(
                "Enter the end of period in format \"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"");
        orders = orderController.getOrdersByPeriod(beginningPeriodTime, endPeriodTime);
        Printer.printInfo(String.format("Period of time: %s - %s", beginningPeriodTime, endPeriodTime));
        if (orders.isEmpty()) {
            Printer.printInfo("There are no orders for this period of time!");
        }
        return orders;
    }
}