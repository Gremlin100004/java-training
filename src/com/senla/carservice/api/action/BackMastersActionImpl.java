package com.senla.carservice.api.action;

public final class BackMastersActionImpl implements Action {
    private static BackMastersActionImpl instance;

    public BackMastersActionImpl() {
    }

    public static BackMastersActionImpl getInstance() {
        if (instance == null) {
            instance = new BackMastersActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
