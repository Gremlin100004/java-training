package com.senla.carservice.api.action;

public final class SortFilingOrderActionImpl implements Action {
    private static SortFilingOrderActionImpl instance;

    public SortFilingOrderActionImpl() {
    }

    public static SortFilingOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new SortFilingOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
