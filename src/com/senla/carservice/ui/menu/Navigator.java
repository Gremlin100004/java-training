package com.senla.carservice.ui.menu;

import java.util.Collections;

public final class Navigator {
    private static Navigator instance;

    private Menu currentMenu;

    private Navigator() {
    }

    private Navigator(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public static Navigator getInstance(Menu currentMenu) {
        if (instance == null) {
            instance = new Navigator(currentMenu);
        }
        return instance;
    }

    public void printMenu() {
        System.out.printf("%s\n",
                String.join("", Collections.nCopies(this.currentMenu.getName().length(), "~")));
        System.out.printf("%s\n", this.currentMenu.getName());
        System.out.printf("%s\n", String.join("", Collections.nCopies(
                this.currentMenu.getName().length(), "~")));
        for (int i = 0; i < this.currentMenu.getMenuItems().size(); i++) {
            System.out.printf("%s. %s\n", i + 1, this.currentMenu.getMenuItems().get(i));
        }
        System.out.println("0. Exit program");
        System.out.println("---------------");
    }

    public void navigate(Integer index) {
        if (index > this.currentMenu.getMenuItems().size()) {
            System.out.println("There is no such item!!!");
            return;
        }
        this.currentMenu.getMenuItems().get(index - 1).doAction();
        this.currentMenu = this.currentMenu.getMenuItems().get(index - 1).getNextMenu();
    }
}