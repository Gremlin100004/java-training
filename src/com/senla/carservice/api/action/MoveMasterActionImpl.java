package com.senla.carservice.api.action;

public final class MoveMasterActionImpl implements Action{
    private static MoveMasterActionImpl instance;

    public MoveMasterActionImpl() {
    }

    public static MoveMasterActionImpl getInstance() {
        if (instance == null) {
            instance = new MoveMasterActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        System.out.println("Go to item masters");
    }
}
