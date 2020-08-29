package com.senla.carservice.menu;

public enum MenuTittle {
    CAR_SERVICE_MENU("Car Service Menu"),
    MASTERS("Masters"),
    ORDERS("Orders"),
    PLACES("Places"),
    ORDERS_LIST("Orders list"),
    LIST_OF_ORDERS_CURRENTLY_BEING_EXECUTED("list of orders currently being executed"),
    ORDERS_LIST_FOR_A_PERIOD_OF_TIME("Orders list for a period of time"),
    COMPLETED_ORDERS("Completed orders"),
    DELETED_ORDERS("Deleted orders"),
    CANCELED_ORDERS("Canceled orders"),
    GO_TO_MENU("Go to menu"),
    GET_THE_NUMBER_OF_AVAILABLE_SEATS_AT_THE_CAR_SERVICE("Get the number of available seats at the car service"),
    GET_THE_CLOSEST_FREE_DATE("Get the closest free date"),
    ADD_NEW_ORDERS_TO_CAR_SERVICE("Add new orders to car service"),
    EXPORT_OF_ALL_ENTITIES("Export of all entities"),
    IMPORT_OF_ALL_ENTITIES("Import of all entities"),
    ADD_MASTER("Add master"),
    DELETE_MASTER("Delete Master"),
    SHOW_A_LIST_OF_MASTERS_SORTED_ALPHABETICALLY("Show a list of masters sorted alphabetically"),
    SHOW_LIST_OF_MASTERS_SORTED_BY_BUSY("Show list of masters sorted by busy"),
    SHOW_LIST_OF_PLACES("Show list of places"),
    ADD_PLACE("Add place"),
    DELETE_PLACE("Delete place"),
    PREVIOUS_MENU("Previous menu"),
    LIST_OF_ORDERS("List of orders"),
    LIST_OF_ORDERS_EXECUTED_AT_A_GIVEN_TIME("List of orders executed at a given time"),
    LIST_OF_ORDERS_FOR_A_PERIOD_OF_TIME("List of orders for a period of time"),
    SHOW_ORDERS("Show orders"),
    ADD_ORDER("Add order"),
    DELETE_THE_ORDER("Delete the order"),
    CLOSE_THE_ORDER("Close the order"),
    CANCEL_THE_ORDER("Cancel the order"),
    TRANSFER_THE_ORDER_TO_EXECUTION_STATUS("Transfer the order to execution status"),
    SHIFT_THE_LEAD_TIME("Shift the lead time"),
    SHOW_ORDERS_SORT_BY_FILING_DATE("Show orders sort by filing date"),
    SHOW_ORDERS_SORT_BY_EXECUTION_DATE("Show orders sort by execution date"),
    SHOW_ORDERS_SORT_BY_PLANNED_START_DATE("Show orders sort by planned start date"),
    SHOW_ORDERS_SORT_BY_PRICE("Show orders sort by price"),
    GET_ORDERS_EXECUTED_CONCRETE_MASTER("Get orders executed concrete master"),
    GET_A_MASTER_PERFORMING_A_SPECIFIC_ORDER("Get a master performing a specific order"),
    GET_LIST_OF_ORDERS_EXECUTED_AT_A_GIVEN_TIME_SORT_BY_FILING_DATE(
        "Get list of orders executed at a given time sort by filing date"),
    GET_LIST_OF_ORDERS_EXECUTED_AT_A_GIVEN_TIME_SORT_BY_EXECUTION_DATE(
        "Get list of orders executed at a given time sort by execution date"),
    GET_LIST_OF_ORDERS_EXECUTED_AT_A_GIVEN_TIME_SORT_BY_PRICE(
        "Get list of orders executed at a given time sort by price"),
    SORT_BY_FILING_DATE("Sort by filing date"),
    SORT_BY_EXECUTION_DATE("Sort by execution date"),
    SORT_BY_PRICE("Sort by price");

    private String value;

    MenuTittle(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}