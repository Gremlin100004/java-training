package com.senla.carservice.api.action;

public final class OrdersPeriodTimeActionImpl implements Action {
    private static OrdersPeriodTimeActionImpl instance;

    public OrdersPeriodTimeActionImpl() {
    }

    public static OrdersPeriodTimeActionImpl getInstance() {
        if (instance == null) {
            instance = new OrdersPeriodTimeActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
