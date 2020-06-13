package com.senla.carservice.api.action;

public final class ShiftLeadOrderActionImpl implements Action {
    private static ShiftLeadOrderActionImpl instance;

    public ShiftLeadOrderActionImpl() {
    }

    public static ShiftLeadOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new ShiftLeadOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
