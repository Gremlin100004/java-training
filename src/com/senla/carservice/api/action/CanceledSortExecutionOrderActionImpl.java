package com.senla.carservice.api.action;

public final class CanceledSortExecutionOrderActionImpl implements Action {
    private static CanceledSortExecutionOrderActionImpl instance;

    public CanceledSortExecutionOrderActionImpl() {
    }

    public static CanceledSortExecutionOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new CanceledSortExecutionOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
