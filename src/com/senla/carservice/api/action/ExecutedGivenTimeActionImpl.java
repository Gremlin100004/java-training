package com.senla.carservice.api.action;

public final class ExecutedGivenTimeActionImpl implements Action {
    private static ExecutedGivenTimeActionImpl instance;

    public ExecutedGivenTimeActionImpl() {
    }

    public static ExecutedGivenTimeActionImpl getInstance() {
        if (instance == null) {
            instance = new ExecutedGivenTimeActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item garages");
    }
}
