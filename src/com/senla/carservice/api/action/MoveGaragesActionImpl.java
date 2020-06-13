package com.senla.carservice.api.action;

public final class MoveGaragesActionImpl implements Action {
    private static MoveGaragesActionImpl instance;

    public MoveGaragesActionImpl() {
    }

    public static MoveGaragesActionImpl getInstance() {
        if (instance == null) {
            instance = new MoveGaragesActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
