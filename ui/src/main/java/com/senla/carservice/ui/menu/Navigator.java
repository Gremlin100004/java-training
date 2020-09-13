package com.senla.carservice.ui.menu;

import org.springframework.stereotype.Component;
import com.senla.carservice.ui.util.Printer;

import java.util.List;

@Component
public class Navigator {

    private static final int INDEX_OFFSET = 1;
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
        MenuItem menuItem = menuItems.get(index - INDEX_OFFSET);
        menuItem.doAction();
        this.currentMenu = menuItem.getNextMenu();
    }
}