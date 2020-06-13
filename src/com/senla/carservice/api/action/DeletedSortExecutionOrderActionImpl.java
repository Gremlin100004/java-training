package com.senla.carservice.api.action;

public final class DeletedSortExecutionOrderActionImpl implements Action{
    private static DeletedSortExecutionOrderActionImpl instance;

    public DeletedSortExecutionOrderActionImpl() {
    }

    public static DeletedSortExecutionOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new DeletedSortExecutionOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
