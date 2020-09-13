package com.senla.carservice.ui.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.senla.carservice.controller.CarOfficeController;
import com.senla.carservice.controller.MasterController;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.controller.PlaceController;
import com.senla.carservice.ui.util.Printer;
import com.senla.carservice.ui.util.ScannerUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Builder {

    private static final int INDEX_OFFSET = 1;
    private static final int MAX_INDEX = 999;
    private Menu rootMenu;
    @Autowired
    private CarOfficeController carOfficeController;
    @Autowired
    private MasterController masterController;
    @Autowired
    private OrderController orderController;
    @Autowired
    private PlaceController placeController;

    public Builder() {
    }

    public void buildMenu() {
        this.rootMenu = new Menu(MenuTittle.CAR_SERVICE_MENU.getValue());
        Menu mastersMenu = new Menu(MenuTittle.MASTERS.getValue());
        Menu ordersMenu = new Menu(MenuTittle.ORDERS.getValue());
        Menu placesMenu = new Menu(MenuTittle.PLACES.getValue());
        Menu listOrderMenu = new Menu(MenuTittle.ORDERS_LIST.getValue());
        Menu executedOrderMenu = new Menu(MenuTittle.LIST_OF_ORDERS_CURRENTLY_BEING_EXECUTED.getValue());
        Menu periodOrderMenu = new Menu(MenuTittle.ORDERS_LIST_FOR_A_PERIOD_OF_TIME.getValue());
        Menu completedOrderMenu = new Menu(MenuTittle.COMPLETED_ORDERS.getValue());
        Menu deletedOrderMenu = new Menu(MenuTittle.DELETED_ORDERS.getValue());
        Menu canceledOrderMenu = new Menu(MenuTittle.CANCELED_ORDERS.getValue());
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
            new MenuItem(MenuTittle.MASTERS.getValue(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.getValue()), mastersMenu));
        this.rootMenu.getMenuItems().add(
            new MenuItem(MenuTittle.ORDERS.getValue(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.getValue()), ordersMenu));
        this.rootMenu.getMenuItems().add(
            new MenuItem(MenuTittle.PLACES.getValue(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.getValue()), placesMenu));
        this.rootMenu.getMenuItems().add(
            new MenuItem(MenuTittle.GET_THE_NUMBER_OF_AVAILABLE_SEATS_AT_THE_CAR_SERVICE.getValue(), () -> {
                String date =
                    ScannerUtil.getStringDateUser("Enter the date in format dd.mm.yyyy, example:\"10.10.2010\"", false);
                Printer.printInfo(carOfficeController.getFreePlacesMastersByDate(date));
            }, this.rootMenu));
    }

    private void setMenuItemRootMenuPart() {
        this.rootMenu.getMenuItems().add(new MenuItem(MenuTittle.GET_THE_CLOSEST_FREE_DATE.getValue(),
                                                      () -> Printer.printInfo(carOfficeController.getNearestFreeDate()),
                                                      this.rootMenu));
        this.rootMenu.getMenuItems().add(
            new MenuItem(MenuTittle.EXPORT_OF_ALL_ENTITIES.getValue(),
                         () -> Printer.printInfo(carOfficeController.exportEntities()), this.rootMenu));
        this.rootMenu.getMenuItems().add(
            new MenuItem(MenuTittle.IMPORT_OF_ALL_ENTITIES.getValue(),
                         () -> Printer.printInfo(carOfficeController.importEntities()), this.rootMenu));
    }

    private void createItemMastersMenu(Menu mastersMenu) {
        mastersMenu.setMenuItems(new ArrayList<>(Arrays.asList(
            new MenuItem(MenuTittle.ADD_MASTER.getValue(), () -> Printer
                .printInfo(masterController.addMaster(ScannerUtil.getStringUser("Enter the name of master", false))),
                         mastersMenu), new MenuItem(MenuTittle.DELETE_MASTER.getValue(), () -> {
                if (isCheckMaster()) {
                    return;
                }
                deleteMaster();
            }, mastersMenu),
            new MenuItem(MenuTittle.SHOW_A_LIST_OF_MASTERS_SORTED_ALPHABETICALLY.getValue(),
                         () -> Printer.printInfo(masterController.getMasterByAlphabet()), mastersMenu),
            new MenuItem(MenuTittle.SHOW_LIST_OF_MASTERS_SORTED_BY_BUSY.getValue(),
                         () -> Printer.printInfo(masterController.getMasterByBusy()), mastersMenu),
            new MenuItem(MenuTittle.PREVIOUS_MENU.getValue(), () -> Printer.printInfo(MenuTittle.GO_TO_MENU.getValue()),
                         this.rootMenu))));
    }

    private void createItemPlacesMenu(Menu placesMenu) {
        placesMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SHOW_LIST_OF_PLACES.getValue(),
                         () -> Printer.printInfo(placeController.getPlaces()), placesMenu));
        placesMenu.getMenuItems().add(
            new MenuItem(MenuTittle.ADD_PLACE.getValue(), () ->
                Printer.printInfo(placeController.addPlace(ScannerUtil.getIntUser("Enter the number of place"))),
                         placesMenu));
        placesMenu.getMenuItems().add(new MenuItem(MenuTittle.DELETE_PLACE.getValue(), () -> {
            if (isCheckPlace()) {
                return;
            }
            deletePlace();
        }, placesMenu));
        placesMenu.getMenuItems().add(
            new MenuItem(MenuTittle.PREVIOUS_MENU.getValue(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.getValue()), this.rootMenu));
    }

    private void createItemOrderMenu(List<Menu> menus) {
        menus.get(0).setMenuItems(new ArrayList<>(Arrays.asList(
            new MenuItem(MenuTittle.LIST_OF_ORDERS.getValue(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.getValue()), menus.get(1)),
            new MenuItem(MenuTittle.LIST_OF_ORDERS_EXECUTED_AT_A_GIVEN_TIME.getValue(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.getValue()), menus.get(2)),
            new MenuItem(MenuTittle.LIST_OF_ORDERS_FOR_A_PERIOD_OF_TIME.getValue(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.getValue()), menus.get(3)),
            new MenuItem(MenuTittle.PREVIOUS_MENU.getValue(), () -> Printer.printInfo(MenuTittle.GO_TO_MENU.getValue()),
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
            new MenuItem(MenuTittle.SHOW_ORDERS.getValue(), this::isCheckOrder, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem(MenuTittle.ADD_ORDER.getValue(), this::addOrder, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem(MenuTittle.DELETE_THE_ORDER.getValue(), () -> {
            if (isCheckOrder()) {
                return;
            }
            deleteOrder();
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartTwo(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(new MenuItem(MenuTittle.CLOSE_THE_ORDER.getValue(), () -> {
            if (isCheckOrder()) {
                return;
            }
            List<String> listData = orderController.getOrders();
            Printer.printInfo(listData.get(0));
            listData.remove(0);
            List<Long> listId = getIdFromListString(listData);
            int index;
            String message = "";
            while (!message.equals(" -the order has been completed.")) {
                index = ScannerUtil.getIntUser("Enter the index number of the order to close:") - INDEX_OFFSET;
                if (index + INDEX_OFFSET == 0) {
                    return;
                } else if (index >= listId.size()) {
                    Printer.printInfo("There is no such order");
                    continue;
                }
                message = orderController.closeOrder(listId.get(index));
                Printer.printInfo(message);
            }
        }, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem(MenuTittle.CANCEL_THE_ORDER.getValue(), () -> {
            if (isCheckOrder()) {
                return;
            }
            cancelOrder();
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartThree(Menu listOrderMenu) {
        listOrderMenu.getMenuItems()
            .add(new MenuItem(MenuTittle.TRANSFER_THE_ORDER_TO_EXECUTION_STATUS.getValue(), () -> {
                if (isCheckOrder()) {
                    return;
                }
                completeOrder();
            }, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem(MenuTittle.SHIFT_THE_LEAD_TIME.getValue(), () -> {
            if (isCheckOrder()) {
                return;
            }
            List<String> listData = orderController.getOrders();
            Printer.printInfo(listData.get(0));
            listData.remove(0);
            List<Long> listId = getIdFromListString(listData);
            String message = "";
            while (!message.equals(" -the order lead time has been changed.")) {
                int index = ScannerUtil.getIntUser("Enter the index number of the order to shift time:") -
                            INDEX_OFFSET;
                if (index + INDEX_OFFSET == 0) {
                    return;
                } else if (index >= listId.size()) {
                    Printer.printInfo("There is no such order");
                    continue;
                }
                message = orderController.shiftLeadTime(
                    listId.get(index), ScannerUtil.getStringDateUser(
                        "Enter the planing time start to execute the order in format " +
                        "\"yyyy-mm-dd hh:mm\", example:\"2010-10-10 10:00\"", true),
                    ScannerUtil.getStringDateUser(
                        "Enter the lead time the order in format" + " \"yyyy-mm-dd hh:mm\"," +
                        " example:\"2010-10-10 10:00\"", true));
                Printer.printInfo(message);
            }
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartFour(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SHOW_ORDERS_SORT_BY_FILING_DATE.getValue(),
                         () -> Printer.printInfo(orderController.getOrdersSortByFilingDate()), listOrderMenu));
        listOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SHOW_ORDERS_SORT_BY_EXECUTION_DATE.getValue(),
                         () -> Printer.printInfo(orderController.getOrdersSortByExecutionDate()), listOrderMenu));
        listOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SHOW_ORDERS_SORT_BY_PLANNED_START_DATE.getValue(),
                         () -> Printer.printInfo(orderController.getOrdersSortByPlannedStartDate()), listOrderMenu));
        listOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SHOW_ORDERS_SORT_BY_PRICE.getValue(),
                         () -> Printer.printInfo(orderController.getOrdersSortByPrice()), listOrderMenu));
    }

    private void addItemListOrderMenuPartFive(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.GET_ORDERS_EXECUTED_CONCRETE_MASTER.getValue(), () -> {
                int index;
                if (isCheckMaster()) {
                    return;
                }
                List<String> listData = masterController.getMasters();
                Printer.printInfo(listData.get(0));
                listData.remove(0);
                List<Long> listId = getIdFromListString(listData);
                index = ScannerUtil.getIntUser("Enter the index number of the master to view orders:") -
                        INDEX_OFFSET;
                if (index >= listId.size() || index < 0) {
                    Printer.printInfo("There is no such master");
                } else {
                    Printer.printInfo(orderController.getMasterOrders(listId.get(index)));
                }
            }, listOrderMenu));
    }

    private void addItemListOrderMenuPartSix(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.GET_A_MASTER_PERFORMING_A_SPECIFIC_ORDER.getValue(), () -> {
                int index;
                if (isCheckOrder()) {
                    return;
                }
                List<String> listData = orderController.getOrders();
                Printer.printInfo(listData.get(0));
                listData.remove(0);
                List<Long> listId = getIdFromListString(listData);
                index = ScannerUtil.getIntUser("Enter the index number of the order to view masters:") -
                        INDEX_OFFSET;
                if (index >= listId.size() || index < 0) {
                    Printer.printInfo("There is no such order");
                } else {
                    Printer.printInfo(orderController.getOrderMasters(listId.get(index)));
                }
            }, listOrderMenu));
    }

    private void addItemListOrderMenuPartSeven(Menu listOrderMenu, Menu ordersMenu) {
        listOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.PREVIOUS_MENU.getValue(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.getValue()), ordersMenu));
    }

    private void createItemExecutedOrderMenu(Menu executedOrderMenu, Menu ordersMenu) {
        executedOrderMenu.setMenuItems(new ArrayList<>(Arrays.asList(
            new MenuItem(MenuTittle.GET_LIST_OF_ORDERS_EXECUTED_AT_A_GIVEN_TIME_SORT_BY_FILING_DATE.getValue(),
                         () -> Printer.printInfo(orderController.getExecuteOrderFilingDate()), executedOrderMenu),
            new MenuItem(MenuTittle.GET_LIST_OF_ORDERS_EXECUTED_AT_A_GIVEN_TIME_SORT_BY_EXECUTION_DATE.getValue(),
                         () -> Printer.printInfo(orderController.getExecuteOrderExecutionDate()), executedOrderMenu),
            new MenuItem(MenuTittle.GET_LIST_OF_ORDERS_EXECUTED_AT_A_GIVEN_TIME_SORT_BY_PRICE.getValue(),
                         () -> Printer.printInfo(orderController.getExecuteOrderExecutionDate()), executedOrderMenu),
            new MenuItem(MenuTittle.PREVIOUS_MENU.getValue(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.getValue()), ordersMenu))));
    }

    private void createItemPeriodOrderMenu(List<Menu> menus) {
        menus.get(0).setMenuItems(new ArrayList<>(Arrays.asList(
            new MenuItem(MenuTittle.COMPLETED_ORDERS.getValue(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.getValue()), menus.get(1)),
            new MenuItem(MenuTittle.DELETED_ORDERS.getValue(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.getValue()), menus.get(2)),
            new MenuItem(MenuTittle.CANCELED_ORDERS.getValue(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.getValue()), menus.get(3)),
            new MenuItem(MenuTittle.PREVIOUS_MENU.getValue(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.getValue()), menus.get(4)))));
    }

    private void createItemCompletedOrderMenu(Menu completedOrderMenu, Menu periodOrderMenu) {
        addItemCompletedOrderPartOne(completedOrderMenu);
        addItemCompletedOrderPartTwo(completedOrderMenu, periodOrderMenu);
    }

    private void addItemCompletedOrderPartOne(Menu completedOrderMenu) {
        completedOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SORT_BY_FILING_DATE.getValue(),
                         () -> Printer.printInfo(orderController.getCompletedOrdersFilingDate(
                             ScannerUtil.getStringDateUser(
                                 "Enter the beginning date of period in format yyyy-mm-dd hh:mm," +
                                 " example:\"2010-10-10 10:00\"", true),
                             ScannerUtil.getStringDateUser(
                                 "Enter the end date of period in format yyyy-mm-dd hh:mm," +
                                 " example:\"2010-10-10 10:00\"", true))), completedOrderMenu));
        completedOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SORT_BY_EXECUTION_DATE.getValue(),
                         () -> Printer.printInfo(orderController.getCompletedOrdersExecutionDate(
                             ScannerUtil.getStringDateUser(
                                 "Enter the beginning date of period in format yyyy-mm-dd hh:mm," +
                                 " example:\"2010-10-10 10:00\"", true),
                             ScannerUtil.getStringDateUser(
                                 "Enter the end date of period in format yyyy-mm-dd hh:mm," +
                                 " example:\"2010-10-10 10:00\"", true))), completedOrderMenu));
    }

    private void addItemCompletedOrderPartTwo(Menu completedOrderMenu, Menu periodOrderMenu) {
        completedOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SORT_BY_PRICE.getValue(),
                         () -> Printer.printInfo(orderController.getCompletedOrdersPrice(
                             ScannerUtil.getStringDateUser(
                                 "Enter the beginning date of period in format yyyy-mm-dd hh:mm," +
                                 " example:\"2010-10-10 10:00\"", true),
                             ScannerUtil.getStringDateUser(
                                 "Enter the end date of period in format yyyy-mm-dd hh:mm," +
                                 " example:\"2010-10-10 10:00\"", true))), completedOrderMenu));
        completedOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.PREVIOUS_MENU.getValue(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.getValue()), periodOrderMenu));
    }

    private void createItemDeletedOrderMenu(Menu deletedOrderMenu, Menu periodOrderMenu) {
        addItemDeletedOrderPartOne(deletedOrderMenu);
        addItemDeletedOrderPartTwo(deletedOrderMenu, periodOrderMenu);
    }

    private void addItemDeletedOrderPartOne(Menu deletedOrderMenu) {
        deletedOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SORT_BY_FILING_DATE.getValue(),
                         () -> Printer.printInfo(orderController.getDeletedOrdersFilingDate(
                             ScannerUtil.getStringDateUser(
                                 "Enter the beginning date of period in format yyyy-mm-dd hh:mm," +
                                 " example:\"2010-10-10 10:00\"", true),
                             ScannerUtil.getStringDateUser(
                                 "Enter the end date of period in format yyyy-mm-dd hh:mm," +
                                 " example:\"2010-10-10 10:00\"", true))), deletedOrderMenu));
        deletedOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SORT_BY_EXECUTION_DATE.getValue(),
                         () -> Printer.printInfo(orderController.getDeletedOrdersExecutionDate(
                             ScannerUtil.getStringDateUser(
                                 "Enter the beginning date of period in format yyyy-mm-dd hh:mm," +
                                 " example:\"2010-10-10 10:00\"", true),
                             ScannerUtil.getStringDateUser(
                                 "Enter the end date of period in format yyyy-mm-dd hh:mm," +
                                 " example:\"2010-10-10 10:00\"", true))), deletedOrderMenu));
    }

    private void addItemDeletedOrderPartTwo(Menu deletedOrderMenu, Menu periodOrderMenu) {
        deletedOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SORT_BY_PRICE.getValue(),
                         () -> Printer.printInfo(orderController.getDeletedOrdersPrice(
                             ScannerUtil.getStringDateUser(
                                 "Enter the beginning date of period in format yyyy-mm-dd hh:mm," +
                                 " example:\"2010-10-10 10:00\"", true),
                             ScannerUtil.getStringDateUser(
                                 "Enter the end date of period in format yyyy-mm-dd hh:mm," +
                                 " example:\"2010-10-10 10:00\"", true))), deletedOrderMenu));
        deletedOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.PREVIOUS_MENU.getValue(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.getValue()), periodOrderMenu));
    }

    private void createItemCanceledOrderMenu(Menu canceledOrderMenu, Menu periodOrderMenu) {
        addItemCanceledOrderPartOne(canceledOrderMenu);
        addItemCanceledOrderPartTwo(canceledOrderMenu, periodOrderMenu);
    }

    private void addItemCanceledOrderPartOne(Menu canceledOrderMenu) {
        canceledOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SORT_BY_FILING_DATE.getValue(),
                         () -> Printer.printInfo(orderController.getCanceledOrdersFilingDate(
                             ScannerUtil.getStringDateUser(
                                 "Enter the beginning date of period in format yyyy-mm-dd hh:mm," +
                                 " example:\"2010-10-10 10:00\"", true),
                             ScannerUtil.getStringDateUser(
                                 "Enter the end date of period in format yyyy-mm-dd hh:mm," +
                                 " example:\"2010-10-10 10:00\"", true))), canceledOrderMenu));
        canceledOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SORT_BY_EXECUTION_DATE.getValue(),
                         () -> Printer.printInfo(orderController.getCanceledOrdersExecutionDate(
                             ScannerUtil.getStringDateUser(
                                 "Enter the beginning date of period in format yyyy-mm-dd hh:mm," +
                                 " example:\"2010-10-10 10:00\"", true),
                             ScannerUtil.getStringDateUser(
                                 "Enter the end date of period in format yyyy-mm-dd hh:mm," +
                                 " example:\"2010-10-10 10:00\"", true))), canceledOrderMenu));
    }

    private void addItemCanceledOrderPartTwo(Menu canceledOrderMenu, Menu periodOrderMenu) {
        canceledOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SORT_BY_PRICE.getValue(),
                         () -> Printer.printInfo(orderController.getCanceledOrdersPrice(
                             ScannerUtil.getStringDateUser("Enter the beginning date of period in " +
                                                           "format dd.mm.yyyy, example:\"10.10.2010 10:00\"", true),
                             ScannerUtil.getStringDateUser(
                                 "Enter the end date of period in format yyyy-mm-dd hh:mm, " +
                                 "example:\"2010-10-10 10:00\"", true))), canceledOrderMenu));
        canceledOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.PREVIOUS_MENU.getValue(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.getValue()), periodOrderMenu));
    }


    private void deleteOrder() {
        List<String> listData = orderController.getOrders();
        Printer.printInfo(listData.get(0));
        listData.remove(0);
        List<Long> listId = getIdFromListString(listData);
        String message = "";
        int index;
        while (!message.equals(" -the order has been deleted.")) {
            index = ScannerUtil.getIntUser("Enter the index number of the order to delete:") - INDEX_OFFSET;
            if (index + INDEX_OFFSET == 0) {
                return;
            } else if (index >= listId.size()) {
                Printer.printInfo("There is no such order");
                continue;
            }
            message = orderController.deleteOrder(listId.get(index));
            Printer.printInfo(message);
        }
    }

    private void deleteMaster() {
        List<String> listData = masterController.getMasters();
        Printer.printInfo(listData.get(0));
        listData.remove(0);
        List<Long> listId = getIdFromListString(listData);
        int index = ScannerUtil.getIntUser("Enter the index number of the master to delete:") - INDEX_OFFSET;
        if (index >= listId.size() || index < 0) {
            Printer.printInfo("There is no such master");
        } else {
            Printer.printInfo(masterController.deleteMaster(listId.get(index)));
        }
    }

    private void deletePlace() {
        List<String> listData = placeController.getPlacesWithId();
        Printer.printInfo(listData.get(0));
        listData.remove(0);
        List<Long> listId = getIdFromListString(listData);
        int index = ScannerUtil.getIntUser("Enter the index number of the place to delete:") - INDEX_OFFSET;
        if (index >= listId.size() || index < 0) {
            Printer.printInfo("There is no such place");
        } else {
            Printer.printInfo(placeController.deletePlace(listId.get(index)));
        }
    }

    private void completeOrder() {
        List<String> listData = orderController.getOrders();
        Printer.printInfo(listData.get(0));
        Printer.printInfo("0. Previous menu");
        listData.remove(0);
        List<Long> listId = getIdFromListString(listData);
        String message = "";
        int index;
        while (!message.equals(" - the order has been transferred to execution status")) {
            index = ScannerUtil.getIntUser("Enter the index number of the order to change status:") -
                    INDEX_OFFSET;
            if (index + INDEX_OFFSET == 0) {
                return;
            } else if (index >= listId.size()) {
                Printer.printInfo("There is no such order");
                continue;
            }
            message = orderController.completeOrder(listId.get(index));
            Printer.printInfo(message);
        }
    }

    private void cancelOrder() {
        List<String> listData = orderController.getOrders();
        Printer.printInfo(listData.get(0));
        Printer.printInfo("0. Previous menu");
        listData.remove(0);
        List<Long> listId = getIdFromListString(listData);
        String message = "";
        int index;
        while (!message.equals(" -the order has been canceled.")) {
            index = ScannerUtil.getIntUser("Enter the index number of the order to cancel:") - INDEX_OFFSET;
            if (index + INDEX_OFFSET == 0) {
                return;
            } else if (index >= listId.size()) {
                Printer.printInfo("There is no such order");
                continue;
            }
            message = orderController.cancelOrder(listId.get(index));
            Printer.printInfo(message);
        }
    }

    private void addOrder() {
        String message = "";
        while (!message.equals("order add successfully!")) {
            if (isCheckMaster() || isCheckPlace()) {
                return;
            }
            String automaker = ScannerUtil.getStringUser("Enter the automaker of car", false);
            String model = ScannerUtil.getStringUser("Enter the model of car", false);
            String registrationNumber = ScannerUtil.getStringUser(
                "Enter the registration number of car, example: 1111 AB-7", true);
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
                "format \"yyyy-mm-dd hh:mm\", example:\"2010-10-10 10:00\"", true);
            leadTime = ScannerUtil.getStringDateUser(
                "Enter the lead time the order in format " +
                "\"yyyy-mm-dd hh:mm\", example:\"2010-10-10 10:00\"", true);
            message = orderController.addOrderDeadlines(executionStartTime, leadTime);
            Printer.printInfo(message);
        }
        return executionStartTime;
    }

    private void addMastersOrder(String executionStartTime) {
        List<String> listData = masterController.getFreeMasters(executionStartTime);
        Printer.printInfo(listData.get(0));
        Printer.printInfo("0. Stop adding");
        listData.remove(0);
        List<Long> listId = getIdFromListString(listData);
        int quit = 0;
        int index;
        int number_masters = 0;
        while (quit == 0) {
            List<Integer> statusInt = addMasters(quit, number_masters, listId);
            quit = statusInt.get(0);
            index = statusInt.get(1);
            number_masters = statusInt.get(2);
            if (number_masters == 0 && index == 0) {
                Printer.printInfo("You must add at least one master!!!");
            }
        }
    }

    private List<Integer> addMasters(int quit, int numberMasters, List<Long> listId) {
        String message = "";
        int index = MAX_INDEX;
        boolean userAnswer = false;
        while (!message.equals("masters add successfully")) {
            index = ScannerUtil.getIntUser("Enter the index number of the master to add:") - INDEX_OFFSET;
            if (index + INDEX_OFFSET == 0) {
                break;
            } else if (index >= listId.size()) {
                Printer.printInfo("There is no such master");
                continue;
            }
            message = orderController.addOrderMasters(listId.get(index));
            Printer.printInfo(message);
            if (message.equals("masters add successfully")) {
                numberMasters++;
                userAnswer = ScannerUtil.isAnotherMaster();
            }
            if (userAnswer) {
                quit++;
                break;
            }
        }
        return new ArrayList<>(Arrays.asList(quit, index, numberMasters));
    }

    private void addPlaceOrder(String executionStartTime) {
        List<String> listData = placeController.getFreePlacesByDate(executionStartTime);
        String message = "";
        int index;
        Printer.printInfo(listData.get(0));
        Printer.printInfo("0. Stop adding");
        listData.remove(0);
        List<Long> listId = getIdFromListString(listData);
        while (!message.equals("place add to order successfully")) {
            index = ScannerUtil.getIntUser("Enter the index number of the place to add in order:") -
                    INDEX_OFFSET;
            message = orderController.addOrderPlace(listId.get(index));
            Printer.printInfo(message);
        }
    }

    private List<Long> getIdFromListString(List<String> listStringId) {
        return listStringId.stream()
            .map(Long::parseLong)
            .collect(Collectors.toList());
    }

    private boolean isCheckOrder() {
        String message = orderController.checkOrders();
        boolean status = message.equals("verification was successfully");
        if (!status) {
            Printer.printInfo(message);
        }
        return !status;
    }

    private boolean isCheckMaster() {
        String message = masterController.checkMasters();
        boolean status = message.equals("verification was successfully");
        if (!status) {
            Printer.printInfo(message);
        }
        return !status;
    }

    private boolean isCheckPlace() {
        String message = placeController.checkPlaces();
        boolean status = message.equals("verification was successfully");
        if (!status) {
            Printer.printInfo(message);
        }
        return !status;
    }
}