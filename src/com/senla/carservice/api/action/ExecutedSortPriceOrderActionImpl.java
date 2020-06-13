package com.senla.carservice.api.action;

public final class ExecutedSortPriceOrderActionImpl implements Action {
    private static ExecutedSortPriceOrderActionImpl instance;

    public ExecutedSortPriceOrderActionImpl() {
    }

    public static ExecutedSortPriceOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new ExecutedSortPriceOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
