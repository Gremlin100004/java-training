package com.senla.carservice.api.action;

public final class CanceledSortFilingOrderActionImpl implements Action{
    private static CanceledSortFilingOrderActionImpl instance;

    public CanceledSortFilingOrderActionImpl() {
    }

    public static CanceledSortFilingOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new CanceledSortFilingOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
