package com.senla.carservice.ui.menu;

import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;
import com.senla.carservice.dto.PlaceDto;
import com.senla.carservice.ui.client.CarOfficeClient;
import com.senla.carservice.ui.client.MasterClient;
import com.senla.carservice.ui.client.OrderClient;
import com.senla.carservice.ui.client.PlaceClient;
import com.senla.carservice.ui.exception.BusinessException;
import com.senla.carservice.ui.client.CarOfficeClientImpl;
import com.senla.carservice.ui.client.MasterClientImpl;
import com.senla.carservice.ui.client.OrderClientImpl;
import com.senla.carservice.ui.client.PlaceClientImpl;
import com.senla.carservice.ui.util.StringMaster;
import com.senla.carservice.ui.util.StringOrder;
import com.senla.carservice.ui.util.StringPlaces;
import com.senla.carservice.util.DateUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.senla.carservice.ui.util.Printer;
import com.senla.carservice.ui.util.ScannerUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@NoArgsConstructor
@Slf4j
public class Builder {

    private static final int INDEX_OFFSET = 1;
    private static final String DATE_INPUT_HEADER = "Enter the date in format dd.mm.yyyy, example:\"10.10.2010\"";
    private static final String NAME_INPUT_HEADER = "Enter the name of master";
    private static final String NUMBER_PLACE_INPUT_HEADER = "Enter the number of place";
    private static final String ORDER_SUCCESS_SERVER_MESSAGE = "The order has been completed successfully";
    private static final String ORDER_CLOSE_INDEX_HEADER = "Enter the index number of the order to close:";
    private static final String WARNING_ORDER_MESSAGE = "There is no such order";
    private static final String ORDER_TIME_CHANGE_SUCCESS_SERVER_MESSAGE = "The order lead time has been changed " +
        "successfully";
    private static final String ORDER_SHIFT_TIME_INDEX_HEADER = "Enter the index number of the order to shift time:";
    private static final String PLANING_TIME_START_INPUT_HEADER = "Enter the planing time start to execute the order in " +
        "format \"yyyy-mm-dd hh:mm\", example:\"2010-10-10 10:00\"";
    private static final String LEAD_TIME_INPUT_HEADER = "Enter the lead time the order in format \"yyyy-mm-dd hh:mm\", " +
        "example:\"2010-10-10 10:00\"";
    private static final String MASTER_INDEX_INPUT_HEADER = "Enter the index number of the master to view orders:";
    private static final String WARNING_MASTER_MESSAGE = "There is no such master";
    private static final String ORDER_INDEX_FOR_VIEW_MASTERS_INPUT_HEADER = "Enter the index number of the order " +
        "to view masters:";
    private static final String START_DATE_INPUT_HEADER = "Enter the beginning date of period in format yyyy-mm-dd hh:mm," +
        " example:\"2010-10-10 10:00\"";
    private static final String END_DATE_INPUT_HEADER = "Enter the end date of period in format yyyy-mm-dd hh:mm," +
        " example:\"2010-10-10 10:00\"";
    private static final String STOP_DELETING_MENU_ITEM = "0. Stop deleting";
    private static final String ORDER_DELETE_SUCCESS_SERVER_MESSAGE = "The order has been deleted successfully";
    private static final String ORDER_DELETE_INDEX_HEADER = "Enter the index number of the order to delete:";
    private static final String MASTER_DELETE_INDEX_HEADER = "Enter the index number of the master to delete:";
    private static final String PLACE_DELETE_INDEX_HEADER = "Enter the index number of the place to delete:";
    private static final String WARNING_PLACE_MESSAGE = "There is no such place";
    private static final String PREVIOUS_MENU_MENU_ITEM = "0. Previous menu";
    private static final String TRANSFERRED_TO_EXECUTION_STATUS_SUCCESS_SERVER_MESSAGE = "The order has been transferred" +
        " to execution status successfully";
    private static final String ORDER_CHANGE_STATUS_INDEX_HEADER = "Enter the index number of the order to change status:";
    private static final String ORDER_CANCEL_SUCCESS_SERVER_MESSAGE = "The order has been canceled successfully";
    private static final String ORDER_CANCEL_INDEX_HEADER = "Enter the index number of the order to cancel:";
    private static final String ORDER_PRICE_INPUT_HEADER = "Enter the price:";
    private static final String ORDER_AUTOMAKER_INPUT_HEADER = "Enter the automaker of car:";
    private static final String ORDER_MODEL_INPUT_HEADER = "Enter the model of car:";
    private static final String ORDER_REGISTRATION_NUMBER_INPUT_HEADER = "Enter the registration number of car, " +
        "example: 1111 AB-7";
    private static final String VERIFICATION_SUCCESS_SERVER_MESSAGE = "verification was successfully";
    private static final String STOP_ADDING_MENU_ITEM = "0. Stop adding";
    private static final String WARNING_COUNT_MASTER_MESSAGE = "You must add at least one master!!!";
    private static final String WARNING_EXIST_MASTER_MESSAGE = "This master already exists";
    private static final String MASTER_ADD_INDEX_HEADER = "Enter the index number of the master to add:";
    private static final String WARNING_DELETE_MASTER_MESSAGE = "Master has been deleted";
    private static final String PLACE_ADD_INDEX_HEADER = "Enter the index number of the place to add in order:";

    private Menu rootMenu;
    @Autowired
    private CarOfficeClient carOfficeService;
    @Autowired
    private MasterClient masterService;
    @Autowired
    private OrderClient orderService;
    @Autowired
    private PlaceClient placeService;

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
                    ScannerUtil.getStringDateUser(DATE_INPUT_HEADER, false);
                Printer.printInfo(carOfficeService.getFreePlacesMastersByDate(date));
            }, this.rootMenu));
    }

    private void setMenuItemRootMenuPart() {
        this.rootMenu.getMenuItems().add(new MenuItem(MenuTittle.GET_THE_CLOSEST_FREE_DATE.getValue(),
                                                      () -> Printer.printInfo(carOfficeService.getNearestFreeDate()),
                                                      this.rootMenu));
        this.rootMenu.getMenuItems().add(
            new MenuItem(MenuTittle.EXPORT_OF_ALL_ENTITIES.getValue(),
                         () -> Printer.printInfo(carOfficeService.exportEntities()), this.rootMenu));
        this.rootMenu.getMenuItems().add(
            new MenuItem(MenuTittle.IMPORT_OF_ALL_ENTITIES.getValue(),
                         () -> Printer.printInfo(carOfficeService.importEntities()), this.rootMenu));
    }

    private void createItemMastersMenu(Menu mastersMenu) {
        mastersMenu.setMenuItems(new ArrayList<>(Arrays.asList(
            new MenuItem(MenuTittle.ADD_MASTER.getValue(), () -> Printer
                .printInfo(masterService.addMaster(ScannerUtil.getStringUser(NAME_INPUT_HEADER, false))),
                         mastersMenu), new MenuItem(MenuTittle.DELETE_MASTER.getValue(), () -> {
                if (isCheckMasters()) {
                    return;
                }
                deleteMaster();
            }, mastersMenu),
            new MenuItem(MenuTittle.SHOW_A_LIST_OF_MASTERS_SORTED_ALPHABETICALLY.getValue(),
                         () -> Printer.printInfo(masterService.getMasterByAlphabet()), mastersMenu),
            new MenuItem(MenuTittle.SHOW_LIST_OF_MASTERS_SORTED_BY_BUSY.getValue(),
                         () -> Printer.printInfo(masterService.getMasterByBusy()), mastersMenu),
            new MenuItem(MenuTittle.PREVIOUS_MENU.getValue(), () -> Printer.printInfo(MenuTittle.GO_TO_MENU.getValue()),
                         this.rootMenu))));
    }

    private void createItemPlacesMenu(Menu placesMenu) {
        placesMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SHOW_LIST_OF_PLACES.getValue(),
                         () -> Printer.printInfo(StringPlaces.getStringFromPlaces(placeService.getPlaces())), placesMenu));
        placesMenu.getMenuItems().add(
            new MenuItem(MenuTittle.ADD_PLACE.getValue(), () ->
                Printer.printInfo(placeService.addPlace(ScannerUtil.getIntUser(NUMBER_PLACE_INPUT_HEADER))),
                         placesMenu));
        placesMenu.getMenuItems().add(new MenuItem(MenuTittle.DELETE_PLACE.getValue(), () -> {
            if (isCheckPlaces()) {
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
            new MenuItem(MenuTittle.SHOW_ORDERS.getValue(), () -> {
                if (isCheckOrders()) {
                    return;
                }
                Printer.printInfo(StringOrder.getStringFromOrder(orderService.getOrders()));
            }, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem(MenuTittle.ADD_ORDER.getValue(), this::addOrder, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem(MenuTittle.DELETE_THE_ORDER.getValue(), () -> {
            if (isCheckOrders()) {
                return;
            }
            deleteOrder();
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartTwo(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(new MenuItem(MenuTittle.CLOSE_THE_ORDER.getValue(), () -> {
            if (isCheckOrders()) {
                return;
            }
            List<OrderDto> ordersDto = orderService.getOrders();
            Printer.printInfo(StringOrder.getStringFromOrder(ordersDto));
            int index;
            String message = "";
            while (!message.equals(ORDER_SUCCESS_SERVER_MESSAGE)) {
                index = ScannerUtil.getIntUser(ORDER_CLOSE_INDEX_HEADER) - INDEX_OFFSET;
                if (index + INDEX_OFFSET == 0) {
                    return;
                } else if (index >= ordersDto.size()) {
                    Printer.printInfo(WARNING_ORDER_MESSAGE);
                    continue;
                }
                message = orderService.closeOrder(ordersDto.get(index).getId());
                Printer.printInfo(message);
            }
        }, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem(MenuTittle.CANCEL_THE_ORDER.getValue(), () -> {
            if (isCheckOrders()) {
                return;
            }
            cancelOrder();
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartThree(Menu listOrderMenu) {
        listOrderMenu.getMenuItems()
            .add(new MenuItem(MenuTittle.TRANSFER_THE_ORDER_TO_EXECUTION_STATUS.getValue(), () -> {
                if (isCheckOrders()) {
                    return;
                }
                completeOrder();
            }, listOrderMenu));
        listOrderMenu.getMenuItems().add(new MenuItem(MenuTittle.SHIFT_THE_LEAD_TIME.getValue(), () -> {
            if (isCheckOrders()) {
                return;
            }
            List<OrderDto> ordersDto = orderService.getOrders();
            Printer.printInfo(StringOrder.getStringFromOrder(ordersDto));
            String message = "";

            while (!message.equals(ORDER_TIME_CHANGE_SUCCESS_SERVER_MESSAGE)) {
                int index = ScannerUtil.getIntUser(ORDER_SHIFT_TIME_INDEX_HEADER) -
                            INDEX_OFFSET;
                if (index + INDEX_OFFSET == 0) {
                    return;
                } else if (index >= ordersDto.size()) {
                    Printer.printInfo(WARNING_ORDER_MESSAGE);
                    continue;
                }
                OrderDto orderDto = ordersDto.get(index);
                String executionStartTime = ScannerUtil.getStringDateUser(PLANING_TIME_START_INPUT_HEADER, true);
                String leadTime = ScannerUtil.getStringDateUser(LEAD_TIME_INPUT_HEADER, true);
                orderDto.setExecutionStartTime(executionStartTime);
                orderDto.setLeadTime(leadTime);
                message = orderService.shiftLeadTime(orderDto);
                Printer.printInfo(message);
            }
        }, listOrderMenu));
    }

    private void addItemListOrderMenuPartFour(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SHOW_ORDERS_SORT_BY_FILING_DATE.getValue(),
                         () -> Printer.printInfo(orderService.getOrdersSortByFilingDate()), listOrderMenu));
        listOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SHOW_ORDERS_SORT_BY_EXECUTION_DATE.getValue(),
                         () -> Printer.printInfo(orderService.getOrdersSortByExecutionDate()), listOrderMenu));
        listOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SHOW_ORDERS_SORT_BY_PLANNED_START_DATE.getValue(),
                         () -> Printer.printInfo(orderService.getOrdersSortByPlannedStartDate()), listOrderMenu));
        listOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SHOW_ORDERS_SORT_BY_PRICE.getValue(),
                         () -> Printer.printInfo(orderService.getOrdersSortByPrice()), listOrderMenu));
    }

    private void addItemListOrderMenuPartFive(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.GET_ORDERS_EXECUTED_CONCRETE_MASTER.getValue(), () -> {
                int index;
                if (isCheckMasters()) {
                    return;
                }
                try {
                    List<MasterDto> mastersDto = masterService.getMasters();
                    Printer.printInfo(StringMaster.getStringFromMasters(mastersDto));
                    index = ScannerUtil.getIntUser(MASTER_INDEX_INPUT_HEADER) -
                            INDEX_OFFSET;
                    if (index >= mastersDto.size() || index < 0) {
                        Printer.printInfo(WARNING_MASTER_MESSAGE);
                    } else {
                        Printer.printInfo(masterService.getMasterOrders(mastersDto.get(index).getId()));
                    }
                } catch (BusinessException exception) {
                    Printer.printInfo(exception.getMessage());
                }
            }, listOrderMenu));
    }

    private void addItemListOrderMenuPartSix(Menu listOrderMenu) {
        listOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.GET_A_MASTER_PERFORMING_A_SPECIFIC_ORDER.getValue(), () -> {
                int index;
                if (isCheckOrders()) {
                    return;
                }
                List<OrderDto> ordersDto = orderService.getOrders();
                Printer.printInfo(StringOrder.getStringFromOrder(ordersDto));
                index = ScannerUtil.getIntUser(ORDER_INDEX_FOR_VIEW_MASTERS_INPUT_HEADER) -
                        INDEX_OFFSET;
                if (index >= ordersDto.size() || index < 0) {
                    Printer.printInfo(WARNING_ORDER_MESSAGE);
                } else {
                    Printer.printInfo(orderService.getOrderMasters(ordersDto.get(index).getId()));
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
                         () -> Printer.printInfo(orderService.getExecuteOrderFilingDate()), executedOrderMenu),
            new MenuItem(MenuTittle.GET_LIST_OF_ORDERS_EXECUTED_AT_A_GIVEN_TIME_SORT_BY_EXECUTION_DATE.getValue(),
                         () -> Printer.printInfo(orderService.getExecuteOrderExecutionDate()), executedOrderMenu),
            new MenuItem(MenuTittle.GET_LIST_OF_ORDERS_EXECUTED_AT_A_GIVEN_TIME_SORT_BY_PRICE.getValue(),
                         () -> Printer.printInfo(orderService.getExecuteOrderExecutionDate()), executedOrderMenu),
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
                         () -> Printer.printInfo(orderService.getCompletedOrdersFilingDate(
                             ScannerUtil.getStringDateUser(START_DATE_INPUT_HEADER, true),
                             ScannerUtil.getStringDateUser(END_DATE_INPUT_HEADER, true))), completedOrderMenu));
        completedOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SORT_BY_EXECUTION_DATE.getValue(),
                         () -> Printer.printInfo(orderService.getCompletedOrdersExecutionDate(
                             ScannerUtil.getStringDateUser(START_DATE_INPUT_HEADER, true),
                             ScannerUtil.getStringDateUser(END_DATE_INPUT_HEADER, true))), completedOrderMenu));
    }

    private void addItemCompletedOrderPartTwo(Menu completedOrderMenu, Menu periodOrderMenu) {
        completedOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SORT_BY_PRICE.getValue(),
                         () -> Printer.printInfo(orderService.getCompletedOrdersPrice(
                             ScannerUtil.getStringDateUser(START_DATE_INPUT_HEADER, true),
                             ScannerUtil.getStringDateUser(END_DATE_INPUT_HEADER, true))), completedOrderMenu));
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
                         () -> Printer.printInfo(orderService.getDeletedOrdersFilingDate(
                             ScannerUtil.getStringDateUser(START_DATE_INPUT_HEADER, true),
                             ScannerUtil.getStringDateUser(END_DATE_INPUT_HEADER, true))), deletedOrderMenu));
        deletedOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SORT_BY_EXECUTION_DATE.getValue(),
                         () -> Printer.printInfo(orderService.getDeletedOrdersExecutionDate(
                             ScannerUtil.getStringDateUser(START_DATE_INPUT_HEADER, true),
                             ScannerUtil.getStringDateUser(END_DATE_INPUT_HEADER, true))), deletedOrderMenu));
    }

    private void addItemDeletedOrderPartTwo(Menu deletedOrderMenu, Menu periodOrderMenu) {
        deletedOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SORT_BY_PRICE.getValue(),
                         () -> Printer.printInfo(orderService.getDeletedOrdersPrice(
                             ScannerUtil.getStringDateUser(
                                 START_DATE_INPUT_HEADER, true),
                             ScannerUtil.getStringDateUser(END_DATE_INPUT_HEADER, true))), deletedOrderMenu));
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
                         () -> Printer.printInfo(orderService.getCanceledOrdersFilingDate(
                             ScannerUtil.getStringDateUser(START_DATE_INPUT_HEADER, true),
                             ScannerUtil.getStringDateUser(END_DATE_INPUT_HEADER, true))), canceledOrderMenu));
        canceledOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SORT_BY_EXECUTION_DATE.getValue(),
                         () -> Printer.printInfo(orderService.getCanceledOrdersExecutionDate(
                             ScannerUtil.getStringDateUser(START_DATE_INPUT_HEADER, true),
                             ScannerUtil.getStringDateUser(END_DATE_INPUT_HEADER, true))), canceledOrderMenu));
    }

    private void addItemCanceledOrderPartTwo(Menu canceledOrderMenu, Menu periodOrderMenu) {
        canceledOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.SORT_BY_PRICE.getValue(),
                         () -> Printer.printInfo(orderService.getCanceledOrdersPrice(
                             ScannerUtil.getStringDateUser(START_DATE_INPUT_HEADER, true),
                             ScannerUtil.getStringDateUser(END_DATE_INPUT_HEADER, true))), canceledOrderMenu));
        canceledOrderMenu.getMenuItems().add(
            new MenuItem(MenuTittle.PREVIOUS_MENU.getValue(),
                         () -> Printer.printInfo(MenuTittle.GO_TO_MENU.getValue()), periodOrderMenu));
    }


    private void deleteOrder() {
        log.info("deleteOrder");
        List<OrderDto> ordersDto = orderService.getOrders();
        Printer.printInfo(StringOrder.getStringFromOrder(ordersDto));
        Printer.printInfo(STOP_DELETING_MENU_ITEM);
        String message = "";
        int index;
        while (!message.equals(ORDER_DELETE_SUCCESS_SERVER_MESSAGE)) {
            index = ScannerUtil.getIntUser(ORDER_DELETE_INDEX_HEADER) - INDEX_OFFSET;
            if (index + INDEX_OFFSET == 0) {
                return;
            } else if (index >= ordersDto.size()) {
                Printer.printInfo(WARNING_ORDER_MESSAGE);
                continue;
            }
            message = orderService.deleteOrder(ordersDto.get(index).getId());
            Printer.printInfo(message);
        }
    }

    private void deleteMaster() {
        log.info("deleteMaster");
        try {
            List<MasterDto> mastersDto = masterService.getMasters();
            Printer.printInfo(StringMaster.getStringFromMasters(mastersDto));
            int index = ScannerUtil.getIntUser(MASTER_DELETE_INDEX_HEADER) - INDEX_OFFSET;
            if (index >= mastersDto.size() || index < 0) {
                Printer.printInfo(WARNING_MASTER_MESSAGE);
            } else {
                Printer.printInfo(masterService.deleteMaster(mastersDto.get(index).getId()));
            }
        } catch (BusinessException exception) {
            log.error(exception.getMessage());
            Printer.printInfo(exception.getMessage());
        }
    }

    private void deletePlace() {
        log.info("deletePlace");
        List<PlaceDto> placesDto = placeService.getPlaces();
        Printer.printInfo(StringPlaces.getStringFromPlaces(placesDto));
        int index = ScannerUtil.getIntUser(PLACE_DELETE_INDEX_HEADER) - INDEX_OFFSET;
        if (index >= placesDto.size() || index < 0) {
            Printer.printInfo(WARNING_PLACE_MESSAGE);
        } else {
            Printer.printInfo(placeService.deletePlace(placesDto.get(index).getId()));
        }
    }

    private void completeOrder() {
        log.info("completeOrder");
        List<OrderDto> ordersDto = orderService.getOrders();
        Printer.printInfo(StringOrder.getStringFromOrder(ordersDto));
        Printer.printInfo(PREVIOUS_MENU_MENU_ITEM);
        String message = "";
        int index;
        while (!message.equals(TRANSFERRED_TO_EXECUTION_STATUS_SUCCESS_SERVER_MESSAGE)) {
            index = ScannerUtil.getIntUser(ORDER_CHANGE_STATUS_INDEX_HEADER) - INDEX_OFFSET;
            if (index + INDEX_OFFSET == 0) {
                return;
            } else if (index >= ordersDto.size()) {
                Printer.printInfo(WARNING_ORDER_MESSAGE);
                continue;
            }
            message = orderService.completeOrder(ordersDto.get(index).getId());
            Printer.printInfo(message);
        }
    }

    private void cancelOrder() {
        log.info("cancelOrder");
        List<OrderDto> ordersDto = orderService.getOrders();
        Printer.printInfo(StringOrder.getStringFromOrder(ordersDto));
        Printer.printInfo(PREVIOUS_MENU_MENU_ITEM);
        String message = "";
        int index;
        while (!message.equals(ORDER_CANCEL_SUCCESS_SERVER_MESSAGE)) {
            index = ScannerUtil.getIntUser(ORDER_CANCEL_INDEX_HEADER) - INDEX_OFFSET;
            if (index + INDEX_OFFSET == 0) {
                return;
            } else if (index >= ordersDto.size()) {
                Printer.printInfo(WARNING_ORDER_MESSAGE);
                continue;
            }
            message = orderService.cancelOrder(ordersDto.get(index).getId());
            Printer.printInfo(message);
        }
    }

    private void addOrder() {
        log.info("addOrder");
        OrderDto orderDto = new OrderDto();
        if (isCheckMasters() || isCheckPlaces()) {
            return;
        }
        addCarToOrder(orderDto);
        addOrderDeadline(orderDto);
        if (addMastersOrder(orderDto) || addPlaceOrder(orderDto)) {
            return;
        }
        orderDto.setPrice(ScannerUtil.getBigDecimalUser(ORDER_PRICE_INPUT_HEADER));
        orderDto.setCreationTime(DateUtil.getStringFromDate(new Date(), true));
        Printer.printInfo(orderService.addOrder(orderDto));
    }

    private void addCarToOrder(OrderDto orderDto) {
        log.debug("addCarToOrder");
        log.trace("Parameter orderDto: {}", orderDto);
        String automaker = ScannerUtil.getStringUser(ORDER_AUTOMAKER_INPUT_HEADER, false);
        String model = ScannerUtil.getStringUser(ORDER_MODEL_INPUT_HEADER, false);
        String registrationNumber = ScannerUtil.getStringUser(
            ORDER_REGISTRATION_NUMBER_INPUT_HEADER, true);
        orderDto.setAutomaker(automaker);
        orderDto.setModel(model);
        orderDto.setRegistrationNumber(registrationNumber);
    }

    private void addOrderDeadline(OrderDto orderDto) {
        log.debug("addOrderDeadline");
        log.trace("Parameter orderDto: {}", orderDto);
        String leadTime = "";
        String executionStartTime = "";
        String message = "";
        while (!message.equals(VERIFICATION_SUCCESS_SERVER_MESSAGE)) {
            if (!message.equals("")) {
                Printer.printInfo(message);
            }
            executionStartTime = ScannerUtil.getStringDateUser(PLANING_TIME_START_INPUT_HEADER, true);
            leadTime = ScannerUtil.getStringDateUser(LEAD_TIME_INPUT_HEADER, true);
            message = orderService.checkOrderDeadlines(executionStartTime, leadTime);

        }
        orderDto.setExecutionStartTime(executionStartTime);
        orderDto.setLeadTime(leadTime);
    }

    private boolean addMastersOrder(OrderDto orderDto) {
        log.debug("addMastersOrder");
        log.trace("Parameter orderDto: {}", orderDto);
        try {
            List<MasterDto> freeMasters = masterService.getFreeMasters(orderDto.getExecutionStartTime());
            Printer.printInfo(StringMaster.getStringFromMasters(freeMasters));
            Printer.printInfo(STOP_ADDING_MENU_ITEM);
            boolean quit = false;
            List<MasterDto> mastersDto = new ArrayList<>();
            while (!quit) {
                MasterDto masterDto = addMasters(freeMasters);
                if (mastersDto.isEmpty() && masterDto == null) {
                    Printer.printInfo(WARNING_COUNT_MASTER_MESSAGE);
                } else if (masterDto == null) {
                    quit = true;
                } else if (mastersDto.contains(masterDto)) {
                    Printer.printInfo(WARNING_EXIST_MASTER_MESSAGE);
                } else {
                    mastersDto.add(masterDto);
                }
            }
            orderDto.setMasters(mastersDto);
            return false;
        } catch (BusinessException exception) {
            log.trace(exception.getMessage());
            Printer.printInfo(exception.getMessage());
            return true;
        }
    }

    private MasterDto addMasters(List<MasterDto> mastersDto) {
        log.debug("addMasters");
        log.trace("Parameter mastersDto: {}", mastersDto);
        Integer index = null;
        while (index == null) {
            index = ScannerUtil.getIntUser(MASTER_ADD_INDEX_HEADER) - INDEX_OFFSET;
            if (index + INDEX_OFFSET == 0) {
                return null;
            } else if (index >= mastersDto.size()) {
                Printer.printInfo(WARNING_MASTER_MESSAGE);
                index = null;
            } else if (mastersDto.get(index).getDeleteStatus()) {
                Printer.printInfo(WARNING_DELETE_MASTER_MESSAGE);
                index = null;
            }
        }
        return mastersDto.get(index);
    }

    private boolean addPlaceOrder(OrderDto orderDto) {
        log.debug("addPlaceOrder");
        log.trace("Parameter orderDto: {}", orderDto);
        try {
            List<PlaceDto> placesDto = placeService.getFreePlacesByDate(orderDto.getExecutionStartTime());
            Printer.printInfo(StringPlaces.getStringFromPlaces(placesDto));
            Integer index = null;
            while (index == null) {
                index = ScannerUtil.getIntUser(PLACE_ADD_INDEX_HEADER) -
                        INDEX_OFFSET;
                if (index >= placesDto.size() || INDEX_OFFSET + index == 0) {
                    index = null;
                }
            }
            orderDto.setPlace(placesDto.get(index));
            return false;
        } catch (BusinessException exception) {
            log.error(exception.getMessage());
            Printer.printInfo(exception.getMessage());
            return true;
        }
    }

    private boolean isCheckOrders() {
        log.debug("isCheckOrders");
        String message = orderService.checkOrders();
        boolean status = message.equals(VERIFICATION_SUCCESS_SERVER_MESSAGE);
        if (!status) {
            Printer.printInfo(message);
        }
        return !status;
    }

    private boolean isCheckMasters() {
        log.debug("isCheckMasters");
        String message = masterService.checkMasters();
        boolean status = message.equals(VERIFICATION_SUCCESS_SERVER_MESSAGE);
        if (!status) {
            Printer.printInfo(message);
        }
        return !status;
    }

    private boolean isCheckPlaces() {
        log.debug("isCheckPlaces");
        String message = placeService.checkPlaces();
        boolean status = message.equals(VERIFICATION_SUCCESS_SERVER_MESSAGE);
        if (!status) {
            Printer.printInfo(message);
        }
        return !status;
    }

}
