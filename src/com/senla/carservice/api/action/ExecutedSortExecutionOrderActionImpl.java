package com.senla.carservice.api.action;

public final class ExecutedSortExecutionOrderActionImpl implements Action {
    private static ExecutedSortExecutionOrderActionImpl instance;

    public ExecutedSortExecutionOrderActionImpl() {
    }

    public static ExecutedSortExecutionOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new ExecutedSortExecutionOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
