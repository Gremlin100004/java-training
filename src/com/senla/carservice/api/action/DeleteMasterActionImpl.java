package com.senla.carservice.api.action;

public final class DeleteMasterActionImpl implements Action {
    private static DeleteMasterActionImpl instance;

    public DeleteMasterActionImpl() {
    }

    public static DeleteMasterActionImpl getInstance() {
        if (instance == null) {
            instance = new DeleteMasterActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("delete master");
    }
}
