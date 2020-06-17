package com.senla.carservice.api.action;

public final class PassiveActionImpl implements Action {
    private static PassiveActionImpl instance;

    public PassiveActionImpl() {
    }

    public static PassiveActionImpl getInstance() {
        if (instance == null) {
            instance = new PassiveActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to menu");
    }
}