package com.senla.carservice.api.action;

public final class CancelOrderActionImpl implements Action {
    private static CancelOrderActionImpl instance;

    public CancelOrderActionImpl() {
    }

    public static CancelOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new CancelOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
