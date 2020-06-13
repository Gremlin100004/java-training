package com.senla.carservice.api.action;

public final class PeriodCanceledOrderActionImpl implements Action{
    private static PeriodCanceledOrderActionImpl instance;

    public PeriodCanceledOrderActionImpl() {
    }

    public static PeriodCanceledOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new PeriodCanceledOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
