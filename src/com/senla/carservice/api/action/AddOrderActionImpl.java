package com.senla.carservice.api.action;

public final class AddOrderActionImpl implements Action {
    private static AddOrderActionImpl instance;

    public AddOrderActionImpl() {
    }

    public static AddOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new AddOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
