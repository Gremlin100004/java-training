package com.senla.carservice.ui.menu;

import com.senla.carservice.ui.util.Printer;

import java.util.List;

public class Navigator {
    private static Navigator instance;
    private Menu currentMenu;

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
        List<MenuItem> menuItems = this.currentMenu.getMenuItems();
        if (index > menuItems.size()) {
            Printer.printInfo("There is no such item!!!");
            return;
        }
        MenuItem menuItem = menuItems.get(index - 1);
        menuItem.doAction();
        this.currentMenu = menuItem.getNextMenu();
    }
}