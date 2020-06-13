package com.senla.carservice.api.action;

public final class ShowMastersActionImpl implements Action {
    private static ShowMastersActionImpl instance;

    public ShowMastersActionImpl() {
    }

    public static ShowMastersActionImpl getInstance() {
        if (instance == null) {
            instance = new ShowMastersActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Show masters list");
    }
}
