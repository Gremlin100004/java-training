package com.senla.carservice.ui.menu;

import com.senla.carservice.ui.util.Printer;

public class Navigator {
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
        Printer.printInfo(this.currentMenu.toString());
    }

    public void navigate(Integer index) {
        if (index > this.currentMenu.getMenuItems().size()) {
            Printer.printInfo("There is no such item!!!");
            return;
        }
        this.currentMenu.getMenuItems().get(index - 1).doAction();
        this.currentMenu = this.currentMenu.getMenuItems().get(index - 1).getNextMenu();
    }
}