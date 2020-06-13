package com.senla.carservice.api.action;

public final class ListOrdersActionImpl implements Action {
    private static ListOrdersActionImpl instance;

    public ListOrdersActionImpl() {
    }

    public static ListOrdersActionImpl getInstance() {
        if (instance == null) {
            instance = new ListOrdersActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
