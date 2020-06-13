package com.senla.carservice.api.action;

public final class DeletedSortFilingOrderActionImpl implements Action {
    private static DeletedSortFilingOrderActionImpl instance;

    public DeletedSortFilingOrderActionImpl() {
    }

    public static DeletedSortFilingOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new DeletedSortFilingOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
