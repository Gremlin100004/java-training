package com.senla.carservice.ui.menu;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class MenuItem {

    @NonNull
    private final String title;
    @NonNull
    private final Action action;
    @NonNull
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