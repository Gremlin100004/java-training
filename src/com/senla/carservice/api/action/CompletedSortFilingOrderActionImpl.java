package com.senla.carservice.api.action;

public final class CompletedSortFilingOrderActionImpl implements Action {
    private static CompletedSortFilingOrderActionImpl instance;

    public CompletedSortFilingOrderActionImpl() {
    }

    public static CompletedSortFilingOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new CompletedSortFilingOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
