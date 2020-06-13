package com.senla.carservice.api.action;

public final class CompletedSortPriceOrderActionImpl implements Action{
    private static CompletedSortPriceOrderActionImpl instance;

    public CompletedSortPriceOrderActionImpl() {
    }

    public static CompletedSortPriceOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new CompletedSortPriceOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
