package com.senla.carservice.api.action;

public final class BusyListMastersActionImpl implements Action {
    private static BusyListMastersActionImpl instance;

    public BusyListMastersActionImpl() {
    }

    public static BusyListMastersActionImpl getInstance() {
        if (instance == null) {
            instance = new BusyListMastersActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("get list masters sort by busy");
    }
}
