package com.senla.carservice.api.action;

public final class CanceledSortPriceOrderActionImpl implements Action {
    private static CanceledSortPriceOrderActionImpl instance;

    public CanceledSortPriceOrderActionImpl() {
    }

    public static CanceledSortPriceOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new CanceledSortPriceOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
