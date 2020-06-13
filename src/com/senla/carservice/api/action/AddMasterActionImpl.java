package com.senla.carservice.api.action;

public final class AddMasterActionImpl implements Action {
    private static AddMasterActionImpl instance;

    public AddMasterActionImpl() {
    }

    public static AddMasterActionImpl getInstance() {
        if (instance == null) {
            instance = new AddMasterActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Add master to service");
    }
}
