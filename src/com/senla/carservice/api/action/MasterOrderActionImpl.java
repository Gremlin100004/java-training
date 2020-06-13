package com.senla.carservice.api.action;

public final class MasterOrderActionImpl implements Action {
    private static MasterOrderActionImpl instance;

    public MasterOrderActionImpl() {
    }

    public static MasterOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new MasterOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
