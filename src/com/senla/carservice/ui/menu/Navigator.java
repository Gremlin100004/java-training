package com.senla.carservice.ui.menu;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.ui.util.Printer;

import java.util.List;

@Singleton
public class Navigator {
    private Menu currentMenu;

    public Navigator() {
    }

    public void addCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
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