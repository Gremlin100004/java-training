package com.senla.carservice.ui.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Menu {
    private String name;
    private List<MenuItem> menuItems = new ArrayList<>();

    public Menu() {
    }

    public Menu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%s\n%s\n%s\n", String.join("", Collections.nCopies(name.length(), "~")),
                                           name, String.join("", Collections.nCopies(name.length(), "~"))));
        for (int i = 0; i < this.menuItems.size(); i++) {
            stringBuilder.append(String.format("%s. %s\n", i + 1, menuItems.get(i)));
        }
        stringBuilder.append("0. Exit program\n---------------");
        return stringBuilder.toString();
    }
}