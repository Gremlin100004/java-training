package com.senla.carservice.api.action;

public final class AlphabetListMasterActionImpl implements Action {
    private static AlphabetListMasterActionImpl instance;

    public AlphabetListMasterActionImpl() {
    }

    public static AlphabetListMasterActionImpl getInstance() {
        if (instance == null) {
            instance = new AlphabetListMasterActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("get list masters sort by alphabet");
    }
}
