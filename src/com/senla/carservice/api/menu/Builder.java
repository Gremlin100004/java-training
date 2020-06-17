package com.senla.carservice.api.menu;

import com.senla.carservice.api.action.*;

import java.util.ArrayList;
import java.util.Arrays;

public final class Builder {
    private static Builder instance;
    private Menu rootMenu;

    public Builder() {
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
                new MenuItem("Masters", PassiveActionImpl.getInstance(), mastersMenu),
                new MenuItem("Orders", PassiveActionImpl.getInstance(), ordersMenu),
                new MenuItem("Garages", PassiveActionImpl.getInstance(), garagesMenu),
                new MenuItem("Get the number of available seats at the car service", AvailableSeatsActionImpl.getInstance(), this.rootMenu),
                new MenuItem("Get the closest free date", FreeDateActionImpl.getInstance(), this.rootMenu),
                new MenuItem("Fill in test data", DemoActionImpl.getInstance(), this.rootMenu)
        )));
        mastersMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Add master", AddMasterActionImpl.getInstance(), mastersMenu),
                new MenuItem("Delete Master", DeleteMasterActionImpl.getInstance(), mastersMenu),
                new MenuItem("Show a list of masters sorted alphabetically", AlphabetListMasterActionImpl.getInstance(), mastersMenu),
                new MenuItem("Show list of masters sorted by busy", BusyListMastersActionImpl.getInstance(), mastersMenu),
                new MenuItem("Previous menu", PassiveActionImpl.getInstance(), this.rootMenu)
        )));
        garagesMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Show list of garages", ListGaragesActionImpl.getInstance(), garagesMenu),
                new MenuItem("Add garage", AddGarageActionImpl.getInstance(), garagesMenu),
                new MenuItem("Delete garage", DeleteGarageActionImpl.getInstance(), garagesMenu),
                new MenuItem("Add place in garage", AddPlaceActionImpl.getInstance(), garagesMenu),
                new MenuItem("Delete place in garage", DeletePlaceActionImpl.getInstance(), garagesMenu),
                new MenuItem("Previous menu", PassiveActionImpl.getInstance(), this.rootMenu)
        )));
        ordersMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("List of orders", PassiveActionImpl.getInstance(), listOrderMenu),
                new MenuItem("List of orders executed at a given time", PassiveActionImpl.getInstance(), executedOrderMenu),
                new MenuItem("List of orders for a period of time", PassiveActionImpl.getInstance(), periodOrderMenu),
                new MenuItem("Previous menu", PassiveActionImpl.getInstance(), this.rootMenu)
        )));
        listOrderMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Show orders", ShowOrderActionImpl.getInstance(), listOrderMenu),
                new MenuItem("Add order", AddOrderActionImpl.getInstance(), listOrderMenu),
                new MenuItem("Delete the order", DeleteOrderActionImpl.getInstance(), listOrderMenu),
                new MenuItem("Close the order", CloseOrderActionImpl.getInstance(), listOrderMenu),
                new MenuItem("Cancel the order", CancelOrderActionImpl.getInstance(), listOrderMenu),
                new MenuItem("Transfer the order to execution status", CompleteOrderActionImpl.getInstance(), listOrderMenu),
                new MenuItem("Shift the lead time", ShiftLeadOrderActionImpl.getInstance(), listOrderMenu),
                new MenuItem("Show orders sort by filing date",
                        SortFilingOrderActionImpl.getInstance(), listOrderMenu),
                new MenuItem("Show orders sort by execution date",
                        SortExecutionOrderActionImpl.getInstance(), listOrderMenu),
                new MenuItem("Show orders sort by planned start date",
                        SortPlannedOrderActionImpl.getInstance(), listOrderMenu),
                new MenuItem("Show orders sort by price", SortPriceOrderActionImpl.getInstance(), listOrderMenu),
                new MenuItem("Get orders executed concrete master.",
                        MasterOrderActionImpl.getInstance(), listOrderMenu),
                new MenuItem("Get a master performing a specific order",
                        OrderMastersActionImpl.getInstance(), listOrderMenu),
                new MenuItem("Previous menu", PassiveActionImpl.getInstance(), ordersMenu)
        )));
        executedOrderMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Get list of orders executed at a given time sort by filing date",
                        ExecutedSortFilingOrderActionImpl.getInstance(), executedOrderMenu),
                new MenuItem("Get list of orders executed at a given time sort by execution date",
                        ExecutedSortExecutionOrderActionImpl.getInstance(), executedOrderMenu),
                new MenuItem("Get list of orders executed at a given time sort by price",
                        ExecutedSortPriceOrderActionImpl.getInstance(), executedOrderMenu),
                new MenuItem("Previous menu", PassiveActionImpl.getInstance(), ordersMenu)
        )));
        periodOrderMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Completed orders", PassiveActionImpl.getInstance(), completedOrderMenu),
                new MenuItem("Deleted orders", PassiveActionImpl.getInstance(), deletedOrderMenu),
                new MenuItem("Canceled orders", PassiveActionImpl.getInstance(), canceledOrderMenu),
                new MenuItem("Previous menu", PassiveActionImpl.getInstance(), ordersMenu)
        )));
        completedOrderMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Sort by filing date", CompletedSortFilingOrderActionImpl.getInstance(), completedOrderMenu),
                new MenuItem("Sort by execution date", CompletedSortExecutionOrderActionImpl.getInstance(), completedOrderMenu),
                new MenuItem("Sort by price", CompletedSortPriceOrderActionImpl.getInstance(), completedOrderMenu),
                new MenuItem("Previous menu", PassiveActionImpl.getInstance(), periodOrderMenu)
        )));
        deletedOrderMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Sort by filing date", DeletedSortFilingOrderActionImpl.getInstance(), deletedOrderMenu),
                new MenuItem("Sort by execution date", DeletedSortExecutionOrderActionImpl.getInstance(), deletedOrderMenu),
                new MenuItem("Sort by price", DeletedSortPriceOrderActionImpl.getInstance(), deletedOrderMenu),
                new MenuItem("Previous menu", PassiveActionImpl.getInstance(), periodOrderMenu)
        )));
        canceledOrderMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Sort by filing date", CanceledSortFilingOrderActionImpl.getInstance(), canceledOrderMenu),
                new MenuItem("Sort by execution date", CanceledSortExecutionOrderActionImpl.getInstance(), canceledOrderMenu),
                new MenuItem("Sort by price", CanceledSortPriceOrderActionImpl.getInstance(), canceledOrderMenu),
                new MenuItem("Previous menu", PassiveActionImpl.getInstance(), periodOrderMenu)
        )));
    }

    public Menu getRootMenu() {
        return this.rootMenu;
    }
}