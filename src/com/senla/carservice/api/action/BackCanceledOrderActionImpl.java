package com.senla.carservice.api.action;

public final class BackCanceledOrderActionImpl implements Action {
    private static BackCanceledOrderActionImpl instance;

    public BackCanceledOrderActionImpl() {
    }

    public static BackCanceledOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new BackCanceledOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
