package com.senla.carservice.ui.menu;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
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

@Singleton
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
    private static final int INDEX_OFFSET = 1;
    private static final int MAX_INDEX = 999;

    public Builder() {
    }

    public void buildMenu() {
        this.rootMenu = new Menu(MenuTittle.CAR_SERVICE_MENU.toString());
        Menu mastersMenu = new Menu(MenuTittle.MASTERS.toString());
        Menu ordersMenu = new Menu(MenuTittle.ORDERS.toString());
        Menu placesMenu = new Menu(MenuTittle.PLACES.toString());
        Menu listOrderMenu = new Menu(MenuTittle.ORDERS_LIST.toString());
        Menu executedOrderMenu = new Menu(MenuTittle.LIST_OF_ORDERS_CURRENTLY_BEING_EXECUTED.toString());
        Menu periodOrderMenu = new Menu(MenuTittle.ORDERS_LIST_FOR_A_PERIOD_OF_TIME.toString());
        Menu completedOrderMenu = new Menu(MenuTittle.COMPLETED_ORDERS.toString());
        Menu deletedOrderMenu = new Menu(MenuTittle.DELETED_ORDERS.toString());
        Menu canceledOrderMenu = new Menu(MenuTittle.CANCELED_ORDERS.toString());
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
        this.rootMenu.getMenuItems().add(
            new MenuItem(MenuTittle.MASTERS.toString(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.toString()), mastersMenu));
        this.rootMenu.getMenuItems().add(
            new MenuItem(MenuTittle.ORDERS.toString(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.toString()), ordersMenu));
        this.rootMenu.getMenuItems().add(
            new MenuItem(MenuTittle.PLACES.toString(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.toString()), placesMenu));
        this.rootMenu.getMenuItems().add(
            new MenuItem(MenuTittle.GET_THE_NUMBER_OF_AVAILABLE_SEATS_AT_THE_CAR_SERVICE.toString(), () -> {
                String date =
                    ScannerUtil.getStringDateUser("Enter the date in format dd.mm.yyyy, example:\"10.10.2010\"", false);
                Printer.printInfo(carOfficeController.getFreePlacesMastersByDate(date));
            }, this.rootMenu));
    }

    private void setMenuItemRootMenuPart() {
        this.rootMenu.getMenuItems().add(new MenuItem(MenuTittle.GET_THE_CLOSEST_FREE_DATE.toString(),
                                                      () -> Printer.printInfo(carOfficeController.getNearestFreeDate()),
                                                      this.rootMenu));
        this.rootMenu.getMenuItems().add(new MenuItem(MenuTittle.FILL_IN_TEST_DATA.toString(), () -> {
            String delimiter = "***********************************************************************";
            Printer.printInfo(delimiter);
            addMaster(delimiter, masterController);
            addPlace(delimiter, placeController);
            Printer.printInfo(MenuTittle.ADD_NEW_ORDERS_TO_CAR_SERVICE.toString());
            addOrderDate(orderController);
        }, this.rootMenu));
        this.rootMenu.getMenuItems().add(
            new MenuItem(MenuTittle.EXPORT_OF_ALL_ENTITIES.toString(),
                         () -> Printer.printInfo(carOfficeController.exportEntities()), this.rootMenu));
        this.rootMenu.getMenuItems().add(
            new MenuItem(MenuTittle.IMPORT_OF_ALL_ENTITIES.toString(),
                         () -> Printer.printInfo(carOfficeController.importEntities()), this.rootMenu));
    }

    private void createItemMastersMenu(Menu mastersMenu) {
        mastersMenu.setMenuItems(new ArrayList<>(Arrays.asList(
            new MenuItem(MenuTittle.ADD_MASTER.toString(), () -> Printer
                .printInfo(masterController.addMaster(ScannerUtil.getStringUser("Enter the name of master"))),
                         mastersMenu), new MenuItem(MenuTittle.DELETE_MASTER.toString(), () -> {
                if (isCheckMaster()) {
                    return;
                }
                deleteMaster();
            }, mastersMenu),
            new MenuItem(MenuTittle.SHOW_A_LIST_OF_MASTERS_SORTED_ALPHABETICALLY.toString(),
                         () -> Printer.printInfo(masterController.getMasterByAlphabet()), mastersMenu),
            new MenuItem(MenuTittle.SHOW_LIST_OF_MASTERS_SORTED_BY_BUSY.toString(),
                         () -> Printer.printInfo(masterController.getMasterByBusy()), mastersMenu),
            new MenuItem(MenuTittle.PREVIOUS_MENU.toString(), () -> Printer.printInfo(MenuTittle.GO_TO_MENU.toString()),
                         this.rootMenu))));
    }

    private void createItemPlacesMenu(Menu placesMenu) {
        placesMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SHOW_LIST_OF_PLACES.toString(),
                         () -> Printer.printInfo(placeController.getArrayPlace()), placesMenu));
        placesMenu.getMenuItems().add(
            new MenuItem(MenuTittle.ADD_PLACE.toString(), () ->
                Printer.printInfo(placeController.addPlace(ScannerUtil.getIntUser("Enter the number of place"))),
                         placesMenu));
        placesMenu.getMenuItems().add(new MenuItem(MenuTittle.DELETE_PLACE.toString(), () -> {
            if (isCheckPlace()) {
                return;
            }
            deleteGarage();
        }, placesMenu));
        placesMenu.getMenuItems().add(
            new MenuItem(MenuTittle.PREVIOUS_MENU.toString(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.toString()), this.rootMenu));
    }

    private void createItemOrderMenu(List<Menu> menus) {
        menus.get(0).setMenuItems(new ArrayList<>(Arrays.asList(
            new MenuItem(MenuTittle.LIST_OF_ORDERS.toString(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.toString()), menus.get(1)),
            new MenuItem(MenuTittle.LIST_OF_ORDERS_EXECUTED_AT_A_GIVEN_TIME.toString(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.toString()), menus.get(2)),
            new MenuItem(MenuTittle.LIST_OF_ORDERS_FOR_A_PERIOD_OF_TIME.toString(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.toString()), menus.get(3)),
            new MenuItem(MenuTittle.PREVIOUS_MENU.toString(), () -> Printer.printInfo(MenuTittle.GO_TO_MENU.toString()),
                         this.rootMenu)
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
        listOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SHOW_ORDERS.toString(), this::isCheckOrder, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem(MenuTittle.ADD_ORDER.toString(), this::addOrder, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem(MenuTittle.DELETE_THE_ORDER.toString(), () -> {
            if (isCheckOrder()) {
                return;
            }
            Printer.printInfo("0. Previous menu");
            deleteOrder();
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartTwo(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(new MenuItem(MenuTittle.CLOSE_THE_ORDER.toString(), () -> {
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
                message = orderController.closeOrder(index - INDEX_OFFSET);
                Printer.printInfo(message);
            }
        }, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem(MenuTittle.CANCEL_THE_ORDER.toString(), () -> {
            if (isCheckOrder()) {
                return;
            }
            Printer.printInfo("0. Previous menu");
            cancelOrder();
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartThree(Menu listOrderMenu) {
        listOrderMenu.getMenuItems()
            .add(new MenuItem(MenuTittle.TRANSFER_THE_ORDER_TO_EXECUTION_STATUS.toString(), () -> {
                if (isCheckOrder()) {
                    return;
                }
                Printer.printInfo("0. Previous menu");
                completeOrder();
            }, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem(MenuTittle.SHIFT_THE_LEAD_TIME.toString(), () -> {
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
                message = orderController.shiftLeadTime(
                    index - INDEX_OFFSET, ScannerUtil.getStringDateUser(
                        "Enter the planing time start to execute the order in format " +
                        "\"dd.MM.yyyy hh:mm\", example:\"10.10.2010 10:00\"", true),
                    ScannerUtil.getStringDateUser(
                        "Enter the lead time the order in format" + " \"dd.MM.yyyy hh:mm\"," +
                        " example:\"10.10.2010 10:00\"", true));
                Printer.printInfo(message);
            }
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartFour(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SHOW_ORDERS_SORT_BY_FILING_DATE.toString(),
                         () -> Printer.printInfo(orderController.getOrdersSortByFilingDate()), listOrderMenu));
        listOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SHOW_ORDERS_SORT_BY_EXECUTION_DATE.toString(),
                         () -> Printer.printInfo(orderController.getOrdersSortByExecutionDate()), listOrderMenu));
        listOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SHOW_ORDERS_SORT_BY_PLANNED_START_DATE.toString(),
                         () -> Printer.printInfo(orderController.getOrdersSortByPlannedStartDate()), listOrderMenu));
        listOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SHOW_ORDERS_SORT_BY_PRICE.toString(),
                         () -> Printer.printInfo(orderController.getOrdersSortByPrice()), listOrderMenu));
    }

    private void addItemListOrderMenuPartFive(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.GET_ORDERS_EXECUTED_CONCRETE_MASTER.toString(), () -> {
                int index;
                if (isCheckMaster()) {
                    return;
                }
                index = ScannerUtil.getIntUser("Enter the index number of the master to view orders:");
                String message = orderController.getMasterOrders(index - INDEX_OFFSET);
                Printer.printInfo(message);
            }, listOrderMenu));
    }

    private void addItemListOrderMenuPartSix(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.GET_A_MASTER_PERFORMING_A_SPECIFIC_ORDER.toString(), () -> {
                int index;
                if (isCheckOrder()) {
                    return;
                }
                index = ScannerUtil.getIntUser("Enter the index number of the order to view masters:");
                Printer.printInfo(orderController.getOrderMasters(index - INDEX_OFFSET));
            }, listOrderMenu));
    }

    private void addItemListOrderMenuPartSeven(Menu listOrderMenu, Menu ordersMenu) {
        listOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.PREVIOUS_MENU.toString(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.toString()), ordersMenu));
    }

    private void createItemExecutedOrderMenu(Menu executedOrderMenu, Menu ordersMenu) {
        executedOrderMenu.setMenuItems(new ArrayList<>(Arrays.asList(
            new MenuItem(MenuTittle.GET_LIST_OF_ORDERS_EXECUTED_AT_A_GIVEN_TIME_SORT_BY_FILING_DATE.toString(),
                         () -> Printer.printInfo(orderController.getExecuteOrderFilingDate()), executedOrderMenu),
            new MenuItem(MenuTittle.GET_LIST_OF_ORDERS_EXECUTED_AT_A_GIVEN_TIME_SORT_BY_EXECUTION_DATE.toString(),
                         () -> Printer.printInfo(orderController.getExecuteOrderExecutionDate()), executedOrderMenu),
            new MenuItem(MenuTittle.GET_LIST_OF_ORDERS_EXECUTED_AT_A_GIVEN_TIME_SORT_BY_PRICE.toString(),
                         () -> Printer.printInfo(orderController.getExecuteOrderExecutionDate()), executedOrderMenu),
            new MenuItem(MenuTittle.PREVIOUS_MENU.toString(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.toString()), ordersMenu))));
    }

    private void createItemPeriodOrderMenu(List<Menu> menus) {
        menus.get(0).setMenuItems(new ArrayList<>(Arrays.asList(
            new MenuItem(MenuTittle.COMPLETED_ORDERS.toString(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.toString()), menus.get(1)),
            new MenuItem(MenuTittle.DELETED_ORDERS.toString(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.toString()), menus.get(2)),
            new MenuItem(MenuTittle.CANCELED_ORDERS.toString(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.toString()), menus.get(3)),
            new MenuItem(MenuTittle.PREVIOUS_MENU.toString(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.toString()), menus.get(4)))));
    }

    private void createItemCompletedOrderMenu(Menu completedOrderMenu, Menu periodOrderMenu) {
        addItemCompletedOrderPartOne(completedOrderMenu);
        addItemCompletedOrderPartTwo(completedOrderMenu, periodOrderMenu);
    }

    private void addItemCompletedOrderPartOne(Menu completedOrderMenu) {
        completedOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SORT_BY_FILING_DATE.toString(),
                         () -> Printer.printInfo(orderController.getCompletedOrdersFilingDate(
                             ScannerUtil.getStringDateUser(
                                 "Enter the beginning date of period in format dd.mm.yyyy," +
                                 " example:\"10.10.2010 10:00\"", true),
                             ScannerUtil.getStringDateUser(
                                 "Enter the end date of period in format dd.mm.yyyy," +
                                 " example:\"10.10.2010 10:00\"", true))), completedOrderMenu));
        completedOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SORT_BY_EXECUTION_DATE.toString(),
                         () -> Printer.printInfo(orderController.getCompletedOrdersExecutionDate(
                             ScannerUtil.getStringDateUser(
                                 "Enter the beginning date of period in format dd.mm.yyyy," +
                                 " example:\"10.10.2010 10:00\"", true),
                             ScannerUtil.getStringDateUser(
                                 "Enter the end date of period in format dd.mm.yyyy," +
                                 " example:\"10.10.2010 10:00\"", true))), completedOrderMenu));
    }

    private void addItemCompletedOrderPartTwo(Menu completedOrderMenu, Menu periodOrderMenu) {
        completedOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SORT_BY_PRICE.toString(),
                         () -> Printer.printInfo(orderController.getCompletedOrdersPrice(
                             ScannerUtil.getStringDateUser(
                                 "Enter the beginning date of period in format dd.mm.yyyy," +
                                 " example:\"10.10.2010 10:00\"", true),
                             ScannerUtil.getStringDateUser(
                                 "Enter the end date of period in format dd.mm.yyyy," +
                                 " example:\"10.10.2010 10:00\"", true))), completedOrderMenu));
        completedOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.PREVIOUS_MENU.toString(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.toString()), periodOrderMenu));
    }

    private void createItemDeletedOrderMenu(Menu deletedOrderMenu, Menu periodOrderMenu) {
        addItemDeletedOrderPartOne(deletedOrderMenu);
        addItemDeletedOrderPartTwo(deletedOrderMenu, periodOrderMenu);
    }

    private void addItemDeletedOrderPartOne(Menu deletedOrderMenu) {
        deletedOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SORT_BY_FILING_DATE.toString(),
                         () -> Printer.printInfo(orderController.getDeletedOrdersFilingDate(
                             ScannerUtil.getStringDateUser(
                                 "Enter the beginning date of period in format dd.mm.yyyy," +
                                 " example:\"10.10.2010 10:00\"", true),
                             ScannerUtil.getStringDateUser(
                                 "Enter the end date of period in format dd.mm.yyyy," +
                                 " example:\"10.10.2010 10:00\"", true))), deletedOrderMenu));
        deletedOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SORT_BY_EXECUTION_DATE.toString(),
                         () -> Printer.printInfo(orderController.getDeletedOrdersExecutionDate(
                             ScannerUtil.getStringDateUser(
                                 "Enter the beginning date of period in format dd.mm.yyyy," +
                                 " example:\"10.10.2010 10:00\"", true),
                             ScannerUtil.getStringDateUser(
                                 "Enter the end date of period in format dd.mm.yyyy," +
                                 " example:\"10.10.2010 10:00\"", true))), deletedOrderMenu));
    }

    private void addItemDeletedOrderPartTwo(Menu deletedOrderMenu, Menu periodOrderMenu) {
        deletedOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SORT_BY_PRICE.toString(),
                         () -> Printer.printInfo(orderController.getDeletedOrdersPrice(
                             ScannerUtil.getStringDateUser(
                                 "Enter the beginning date of period in format dd.mm.yyyy," +
                                 " example:\"10.10.2010 10:00\"", true),
                             ScannerUtil.getStringDateUser(
                                 "Enter the end date of period in format dd.mm.yyyy," +
                                 " example:\"10.10.2010 10:00\"", true))), deletedOrderMenu));
        deletedOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.PREVIOUS_MENU.toString(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.toString()), periodOrderMenu));
    }

    private void createItemCanceledOrderMenu(Menu canceledOrderMenu, Menu periodOrderMenu) {
        addItemCanceledOrderPartOne(canceledOrderMenu);
        addItemCanceledOrderPartTwo(canceledOrderMenu, periodOrderMenu);
    }

    private void addItemCanceledOrderPartOne(Menu canceledOrderMenu) {
        canceledOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SORT_BY_FILING_DATE.toString(),
                         () -> Printer.printInfo(orderController.getCanceledOrdersFilingDate(
                             ScannerUtil.getStringDateUser(
                                 "Enter the beginning date of period in format dd.mm.yyyy," +
                                 " example:\"10.10.2010 10:00\"", true),
                             ScannerUtil.getStringDateUser(
                                 "Enter the end date of period in format dd.mm.yyyy," +
                                 " example:\"10.10.2010 10:00\"", true))), canceledOrderMenu));
        canceledOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SORT_BY_EXECUTION_DATE.toString(),
                         () -> Printer.printInfo(orderController.getCanceledOrdersExecutionDate(
                             ScannerUtil.getStringDateUser(
                                 "Enter the beginning date of period in format dd.mm.yyyy," +
                                 " example:\"10.10.2010 10:00\"", true),
                             ScannerUtil.getStringDateUser(
                                 "Enter the end date of period in format dd.mm.yyyy," +
                                 " example:\"10.10.2010 10:00\"", true))), canceledOrderMenu));
    }

    private void addItemCanceledOrderPartTwo(Menu canceledOrderMenu, Menu periodOrderMenu) {
        canceledOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SORT_BY_PRICE.toString(),
                         () -> Printer.printInfo(orderController.getCanceledOrdersPrice(
                             ScannerUtil.getStringDateUser("Enter the beginning date of period in " +
                                                           "format dd.mm.yyyy, example:\"10.10.2010 10:00\"", true),
                             ScannerUtil.getStringDateUser(
                                 "Enter the end date of period in format dd.mm.yyyy, " +
                                 "example:\"10.10.2010 10:00\"", true))), canceledOrderMenu));
        canceledOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.PREVIOUS_MENU.toString(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.toString()), periodOrderMenu));
    }

    private void addMaster(String delimiter, MasterController masterController) {
        Printer.printInfo("Add master:");
        TestData.getArrayMasterNames()
            .forEach(masterName -> Printer.printInfo(masterController.addMaster(masterName)));
        Printer.printInfo(delimiter);
    }

    private void addPlace(String delimiter, PlaceController placeController) {
        Printer.printInfo("Add place to service:");
        TestData.getArrayPlaceNumber()
            .forEach(placeNumber -> Printer.printInfo(placeController.addPlace(placeNumber)));
        Printer.printInfo(delimiter);
    }

    private void addOrderDate(OrderController orderController) {
        int indexMaster = 0;
        for (int i = 0; i < TestData.getArrayAutomaker().size(); i++) {
            Printer.printInfo(
                orderController.addOrder(TestData.getArrayAutomaker().get(i), TestData.getArrayModel().get(i),
                                         TestData.getArrayRegistrationNumber().get(i)));
            for (int j = 0; j < 2; j++) {
                Printer.printInfo(orderController.addOrderMasters(indexMaster));
                indexMaster++;
                if (indexMaster == TestData.getArrayMasterNames().size()) {
                    indexMaster = 0;
                }
            }
            Printer.printInfo(
                orderController.addOrderDeadlines(
                    TestData.getArrayExecutionStartTime().get(i), TestData.getArrayLeadTime().get(i)));
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
            message = orderController.deleteOrder(index - INDEX_OFFSET);
            Printer.printInfo(message);
        }
    }

    private void deleteMaster() {
        int index = ScannerUtil.getIntUser("Enter the index number of the master to delete:");
        Printer.printInfo(masterController.deleteMaster(index - INDEX_OFFSET));
    }

    private void deleteGarage() {
        int index = ScannerUtil.getIntUser("Enter the index number of the place to delete:");
        Printer.printInfo(placeController.deletePlace(index - INDEX_OFFSET));
    }

    private void completeOrder() {
        String message = "";
        int index;
        while (!message.equals(" - the order has been transferred to execution status")) {
            index = ScannerUtil.getIntUser("Enter the index number of the order to change status:");
            if (index == 0) {
                return;
            }
            message = orderController.completeOrder(index - INDEX_OFFSET);
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
            message = orderController.cancelOrder(index - INDEX_OFFSET);
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
        int index = MAX_INDEX;
        while (quit == 0 && index == MAX_INDEX) {
            List<Integer> statusInt = addMasters(quit);
            quit = statusInt.get(0);
            index = statusInt.get(1);
            Printer.printInfo("You must add at least one master!!!");
        }
    }

    private List<Integer> addMasters(int quit) {
        String message = "";
        int index = MAX_INDEX;
        boolean userAnswer;
        while (!message.equals("masters add successfully")) {
            index = ScannerUtil.getIntUser("Enter the index number of the master to add:");
            if (index == 0) {
                break;
            }
            message = orderController.addOrderMasters(index - INDEX_OFFSET);
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
            message = orderController.addOrderPlace(index - INDEX_OFFSET, executionStartTime);
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