package com.senla.carservice.ui.menu;

import com.senla.carservice.ui.action.*;

import java.util.ArrayList;
import java.util.Arrays;

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
                new MenuItem("Fill in test data", new DemoActionImpl(), this.rootMenu)
        )));
        mastersMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Add master", new AddMasterActionImpl(), mastersMenu),
                new MenuItem("Delete Master", new DeleteMasterActionImpl(), mastersMenu),
                new MenuItem("Show a list of masters sorted alphabetically", new AlphabetListMasterActionImpl(), mastersMenu),
                new MenuItem("Show list of masters sorted by busy", new BusyListMastersActionImpl(), mastersMenu),
                new MenuItem("Previous menu", new PassiveActionImpl(), this.rootMenu)
        )));
        garagesMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Show list of garages", new ListGaragesActionImpl(), garagesMenu),
                new MenuItem("Add garage", new AddGarageActionImpl(), garagesMenu),
                new MenuItem("Delete garage", new DeleteGarageActionImpl(), garagesMenu),
                new MenuItem("Add place in garage", new AddPlaceActionImpl(), garagesMenu),
                new MenuItem("Delete place in garage", new DeletePlaceActionImpl(), garagesMenu),
                new MenuItem("Previous menu", new PassiveActionImpl(), this.rootMenu)
        )));
        ordersMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("List of orders", new PassiveActionImpl(), listOrderMenu),
                new MenuItem("List of orders executed at a given time", new PassiveActionImpl(), executedOrderMenu),
                new MenuItem("List of orders for a period of time", new PassiveActionImpl(), periodOrderMenu),
                new MenuItem("Previous menu", new PassiveActionImpl(), this.rootMenu)
        )));
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
                new MenuItem("Previous menu", new  PassiveActionImpl(), ordersMenu)
        )));
        executedOrderMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Get list of orders executed at a given time sort by filing date",
                        new ExecutedSortFilingOrderActionImpl(), executedOrderMenu),
                new MenuItem("Get list of orders executed at a given time sort by execution date",
                        new ExecutedSortExecutionOrderActionImpl(), executedOrderMenu),
                new MenuItem("Get list of orders executed at a given time sort by price",
                        new ExecutedSortPriceOrderActionImpl(), executedOrderMenu),
                new MenuItem("Previous menu", new PassiveActionImpl(), ordersMenu)
        )));
        periodOrderMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Completed orders", new PassiveActionImpl(), completedOrderMenu),
                new MenuItem("Deleted orders", new PassiveActionImpl(), deletedOrderMenu),
                new MenuItem("Canceled orders", new PassiveActionImpl(), canceledOrderMenu),
                new MenuItem("Previous menu", new PassiveActionImpl(), ordersMenu)
        )));
        completedOrderMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Sort by filing date", new CompletedSortFilingOrderActionImpl(), completedOrderMenu),
                new MenuItem("Sort by execution date", new CompletedSortExecutionOrderActionImpl(), completedOrderMenu),
                new MenuItem("Sort by price", new CompletedSortPriceOrderActionImpl(), completedOrderMenu),
                new MenuItem("Previous menu", new PassiveActionImpl(), periodOrderMenu)
        )));
        deletedOrderMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Sort by filing date", new DeletedSortFilingOrderActionImpl(), deletedOrderMenu),
                new MenuItem("Sort by execution date", new DeletedSortExecutionOrderActionImpl(), deletedOrderMenu),
                new MenuItem("Sort by price", new DeletedSortPriceOrderActionImpl(), deletedOrderMenu),
                new MenuItem("Previous menu", new PassiveActionImpl(), periodOrderMenu)
        )));
        canceledOrderMenu.setMenuItems(new ArrayList<>(Arrays.asList(
                new MenuItem("Sort by filing date", new CanceledSortFilingOrderActionImpl(), canceledOrderMenu),
                new MenuItem("Sort by execution date", new CanceledSortExecutionOrderActionImpl(), canceledOrderMenu),
                new MenuItem("Sort by price", new CanceledSortPriceOrderActionImpl(), canceledOrderMenu),
                new MenuItem("Previous menu", new PassiveActionImpl(), periodOrderMenu)
        )));
    }

    public Menu getRootMenu() {
        return this.rootMenu;
    }
}