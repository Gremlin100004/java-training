package com.senla.carservice.api.action;

public final class BackDeletedOrderActionImpl implements Action{
    private static BackDeletedOrderActionImpl instance;

    public BackDeletedOrderActionImpl() {
    }

    public static BackDeletedOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new BackDeletedOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
