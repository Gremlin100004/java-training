package com.senla.carservice.api.action;

public final class DeletedSortPriceOrderActionImpl implements Action {
    private static DeletedSortPriceOrderActionImpl instance;

    public DeletedSortPriceOrderActionImpl() {
    }

    public static DeletedSortPriceOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new DeletedSortPriceOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
