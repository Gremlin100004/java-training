package com.senla.carservice.ui.menu;

import com.senla.carservice.controller.CarOfficeController;
import com.senla.carservice.controller.PlaceController;
import com.senla.carservice.controller.MasterController;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.ui.printer.PrinterGarages;
import com.senla.carservice.ui.printer.PrinterMaster;
import com.senla.carservice.ui.printer.PrinterOrder;
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
        Menu garagesMenu = new Menu("Garages");
        Menu listOrderMenu = new Menu("Orders list");
        Menu executedOrderMenu = new Menu("list of orders currently being executed");
        Menu periodOrderMenu = new Menu("Orders list for a period of time");
        Menu completedOrderMenu = new Menu("Completed orders");
        Menu deletedOrderMenu = new Menu("Deleted orders");
        Menu canceledOrderMenu = new Menu("Canceled orders");
        setMenuItemRootMenu(mastersMenu, ordersMenu, garagesMenu);
        setMenuItemRootMenuPart();
        createItemMastersMenu(mastersMenu);
        createItemGaragesMenu(garagesMenu, this.rootMenu);
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

    private void setMenuItemRootMenu(Menu mastersMenu, Menu ordersMenu, Menu garagesMenu) {
        this.rootMenu.getMenuItems().add(new MenuItem
                ("Masters", () -> System.out.println("Go to menu"), mastersMenu));
        this.rootMenu.getMenuItems().add(new MenuItem
                ("Orders", () -> System.out.println("Go to menu"), ordersMenu));
        this.rootMenu.getMenuItems().add(new MenuItem
                ("Garages", () -> System.out.println("Go to menu"), garagesMenu));
        this.rootMenu.getMenuItems().add(new MenuItem
                ("Get the number of available seats at the car service", () -> {
                    String message = null;
                    String date;
                    if (MasterController.getInstance().getMasters().isEmpty()) {
                        System.out.println("Add masters to service!!!");
                        return;
                    }
                    if (PlaceController.getInstance().getNumberFreePlaces() == 0) {
                        System.out.println("Add places to garages!!!");
                        return;
                    }
                    while (message == null) {
                        date = ScannerUtil.getStringDateUser(
                                "Enter the date in format dd.mm.yyyy, example:\"10.10.2010\"");
                        message = CarOfficeController.getInstance().getFreePlacesByDate(date);
                        if (message.equals("error date")) {
                            System.out.println("You enter wrong value!!!");
                            continue;
                        }
                        if (message.equals("past date")) {
                            System.out.println("Entered date cannot be in the past!!!");
                            message = null;
                        }
                    }
                    System.out.println(message);
                }, this.rootMenu));
    }

    private void setMenuItemRootMenuPart() {
        this.rootMenu.getMenuItems().add(new MenuItem("Get the closest free date",
                () -> System.out.println(CarOfficeController.getInstance().getNearestFreeDate()), this.rootMenu));
        this.rootMenu.getMenuItems().add(new MenuItem("Fill in test data", () -> {
            String delimiter = "***********************************************************************";
            System.out.println(delimiter);
            addMaster(delimiter, MasterController.getInstance());
            addGarage(delimiter, PlaceController.getInstance());
            addPlaceGarage(delimiter, PlaceController.getInstance());
            System.out.println("Add new orders to car service.");
            addOrder(MasterController.getInstance(), OrderController.getInstance(), PlaceController.getInstance());
        }, this.rootMenu));
        this.rootMenu.getMenuItems().add(new MenuItem("Export of all entities", () -> {
            System.out.println(MasterController.getInstance().exportMasters());
            System.out.println(PlaceController.getInstance().exportGarages());
            System.out.println(OrderController.getInstance().exportOrders());
        }, this.rootMenu));
        this.rootMenu.getMenuItems().add(new MenuItem("Import of all entities", () -> {
            System.out.println(MasterController.getInstance().importMasters());
            System.out.println(PlaceController.getInstance().importGarages());
            System.out.println(OrderController.getInstance().importOrders());
        }, this.rootMenu));
    }

    private void createItemMastersMenu(Menu mastersMenu) {
        mastersMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Add master", () -> System.out.println(MasterController.getInstance().
                        addMaster(ScannerUtil.getStringUser("Enter the name of master"))), mastersMenu),
                new MenuItem("Delete Master", () -> {
                    if (MasterController.getInstance().getMasters().isEmpty()) {
                        System.out.println("There are no masters to delete!");
                        return;
                    }
                    PrinterMaster.printMasters(MasterController.getInstance().getMasters());
                    System.out.println("0. Previous menu");
                    deleteMaster();
                }, mastersMenu),
                new MenuItem("Show a list of masters sorted alphabetically", () -> {
                    if (MasterController.getInstance().sortMasterByAlphabet().isEmpty()) {
                        System.out.println("There are no masters.");
                        return;
                    }
                    PrinterMaster.printMasters(MasterController.getInstance().sortMasterByAlphabet());
                }, mastersMenu),
                new MenuItem("Show list of masters sorted by busy", () -> {
                    if (MasterController.getInstance().sortMasterByBusy().isEmpty()) {
                        System.out.println("There are no masters.");
                        return;
                    }
                    PrinterMaster.printMasters(MasterController.getInstance().sortMasterByBusy());
                }, mastersMenu),
                new MenuItem("Export masters",
                        () -> System.out.println(MasterController.getInstance().exportMasters()), mastersMenu),
                new MenuItem("Import masters",
                        () -> System.out.println(MasterController.getInstance().importMasters()), mastersMenu),
                new MenuItem("Previous menu", () -> System.out.println("Go to menu"), this.rootMenu)
        )));
    }


    private void createItemGaragesMenu(Menu garagesMenu, Menu rootMenu) {
        garagesMenu.getMenuItems().add(new MenuItem("Show list of garages", () -> {
            if (PlaceController.getInstance().getArrayPlace().isEmpty()) {
                System.out.println("There are no garages.");
                return;
            }
            PrinterGarages.printGarages(PlaceController.getInstance().getArrayPlace());
        }, garagesMenu));
        garagesMenu.getMenuItems().add(new MenuItem("Add garage",
                () -> System.out.println(PlaceController.getInstance()
                        .addPlace(ScannerUtil.getStringUser("Enter the name of garage"))), garagesMenu));
        garagesMenu.getMenuItems().add(new MenuItem("Delete garage", () -> {
            if (PlaceController.getInstance().getArrayPlace().isEmpty()) {
                System.out.println("There are no garages to delete!");
                return;
            }
            PrinterGarages.printGarages(PlaceController.getInstance().getArrayPlace());
            System.out.println("0. Previous menu");
            deleteGarage();
        }, garagesMenu));
        addItemGarageMenu(garagesMenu, rootMenu);
    }

    private void addItemGarageMenu(Menu garagesMenu, Menu rootMenu) {
        garagesMenu.getMenuItems().add(new MenuItem("Add place in garage", () -> {
            if (PlaceController.getInstance().getArrayPlace().isEmpty()) {
                System.out.println("There are no garages!");
                return;
            }
            PrinterGarages.printGarages(PlaceController.getInstance().getArrayPlace());
            System.out.println("0. Previous menu");
            addPlace();
        }, garagesMenu));
        garagesMenu.getMenuItems().add(new MenuItem("Delete place in garage", () -> {
            if (PlaceController.getInstance().getArrayPlace().isEmpty()) {
                System.out.println("There are no garages to delete place!");
                return;
            }
            PrinterGarages.printGarages(PlaceController.getInstance().getArrayPlace());
            System.out.println("0. Previous menu");
            deletePlace();
        }, garagesMenu));
        garagesMenu.getMenuItems().add(new MenuItem("Export garages", () -> System.out.println
                (PlaceController.getInstance().exportGarages()), garagesMenu));
        garagesMenu.getMenuItems().add(new MenuItem("Import garages",
                () -> System.out.println(PlaceController.getInstance().importGarages()), garagesMenu));
        garagesMenu.getMenuItems().add(new MenuItem("Previous menu",
                () -> System.out.println("Go to menu"), rootMenu));
    }

    private void createItemOrderMenu(List<Menu> menus) {
        menus.get(0).setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("List of orders", () -> System.out.println("Go to menu"), menus.get(1)),
                new MenuItem("List of orders executed at a given time",
                        () -> System.out.println("Go to menu"), menus.get(2)),
                new MenuItem("List of orders for a period of time",
                        () -> System.out.println("Go to menu"), menus.get(3)),
                new MenuItem("List of orders for a period of time",
                        () -> System.out.println("Go to menu"), menus.get(3)),
                new MenuItem("Previous menu", () -> System.out.println("Go to menu"), this.rootMenu)
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
            if (OrderController.getInstance().getOrders().isEmpty()) {
                System.out.println("There are no orders.");
                return;
            }
            PrinterOrder.printOrder(OrderController.getInstance().getOrders());
        }, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Add order", () -> {
            if (MasterController.getInstance().getMasters().isEmpty()) {
                System.out.println("There are no masters!");
                return;
            }
            if (!isPlace(PlaceController.getInstance().getArrayPlace())) {
                System.out.println("There are no Places!");
                return;
            }
            addOrder();
        }, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Delete the order", () -> {
            if (OrderController.getInstance().getOrders().isEmpty()) {
                System.out.println("There are no orders to delete!");
                return;
            }
            PrinterOrder.printOrder(OrderController.getInstance().getOrders());
            System.out.println("0. Previous menu");
            deleteOrder();
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartTwo(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(new MenuItem("Close the order", () -> {
            if (OrderController.getInstance().getOrders().isEmpty()) {
                System.out.println("There are no orders!");
                return;
            }
            PrinterOrder.printOrder(OrderController.getInstance().getOrders());
            System.out.println("0. Previous menu");
            String message = "";
            int index;
            while (!message.equals(" -the order has been completed.")) {
                index = ScannerUtil.getIntUser("Enter the index number of the order to close:");
                if (index == 0) {
                    return;
                }
                if (index > OrderController.getInstance().getOrders().size() || index < 0) {
                    System.out.println("There is no such order");
                    continue;
                }
                message = OrderController.getInstance()
                        .closeOrder(OrderController.getInstance().getOrders().get(index - 1));
                System.out.println(message);
            }
        }, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Cancel the order", () -> {
            if (OrderController.getInstance().getOrders().isEmpty()) {
                System.out.println("There are no orders!");
                return;
            }
            PrinterOrder.printOrder(OrderController.getInstance().getOrders());
            System.out.println("0. Previous menu");
            cancelOrder();
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartThree(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(new MenuItem("Transfer the order to execution status", () -> {
            if (OrderController.getInstance().getOrders().isEmpty()) {
                System.out.println("There are no orders!");
                return;
            }
            PrinterOrder.printOrder(OrderController.getInstance().getOrders());
            System.out.println("0. Previous menu");
            completeOrder();
        }, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Shift the lead time", () -> {
            String message = "";
            if (OrderController.getInstance().getOrders().isEmpty()) {
                System.out.println("There are no orders!");
                return;
            }
            PrinterOrder.printOrder(OrderController.getInstance().getOrders());
            int index = 0;
            while (index == 0) {
                index = ScannerUtil.getIntUser("Enter the index number of the order to shift time:");
                if (index > OrderController.getInstance().getOrders().size() || index < 1) {
                    System.out.println("There is no such master");
                    continue;
                }
                break;
            }
            while (!message.equals(" -the order lead time has been changed.")) {
                message = OrderController.getInstance().shiftLeadTime(OrderController.
                                getInstance().getOrders().get(index - 1), ScannerUtil.getStringDateUser(
                        "Enter the planing time start to execute the order in format " +
                                "\"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\""),
                        ScannerUtil.getStringDateUser("Enter the lead time the order in format" +
                                " \"dd.MM.yyyy hh:mm\"," + " example:\"10.10.2010 10:00\""));
                System.out.println(message);
            }
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartFour(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(new MenuItem("Show orders sort by filing date", () -> {
            if (OrderController.getInstance().getOrders().isEmpty()) {
                System.out.println("There are no orders.");
                return;
            }
            PrinterOrder.printOrder(OrderController.getInstance().
                    sortOrderByCreationTime(OrderController.getInstance().getOrders()));
        }, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Show orders sort by execution date", () -> {
            if (OrderController.getInstance().getOrders().isEmpty()) {
                System.out.println("There are no orders.");
                return;
            }
            PrinterOrder.printOrder(OrderController.getInstance()
                    .sortOrderByLeadTime(OrderController.getInstance().getOrders()));
        }, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Show orders sort by planned start date", () -> {
            if (OrderController.getInstance().getOrders().isEmpty()) {
                System.out.println("There are no orders.");
                return;
            }
            List<Order> sortArrayOrders = OrderController
                    .getInstance().sortOrderByStartTime(OrderController.getInstance().getOrders());
            PrinterOrder.printOrder(sortArrayOrders);
        }, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Show orders sort by price", () -> {
            List<Order> orders = OrderController.getInstance().getOrders();
            if (orders.isEmpty()) {
                System.out.println("There are no orders.");
                return;
            }
            List<Order> sortArrayOrders = OrderController.getInstance().sortOrderByPrice(orders);
            PrinterOrder.printOrder(sortArrayOrders);
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartFive(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(new MenuItem("Get orders executed concrete master.", () -> {
            int index;
            if (MasterController.getInstance().getMasters().isEmpty()) {
                System.out.println("There are no masters.");
                return;
            }
            PrinterMaster.printMasters(MasterController.getInstance().getMasters());
            System.out.println("0. Previous menu");
            List<Order> orders = new ArrayList<>();
            while (orders.isEmpty()) {
                index = ScannerUtil.getIntUser("Enter the index number of the master to view orders:");
                if (index == 0) {
                    return;
                }
                if (index > MasterController.getInstance().getMasters().size() || index < 0) {
                    System.out.println("There is no such master");
                    continue;
                }
                orders = OrderController.getInstance()
                        .getMasterOrders(MasterController.getInstance().getMasters().get(index - 1));
                if (OrderController.getInstance()
                        .getMasterOrders(MasterController.getInstance().getMasters().get(index - 1)).isEmpty()) {
                    System.out.println("Such master has no orders!");
                    continue;
                }
                PrinterOrder.printOrder(OrderController
                        .getInstance().getMasterOrders(MasterController.getInstance().getMasters().get(index - 1)));
                break;
            }
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartSix(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(new MenuItem("Get a master performing a specific order", () -> {
            int index;
            if (OrderController.getInstance().getOrders().isEmpty()) {
                System.out.println("There are no orders.");
                return;
            }
            PrinterOrder.printOrder(OrderController.getInstance().getOrders());
            System.out.println("0. Previous menu");
            List<Master> masters = new ArrayList<>();
            while (masters.isEmpty()) {
                index = ScannerUtil.getIntUser("Enter the index number of the order to view masters:");
                if (index == 0) {
                    return;
                }
                if (index > OrderController.getInstance().getOrders().size() || index < 0) {
                    System.out.println("There is no such order");
                    continue;
                }
                masters = OrderController.getInstance().getOrderMasters
                        (OrderController.getInstance().getOrders().get(index - 1));
                PrinterMaster.printMasters(masters);
            }
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartSeven(Menu listOrderMenu, Menu ordersMenu) {
        listOrderMenu.getMenuItems().add(new MenuItem("Export orders", () -> {
            OrderController orderController = OrderController.getInstance();
            if (isContinue()) {
                System.out.println(orderController.exportOrders());
            }
        }, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Import orders",
                () -> System.out.println(OrderController.getInstance().importOrders()), listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem("Previous menu",
                () -> System.out.println("Go to menu"), ordersMenu));
    }

    private void createItemExecutedOrderMenu(Menu executedOrderMenu, Menu ordersMenu) {
        executedOrderMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Get list of orders executed at a given time sort by filing date", () -> {
                    List<Order> executedOrders = OrderController.getInstance().
                            sortOrderByCreationTime(OrderController.getInstance().getExecuteOrder());
                    if (executedOrders.isEmpty()) {
                        System.out.println("There are no orders!");
                        return;
                    }
                    PrinterOrder.printOrder(executedOrders);
                }, executedOrderMenu),
                new MenuItem("Get list of orders executed at a given time sort by execution date",
                        () -> {
                            List<Order> executedOrders = OrderController
                                    .getInstance().sortOrderByLeadTime(OrderController.getInstance().getExecuteOrder());
                            if (executedOrders.isEmpty()) {
                                System.out.println("There are no orders!");
                                return;
                            }
                            PrinterOrder.printOrder(executedOrders);
                        }, executedOrderMenu),
                new MenuItem("Get list of orders executed at a given time sort by price", () -> {
                    List<Order> executedOrders = OrderController.getInstance().
                            sortOrderByPrice(OrderController.getInstance().getExecuteOrder());
                    if (executedOrders.isEmpty()) {
                        System.out.println("There are no orders!");
                        return;
                    }
                    PrinterOrder.printOrder(executedOrders);
                }, executedOrderMenu),
                new MenuItem("Previous menu", () -> System.out.println("Go to menu"), ordersMenu)
        )));
    }

    private void createItemPeriodOrderMenu(List<Menu> menus) {
        menus.get(0).setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Completed orders", () -> System.out.println("Go to menu"), menus.get(1)),
                new MenuItem("Deleted orders", () -> System.out.println("Go to menu"), menus.get(2)),
                new MenuItem("Canceled orders", () -> System.out.println("Go to menu"), menus.get(3)),
                new MenuItem("Previous menu", () -> System.out.println("Go to menu"), menus.get(4))
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
                    System.out.println("There are no completed orders for this period of time!");
                    return;
                }
                orders = OrderController.getInstance().sortOrderByCreationTime(orders);
                PrinterOrder.printOrder(orders);
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
                    System.out.println("There are no completed orders for this period of time!");
                    return;
                }
                orders = OrderController.getInstance().sortOrderByStartTime(orders);
                PrinterOrder.printOrder(orders);
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
                    System.out.println("There are no completed orders for this period of time!");
                    return;
                }
                orders = OrderController.getInstance().sortOrderByPrice(orders);
                PrinterOrder.printOrder(orders);
            }
        }, completedOrderMenu));
        completedOrderMenu.getMenuItems().add(new MenuItem("Previous menu",
                () -> System.out.println("Go to menu"), periodOrderMenu));
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
                    System.out.println("There are no deleted orders for this period of time!");
                    return;
                }
                orders = OrderController.getInstance().sortOrderByCreationTime(orders);
                PrinterOrder.printOrder(orders);
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
                    System.out.println("There are no deleted orders for this period of time!");
                    return;
                }
                orders = OrderController.getInstance().sortOrderByStartTime(orders);
                PrinterOrder.printOrder(orders);
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
                    System.out.println("There are no deleted orders for this period of time!");
                    return;
                }
                orders = OrderController.getInstance().sortOrderByPrice(orders);
                PrinterOrder.printOrder(orders);
            }
        }, deletedOrderMenu));
        deletedOrderMenu.getMenuItems().add(new MenuItem("Previous menu",
                () -> System.out.println("Go to menu"), periodOrderMenu));
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
                    System.out.println("There are no canceled orders for this period of time!");
                    return;
                }
                orders = OrderController.getInstance().sortOrderByCreationTime(orders);
                PrinterOrder.printOrder(orders);
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
                    System.out.println("There are no canceled orders for this period of time!");
                    return;
                }
                orders = OrderController.getInstance().sortOrderByStartTime(orders);
                PrinterOrder.printOrder(orders);
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
                    System.out.println("There are no canceled orders for this period of time!");
                    return;
                }
                orders = OrderController.getInstance().sortOrderByPrice(orders);
                PrinterOrder.printOrder(orders);
            }
        }, canceledOrderMenu));
        canceledOrderMenu.getMenuItems().add(new MenuItem("Previous menu",
                () -> System.out.println("Go to menu"), periodOrderMenu));
    }

    private boolean isContinue() {
        String answer = "";
        while (!answer.equals("y") && !answer.equals("n")) {
            answer = ScannerUtil.getStringUser(
                    "For proper import, do not forget to export \"masters\" and \" " +
                            "garages\"!\n Would you like to continue export? y/n");
            if (!answer.equals("y") && !answer.equals("n")) {
                System.out.println("You have entered wrong answer!");
            }
        }
        return answer.equals("y");
    }

    private void addMaster(String delimiter, MasterController masterController) {
        System.out.println("Add master:");
        for (String masterName : TestData.getArrayMasterNames()) {
            System.out.println(String.format(" -master \"%s\" has been added to service.",
                    masterController.addMaster(masterName)));
        }
        System.out.println(delimiter);
    }

    private void addGarage(String delimiter, PlaceController placeController) {
        System.out.println("Add garage to service:");
        for (String garageName : TestData.getArrayGarageNames()) {
            System.out.println(String.format(" -garage \"%s\" has been added to service",
                    placeController.addPlace(garageName)));
        }
        System.out.println(delimiter);
    }

    private void addPlaceGarage(String delimiter, PlaceController placeController) {
        List<Garage> garages = placeController.getArrayPlace();
        System.out.println("Add places in garages.");
        for (Garage garage : garages) {
            for (int j = 0; j < 4; j++) {
                System.out.println(String.format("Add place in garage \"%s\"",
                        placeController.addGaragePlace(garage)));
            }
        }
        System.out.println(delimiter);
    }

    private void addOrder(MasterController masterController,
                          OrderController orderController, PlaceController placeController) {
        int indexMaster = 0;
        int indexGarage = 0;
        int indexPlace = 0;
        String message;
        List<Garage> garages = placeController.getArrayPlace();
        for (int i = 0; i < TestData.getArrayAutomaker().size(); i++) {
            ArrayList<Master> mastersOrder = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                mastersOrder.add(masterController.getMasters().get(indexMaster));
                indexMaster++;
                if (indexMaster == masterController.getMasters().size() - 1) {
                    indexMaster = 0;
                }
            }
            message = orderController.addOrder(TestData.getArrayAutomaker().get(i),
                    TestData.getArrayModel().get(i), TestData.getArrayRegistrationNumber().get(i));
            System.out.println(message);
            message = orderController.addOrderDeadlines(TestData.getArrayExecutionStartTime().get(i),
                    TestData.getArrayLeadTime().get(i));
            System.out.println(message);
            message = orderController.addOrderMasters(mastersOrder);
            System.out.println(message);
            message = orderController.addOrderPlaces(garages.get(indexGarage),
                    garages.get(indexGarage).getPlaces().get(indexPlace));
            System.out.println(message);
            message = orderController.addOrderPrice(TestData.getArrayPrice().get(i));
            System.out.println(message);
            indexPlace++;
            if (indexPlace == 3) {
                indexPlace = 0;
                indexGarage++;
            }
        }
        List<Order> orders = orderController.getOrders();
        PrinterOrder.printOrder(orders);
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
                System.out.println("There is no such garage");
                continue;
            }
            if (PlaceController.getInstance().getArrayPlace().get(index - 1).getPlaces().size() < 1) {
                System.out.println("There are no places in garage!");
                continue;
            }
            message = PlaceController.getInstance().deleteGaragePlace
                    (PlaceController.getInstance().getArrayPlace().get(index - 1));
            System.out.println(message);
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
            if (index > OrderController.getInstance().getOrders().size() || index < 0) {
                System.out.println("There is no such order");
                continue;
            }
            message = OrderController.getInstance()
                    .deleteOrder(OrderController.getInstance().getOrders().get(index - 1));
            System.out.println(message);
        }
    }

    private void deleteMaster() {
        String message = null;
        int index;
        while (message == null) {
            System.out.println();
            index = ScannerUtil.getIntUser("Enter the index number of the master to delete:");
            if (index == 0) {
                return;
            }
            if (index > MasterController.getInstance().getMasters().size() || index < 0) {
                System.out.println("There is no such master");
                continue;
            }
            message = MasterController.getInstance()
                    .deleteMaster(MasterController.getInstance().getMasters().get(index - 1));
        }
        System.out.println(message);
    }

    private void deleteGarage() {
        String message = null;
        int index;
        while (message == null) {
            index = ScannerUtil.getIntUser("Enter the index number of the garage to delete:");
            if (index == 0) {
                return;
            }
            if (index > PlaceController.getInstance().getArrayPlace().size() || index < 0) {
                System.out.println("There is no such garage");
                continue;
            }
            message = PlaceController.getInstance()
                    .deleteGarage(PlaceController.getInstance().getArrayPlace().get(index - 1));
        }
        System.out.println(message);
    }

    private void completeOrder() {
        String message = "";
        int index;
        do {
            index = ScannerUtil.getIntUser("Enter the index number of the order to change status:");
            if (index == 0) {
                return;
            }
            if (index > OrderController.getInstance().getOrders().size() || index < 0) {
                System.out.println("There is no such order");
                continue;
            }
            message = OrderController.getInstance()
                    .completeOrder(OrderController.getInstance().getOrders().get(index - 1));
            System.out.println(message);
        } while (!message.equals(" - the order has been transferred to execution status"));
    }

    private void cancelOrder() {
        String message = "";
        int index;
        while (!message.equals(" -the order has been canceled.")) {
            index = ScannerUtil.getIntUser("Enter the index number of the order to cancel:");
            if (index == 0) {
                return;
            }
            if (index > OrderController.getInstance().getOrders().size() || index < 0) {
                System.out.println("There is no such order");
                continue;
            }
            message = OrderController.getInstance()
                    .cancelOrder(OrderController.getInstance().getOrders().get(index - 1));
            System.out.println(message);
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
                System.out.println("There is no such garage!");
                continue;
            }
            message = PlaceController.getInstance().addGaragePlace
                    (PlaceController.getInstance().getArrayPlace().get(index - 1));
        }
        System.out.println(message);
    }

    private void addOrder() {
        String automaker = ScannerUtil.getStringUser("Enter the automaker of car");
        String model = ScannerUtil.getStringUser("Enter the model of car");
        String registrationNumber = ScannerUtil.getStringUser
                ("Enter the registration number of car, example: 1111 AB-7");
        String message = OrderController.getInstance().addOrder(automaker, model, registrationNumber);
        System.out.println(message);
        List<String> deadline = addOrderDeadline();
        String executionStartTime = deadline.get(0);
        String leadTime = deadline.get(1);
        addMastersOrder(executionStartTime, leadTime);
        List<Garage> freeGarages = CarOfficeController.getInstance().getFreePlace(executionStartTime, leadTime);
        System.out.println("Garage with free places:");
        for (int i = 0; i < freeGarages.size(); i++) {
            System.out.println(String.format("%s. %s", i + 1, freeGarages.get(i).getName()));
        }
        Garage garage = addGarageOrder(freeGarages, PlaceController.getInstance());
        Place place = PlaceController.getInstance().getFreePlaceGarage(garage).get(0);
        message = OrderController.getInstance().addOrderPlaces(garage, place);
        System.out.println(message);
        message = OrderController.getInstance()
                .addOrderPrice(ScannerUtil.getBigDecimalUser("Enter the price"));
        System.out.println(message);
    }

    private List<String> addOrderDeadline() {
        String message = "";
        String leadTime = "";
        String executionStartTime = "";
        OrderController orderController = OrderController.getInstance();
        while (!message.equals("deadline add to order successfully")) {
            System.out.println(message);
            executionStartTime = ScannerUtil.getStringDateUser(
                    "Enter the planing time start to execute the order in " +
                            "format \"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"");
            leadTime = ScannerUtil.getStringDateUser(
                    "Enter the lead time the order in format " +
                            "\"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"");
            message = orderController.addOrderDeadlines(executionStartTime, leadTime);
        }
        return Arrays.asList(executionStartTime, leadTime);
    }

    private void addMastersOrder(String executionStartTime, String leadTime) {
        String message = "";
        CarOfficeController carOfficeController = CarOfficeController.getInstance();
        OrderController orderController = OrderController.getInstance();
        List<Master> freeMaster = carOfficeController.getFreeMasters(executionStartTime, leadTime);
        List<Master> orderMasters = new ArrayList<>();
        PrinterMaster.printMasters(freeMaster);
        System.out.println("0. Stop adding");
        while (!message.equals("masters add successfully")) {
            addMasters(freeMaster, orderMasters);
            message = orderController.addOrderMasters(orderMasters);
        }
    }

    private void addMasters(List<Master> masters, List<Master> orderMaster) {
        boolean userAnswer = false;
        while (!userAnswer) {
            int index = ScannerUtil.getIntUser("Enter the index number of the master to add:");
            if (index == 0 && orderMaster.size() > 0) {
                return;
            }
            if (index == 0) {
                System.out.println("Add at least one master!");
                continue;
            }
            if (index > masters.size() || index < 0) {
                System.out.println("There is no such master");
                continue;
            }
            if (orderMaster.contains(masters.get(index - 1))) {
                System.out.println("You have already add such master!");
                continue;
            }
            orderMaster.add(masters.get(index - 1));
            userAnswer = isAnotherMaster();
        }
    }

    private boolean isAnotherMaster() {
        String answer = "";
        while (!answer.equals("y") && !answer.equals("n")) {
            answer = ScannerUtil.getStringUser("Add another master to the order? y/n");
            if (!answer.equals("y") && !answer.equals("n")) {
                System.out.println("You have entered wrong answer!");
            }
        }
        return answer.equals("n");
    }

    private Garage addGarageOrder(List<Garage> garages, PlaceController placeController) {
        boolean isFreePlaceGarage = false;
        int index = 0;
        while (!isFreePlaceGarage) {
            System.out.println();
            index = ScannerUtil.getIntUser("Enter the index number of the garage to add in order:");
            if (placeController.getNumberFreePlaceGarage(garages.get(index - 1)) < 1) {
                System.out.println("There are no free place in garage");
                continue;
            }
            isFreePlaceGarage = true;
        }
        return garages.get(index - 1);
    }

    private boolean isPlace(List<Garage> garages) {
        for (Garage garage : garages) {
            if (garage.getPlaces().size() > 0) {
                return true;
            }
        }
        return false;
    }

    private List<Order> getSortPeriodOrders() {
        String beginningPeriodTime;
        String endPeriodTime;
        OrderController orderController = OrderController.getInstance();
        List<Order> testOrders = orderController.getOrders();
        List<Order> orders = new ArrayList<>();
        if (testOrders.isEmpty()) {
            System.out.println("There are no orders in service!");
            return orders;
        }
        beginningPeriodTime = ScannerUtil.getStringDateUser(
                "Enter the start of period in format \"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"");
        endPeriodTime = ScannerUtil.getStringDateUser(
                "Enter the end of period in format \"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"");
        orders = orderController.getOrdersByPeriod(beginningPeriodTime, endPeriodTime);
        System.out.println(String.format("Period of time: %s - %s", beginningPeriodTime, endPeriodTime));
        if (orders.isEmpty()) {
            System.out.println("There are no orders for this period of time!");
        }
        return orders;
    }
}