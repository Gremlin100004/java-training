package com.senla.carservice.api.action;

public final class AvailableSeatsActionImpl implements Action {
    private static AvailableSeatsActionImpl instance;

    public AvailableSeatsActionImpl() {
    }

    public static AvailableSeatsActionImpl getInstance() {
        if (instance == null) {
            instance = new AvailableSeatsActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
