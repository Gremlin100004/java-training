package com.senla.carservice.api.action;

public final class MoveOrdersActionImpl implements Action{
    private static MoveOrdersActionImpl instance;

    public MoveOrdersActionImpl() {
    }

    public static MoveOrdersActionImpl getInstance() {
        if (instance == null) {
            instance = new MoveOrdersActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item orders");
    }
}
