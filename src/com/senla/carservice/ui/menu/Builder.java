package com.senla.carservice.ui.menu;

import com.senla.carservice.ui.action.*;

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
        this.rootMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Masters", new PassiveActionImpl(), mastersMenu),
                new MenuItem("Orders", new PassiveActionImpl(), ordersMenu),
                new MenuItem("Garages", new PassiveActionImpl(), garagesMenu),
                new MenuItem("Get the number of available seats at the car service", new AvailableSeatsActionImpl(), this.rootMenu),
                new MenuItem("Get the closest free date", new FreeDateActionImpl(), this.rootMenu),
                new MenuItem("Fill in test data", new DemoActionImpl(), this.rootMenu),
                new MenuItem("Export of all entities", new ExportAllActionImpl(), this.rootMenu)
        )));
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

    private void createItemMastersMenu(Menu mastersMenu) {
        mastersMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Add master", new AddMasterActionImpl(), mastersMenu),
                new MenuItem("Delete Master", new DeleteMasterActionImpl(), mastersMenu),
                new MenuItem("Show a list of masters sorted alphabetically", new AlphabetListMasterActionImpl(), mastersMenu),
                new MenuItem("Show list of masters sorted by busy", new BusyListMastersActionImpl(), mastersMenu),
                new MenuItem("Export masters", new ExportMastersActionImpl(), mastersMenu),
                new MenuItem("Previous menu", new PassiveActionImpl(), this.rootMenu)
        )));
    }

    private void createItemGaragesMenu(Menu garagesMenu, Menu rootMenu) {
        garagesMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Show list of garages", new ListGaragesActionImpl(), garagesMenu),
                new MenuItem("Add garage", new AddGarageActionImpl(), garagesMenu),
                new MenuItem("Delete garage", new DeleteGarageActionImpl(), garagesMenu),
                new MenuItem("Add place in garage", new AddPlaceActionImpl(), garagesMenu),
                new MenuItem("Delete place in garage", new DeletePlaceActionImpl(), garagesMenu),
                new MenuItem("Export garages", new ExportGarageActionImpl(), garagesMenu),
                new MenuItem("Import garages", new ImportGarageActionImpl(), garagesMenu),
                new MenuItem("Previous menu", new PassiveActionImpl(), rootMenu)
        )));
    }

    private void createItemOrderMenu(List<Menu> menus) {
        menus.get(0).setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("List of orders", new PassiveActionImpl(), menus.get(1)),
                new MenuItem("List of orders executed at a given time", new PassiveActionImpl(), menus.get(2)),
                new MenuItem("List of orders for a period of time", new PassiveActionImpl(), menus.get(3)),
                new MenuItem("List of orders for a period of time", new PassiveActionImpl(), menus.get(3)),
                new MenuItem("Previous menu", new PassiveActionImpl(), this.rootMenu)
        )));
    }

    private void createItemListOrderMenu(Menu listOrderMenu, Menu ordersMenu) {
        listOrderMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Show orders", new ShowOrderActionImpl(), listOrderMenu),
                new MenuItem("Add order", new AddOrderActionImpl(), listOrderMenu),
                new MenuItem("Delete the order", new DeleteOrderActionImpl(), listOrderMenu),
                new MenuItem("Close the order", new CloseOrderActionImpl(), listOrderMenu),
                new MenuItem("Cancel the order", new CancelOrderActionImpl(), listOrderMenu),
                new MenuItem("Transfer the order to execution status", new CompleteOrderActionImpl(), listOrderMenu),
                new MenuItem("Shift the lead time", new ShiftLeadOrderActionImpl(), listOrderMenu),
                new MenuItem("Show orders sort by filing date",
                        new SortFilingOrderActionImpl(), listOrderMenu),
                new MenuItem("Show orders sort by execution date",
                        new SortExecutionOrderActionImpl(), listOrderMenu),
                new MenuItem("Show orders sort by planned start date",
                        new SortPlannedOrderActionImpl(), listOrderMenu),
                new MenuItem("Show orders sort by price", new SortPriceOrderActionImpl(), listOrderMenu),
                new MenuItem("Get orders executed concrete master.",
                        new MasterOrderActionImpl(), listOrderMenu),
                new MenuItem("Get a master performing a specific order",
                        new OrderMastersActionImpl(), listOrderMenu),
                new MenuItem("Export orders",
                        new ExportOrderActionImpl(), listOrderMenu),
                new MenuItem("Previous menu", new PassiveActionImpl(), ordersMenu)
        )));
    }

    private void createItemExecutedOrderMenu(Menu executedOrderMenu, Menu ordersMenu) {
        executedOrderMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Get list of orders executed at a given time sort by filing date",
                        new ExecutedSortFilingOrderActionImpl(), executedOrderMenu),
                new MenuItem("Get list of orders executed at a given time sort by execution date",
                        new ExecutedSortExecutionOrderActionImpl(), executedOrderMenu),
                new MenuItem("Get list of orders executed at a given time sort by price",
                        new ExecutedSortPriceOrderActionImpl(), executedOrderMenu),
                new MenuItem("Previous menu", new PassiveActionImpl(), ordersMenu)
        )));
    }

    private void createItemPeriodOrderMenu(List<Menu> menus) {
        menus.get(0).setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Completed orders", new PassiveActionImpl(), menus.get(1)),
                new MenuItem("Deleted orders", new PassiveActionImpl(), menus.get(2)),
                new MenuItem("Canceled orders", new PassiveActionImpl(), menus.get(3)),
                new MenuItem("Previous menu", new PassiveActionImpl(), menus.get(4))
        )));
    }

    private void createItemCompletedOrderMenu(Menu completedOrderMenu, Menu periodOrderMenu) {
        completedOrderMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Sort by filing date", new CompletedSortFilingOrderActionImpl(), completedOrderMenu),
                new MenuItem("Sort by execution date",
                        new CompletedSortExecutionOrderActionImpl(), completedOrderMenu),
                new MenuItem("Sort by price", new CompletedSortPriceOrderActionImpl(), completedOrderMenu),
                new MenuItem("Previous menu", new PassiveActionImpl(), periodOrderMenu)
        )));
    }

    private void createItemDeletedOrderMenu(Menu deletedOrderMenu, Menu periodOrderMenu) {
        deletedOrderMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Sort by filing date", new DeletedSortFilingOrderActionImpl(), deletedOrderMenu),
                new MenuItem("Sort by execution date", new DeletedSortExecutionOrderActionImpl(), deletedOrderMenu),
                new MenuItem("Sort by price", new DeletedSortPriceOrderActionImpl(), deletedOrderMenu),
                new MenuItem("Previous menu", new PassiveActionImpl(), periodOrderMenu)
        )));
    }

    private void createItemCanceledOrderMenu(Menu canceledOrderMenu, Menu periodOrderMenu) {
        canceledOrderMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Sort by filing date", new CanceledSortFilingOrderActionImpl(), canceledOrderMenu),
                new MenuItem("Sort by execution date", new CanceledSortExecutionOrderActionImpl(), canceledOrderMenu),
                new MenuItem("Sort by price", new CanceledSortPriceOrderActionImpl(), canceledOrderMenu),
                new MenuItem("Previous menu", new PassiveActionImpl(), periodOrderMenu)
        )));
    }
}