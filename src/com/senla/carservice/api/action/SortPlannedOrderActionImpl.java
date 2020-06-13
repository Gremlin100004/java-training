package com.senla.carservice.api.action;

public final class SortPlannedOrderActionImpl implements Action {
    private static SortPlannedOrderActionImpl instance;

    public SortPlannedOrderActionImpl() {
    }

    public static SortPlannedOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new SortPlannedOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
