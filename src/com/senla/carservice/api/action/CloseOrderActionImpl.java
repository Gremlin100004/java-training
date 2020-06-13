package com.senla.carservice.api.action;

public final class CloseOrderActionImpl implements Action {
    private static CloseOrderActionImpl instance;

    public CloseOrderActionImpl() {
    }

    public static CloseOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new CloseOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
