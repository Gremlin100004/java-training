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
        stringBuilder.append(String.join("", Collections.nCopies(name.length(), "~"))).append("\n");
        stringBuilder.append(name).append("\n");
        stringBuilder.append(String.join("", Collections.nCopies(name.length(), "~"))).append("\n");
        for (int i = 0; i < this.menuItems.size(); i++) {
            stringBuilder.append(i + 1).append(". ").append(menuItems.get(i)).append("\n");
        }
        stringBuilder.append("0. Exit program\n---------------");
        return stringBuilder.toString();
    }
}