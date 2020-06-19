package com.senla.carservice.api.action;

public class PassiveActionImpl implements Action {

    public PassiveActionImpl() {
    }

    @Override
    public void execute() {
        System.out.println("Go to menu");
    }
}