package com.senla.carservice.api.action;

public final class BackCompletedOrderActionImpl implements Action {
    private static BackCompletedOrderActionImpl instance;

    public BackCompletedOrderActionImpl() {
    }

    public static BackCompletedOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new BackCompletedOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
