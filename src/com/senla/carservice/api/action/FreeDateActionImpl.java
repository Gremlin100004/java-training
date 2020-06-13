package com.senla.carservice.api.action;

public final class FreeDateActionImpl implements Action {
    private static FreeDateActionImpl instance;

    public FreeDateActionImpl() {
    }

    public static FreeDateActionImpl getInstance() {
        if (instance == null) {
            instance = new FreeDateActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
