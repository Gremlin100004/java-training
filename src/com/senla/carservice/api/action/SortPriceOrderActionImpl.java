package com.senla.carservice.api.action;

public final class SortPriceOrderActionImpl implements Action {
    private static SortPriceOrderActionImpl instance;

    public SortPriceOrderActionImpl() {
    }

    public static SortPriceOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new SortPriceOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
