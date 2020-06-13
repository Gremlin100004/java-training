package com.senla.carservice.api.action;

public final class BackOrderActionImpl implements Action {
    private static BackOrderActionImpl instance;

    public BackOrderActionImpl() {
    }

    public static BackOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new BackOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
