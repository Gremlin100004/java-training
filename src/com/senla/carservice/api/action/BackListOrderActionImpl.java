package com.senla.carservice.api.action;

public final class BackListOrderActionImpl implements Action {
    private static BackListOrderActionImpl instance;

    public BackListOrderActionImpl() {
    }

    public static BackListOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new BackListOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
