package com.senla.carservice.api;

import com.senla.carservice.api.action.*;

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

    public void buildMenu(){
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

        this.rootMenu.setMenuItems(new MenuItem[]{
                new MenuItem("Masters", PassiveActionImpl.getInstance(), mastersMenu),
                new MenuItem("Orders", PassiveActionImpl.getInstance(), ordersMenu),
                new MenuItem("Garages", PassiveActionImpl.getInstance(), garagesMenu),
                new MenuItem("Get the number of available seats at the car service", AvailableSeatsActionImpl.getInstance(), this.rootMenu),
                new MenuItem("Get the closest free date", FreeDateActionImpl.getInstance(), this.rootMenu),
                new MenuItem("Fill in test data", DemoActionImpl.getInstance(), this.rootMenu)
        });
        mastersMenu.setMenuItems(new MenuItem[]{
                new MenuItem("Add master", AddMasterActionImpl.getInstance(), mastersMenu),
                new MenuItem("Delete Master", DeleteMasterActionImpl.getInstance(), mastersMenu),
                new MenuItem("Show a list of masters sorted alphabetically", AlphabetListMasterActionImpl.getInstance(), mastersMenu),
                new MenuItem("Show list of masters sorted by busy", BusyListMastersActionImpl.getInstance(), mastersMenu),
                new MenuItem("Previous menu", BackMastersActionImpl.getInstance(), this.rootMenu)
        });
        garagesMenu.setMenuItems(new MenuItem[]{
                new MenuItem("Show list of garages", ShowMastersActionImpl.getInstance(), garagesMenu),
                new MenuItem("Add garage", AddMasterActionImpl.getInstance(), garagesMenu),
                new MenuItem("Delete garage", DeleteMasterActionImpl.getInstance(), garagesMenu),
                new MenuItem("Add place in garage", AlphabetListMasterActionImpl.getInstance(), garagesMenu),
                new MenuItem("Delete place in garage", BusyListMastersActionImpl.getInstance(), garagesMenu),
                new MenuItem("Previous menu", BackGarageActionImpl.getInstance(), this.rootMenu)
        });
        ordersMenu.setMenuItems(new MenuItem[]{
                new MenuItem("Show list of orders", ListOrdersActionImpl.getInstance(), listOrderMenu),
                new MenuItem("list of orders executed at a given time", ExecutedGivenTimeActionImpl.getInstance(), executedOrderMenu),
                new MenuItem("Get a list of orders for a period of time", OrdersPeriodTimeActionImpl.getInstance(), periodOrderMenu),
                new MenuItem("Previous menu", BackOrderActionImpl.getInstance(), this.rootMenu)
        });
        listOrderMenu.setMenuItems(new MenuItem[]{
                new MenuItem("Add order", AddOrderActionImpl.getInstance(), listOrderMenu),
                new MenuItem("Delete the order", DeleteOrderActionImpl.getInstance(), listOrderMenu),
                new MenuItem("Close the order", CloseOrderActionImpl.getInstance(), listOrderMenu),
                new MenuItem("Cancel the order", CancelOrderActionImpl.getInstance(), listOrderMenu),
                new MenuItem("Shift the lead time", ShiftLeadOrderActionImpl.getInstance(), listOrderMenu),
                new MenuItem("Sort by filing date", SortFilingOrderActionImpl.getInstance(), listOrderMenu),
                new MenuItem("Sort by execution date", SortExecutionOrderActionImpl.getInstance(), listOrderMenu),
                new MenuItem("Sort by planned start date", SortPlannedOrderActionImpl.getInstance(), listOrderMenu),
                new MenuItem("Sort by price", SortPriceOrderActionImpl.getInstance(), listOrderMenu),
                new MenuItem("Get a master performing a specific order", MasterOrderActionImpl.getInstance(), listOrderMenu),
                new MenuItem("Previous menu", BackListOrderActionImpl.getInstance(), ordersMenu),
        });
        executedOrderMenu.setMenuItems(new MenuItem[]{
                new MenuItem("Sort by filing date", ExecutedSortFilingOrderActionImpl.getInstance(), executedOrderMenu),
                new MenuItem("Sort by execution date", ExecutedSortExecutionOrderActionImpl.getInstance(), executedOrderMenu),
                new MenuItem("Sort by price", ExecutedSortPriceOrderActionImpl.getInstance(), executedOrderMenu),
                new MenuItem("Previous menu", BackExecutedOrderActionImpl.getInstance(), ordersMenu)
        });
        periodOrderMenu.setMenuItems(new MenuItem[]{
                new MenuItem("Completed orders", PeriodCompletedOrderActionImpl.getInstance(), completedOrderMenu),
                new MenuItem("Deleted orders", PeriodDeletedOrderActionImpl.getInstance(), deletedOrderMenu),
                new MenuItem("Canceled orders", PeriodCanceledOrderActionImpl.getInstance(), canceledOrderMenu),
                new MenuItem("Previous menu", BackPeriodOrderActionImpl.getInstance(), ordersMenu)
        });
        completedOrderMenu.setMenuItems(new MenuItem[]{
                new MenuItem("Sort by filing date", CompletedSortFilingOrderActionImpl.getInstance(), completedOrderMenu),
                new MenuItem("Sort by execution date", CompletedSortExecutionOrderActionImpl.getInstance(), completedOrderMenu),
                new MenuItem("Sort by price", CompletedSortPriceOrderActionImpl.getInstance(), completedOrderMenu),
                new MenuItem("Previous menu", BackCompletedOrderActionImpl.getInstance(), periodOrderMenu)
        });
        deletedOrderMenu.setMenuItems(new MenuItem[]{
                new MenuItem("Sort by filing date", DeletedSortFilingOrderActionImpl.getInstance(), deletedOrderMenu),
                new MenuItem("Sort by execution date", ExecutedSortExecutionOrderActionImpl.getInstance(), deletedOrderMenu),
                new MenuItem("Sort by price", ExecutedSortPriceOrderActionImpl.getInstance(), deletedOrderMenu),
                new MenuItem("Previous menu", BackExecutedOrderActionImpl.getInstance(), periodOrderMenu)
        });
        canceledOrderMenu.setMenuItems(new MenuItem[]{
                new MenuItem("Sort by filing date", CanceledSortFilingOrderActionImpl.getInstance(), canceledOrderMenu),
                new MenuItem("Sort by execution date", CanceledSortExecutionOrderActionImpl.getInstance(), canceledOrderMenu),
                new MenuItem("Sort by price", CanceledSortPriceOrderActionImpl.getInstance(), canceledOrderMenu),
                new MenuItem("Previous menu", BackExecutedOrderActionImpl.getInstance(), periodOrderMenu)
        });
    }
    public Menu getRootMenu(){
        return this.rootMenu;
    }
}
