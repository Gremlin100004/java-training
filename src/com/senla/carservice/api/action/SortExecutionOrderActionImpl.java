package com.senla.carservice.api.action;

public final class SortExecutionOrderActionImpl implements Action {
    private static SortExecutionOrderActionImpl instance;

    public SortExecutionOrderActionImpl() {
    }

    public static SortExecutionOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new SortExecutionOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
