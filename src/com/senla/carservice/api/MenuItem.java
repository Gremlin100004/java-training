package com.senla.carservice.api;

import com.senla.carservice.api.action.Action;

public class MenuItem {
    private String title;
    private Action action;
    private Menu nextMenu;


    public MenuItem(String title, Action action) {
        this.title = title;
        this.action = action;
    }

    @Override
    public String toString() {
        return title;
    }

    public void doAction(){
        this.action.execute();
    }
}
