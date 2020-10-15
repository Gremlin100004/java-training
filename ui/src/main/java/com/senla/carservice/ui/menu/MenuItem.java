package com.senla.carservice.ui.menu;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class MenuItem {

    private final String title;
    private final Action action;
    private final Menu nextMenu;

    public Menu getNextMenu() {
        return nextMenu;
    }

    public void doAction() {
        if (action != null) {
            action.execute();
        }
    }

    @Override
    public String toString() {
        return title;
    }

}
