package com.senla.carservice.api;

import com.senla.carservice.api.action.Action;

public class MenuItem {
    private final String title;
    private final Action action;
    Menu nextMenu;

    public MenuItem(String title, Action action, Menu nextMenu) {
        this.title = title;
        this.action = action;
        this.nextMenu = nextMenu;
    }

    @Override
    public String toString() {
        return title;
    }

    public void doAction(){
        this.action.execute();
    }

    public Menu getNextMenu() {
        return nextMenu;
    }
}
