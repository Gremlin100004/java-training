package com.senla.carservice.api.action;

public final class BackExecutedOrderActionImpl implements Action {
    private static BackExecutedOrderActionImpl instance;

    public BackExecutedOrderActionImpl() {
    }

    public static BackExecutedOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new BackExecutedOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}