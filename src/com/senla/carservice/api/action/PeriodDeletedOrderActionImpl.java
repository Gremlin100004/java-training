package com.senla.carservice.api.action;

public final class PeriodDeletedOrderActionImpl implements Action {
    private static PeriodDeletedOrderActionImpl instance;

    public PeriodDeletedOrderActionImpl() {
    }

    public static PeriodDeletedOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new PeriodDeletedOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}