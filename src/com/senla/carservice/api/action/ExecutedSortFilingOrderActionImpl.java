package com.senla.carservice.api.action;

public final class ExecutedSortFilingOrderActionImpl implements Action {
    private static ExecutedSortFilingOrderActionImpl instance;

    public ExecutedSortFilingOrderActionImpl() {
    }

    public static ExecutedSortFilingOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new ExecutedSortFilingOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
