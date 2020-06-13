package com.senla.carservice.api.action;

public final class PeriodCompletedOrderActionImpl implements Action {
    private static PeriodCompletedOrderActionImpl instance;

    public PeriodCompletedOrderActionImpl() {
    }

    public static PeriodCompletedOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new PeriodCompletedOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
