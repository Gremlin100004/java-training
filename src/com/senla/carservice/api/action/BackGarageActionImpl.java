package com.senla.carservice.api.action;

public final class BackGarageActionImpl implements Action {
    private static BackGarageActionImpl instance;

    public BackGarageActionImpl() {
    }

    public static BackGarageActionImpl getInstance() {
        if (instance == null) {
            instance = new BackGarageActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
