package com.senla.carservice.api.action;

public final class CompletedSortExecutionOrderActionImpl implements Action{
    private static CompletedSortExecutionOrderActionImpl instance;

    public CompletedSortExecutionOrderActionImpl() {
    }

    public static CompletedSortExecutionOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new CompletedSortExecutionOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
