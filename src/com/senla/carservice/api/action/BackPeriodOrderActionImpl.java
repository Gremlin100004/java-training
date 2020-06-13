package com.senla.carservice.api.action;

public final class BackPeriodOrderActionImpl implements Action{
    private static BackPeriodOrderActionImpl instance;

    public BackPeriodOrderActionImpl() {
    }

    public static BackPeriodOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new BackPeriodOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
