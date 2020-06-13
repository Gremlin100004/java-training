package com.senla.carservice.api.action;

public final class DeleteOrderActionImpl implements Action {
    private static DeleteOrderActionImpl instance;

    public DeleteOrderActionImpl() {
    }

    public static DeleteOrderActionImpl getInstance() {
        if (instance == null) {
            instance = new DeleteOrderActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
