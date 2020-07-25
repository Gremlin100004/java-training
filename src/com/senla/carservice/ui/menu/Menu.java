package com.senla.carservice.ui.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Menu {
    private String name;
    private List<MenuItem> menuItems = new ArrayList<>();
    private static final String EXIT_MENU = "0. Exit program\n---------------";
    private static final String END_OF_LINE = "\n";
    private static final String SYMBOL_FOR_JOIN_METHOD = "";
    private static final String LINE_SEPARATOR = "~";
    private static final String START_OF_MENU_BAR = ". ";
    private static final int INDEX_ADDITION = 1;

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
        stringBuilder.append(String.join(SYMBOL_FOR_JOIN_METHOD, Collections.nCopies(name.length(), LINE_SEPARATOR)))
            .append(END_OF_LINE);
        stringBuilder.append(name).append(END_OF_LINE);
        stringBuilder.append(String.join(SYMBOL_FOR_JOIN_METHOD, Collections.nCopies(name.length(), LINE_SEPARATOR)))
            .append(END_OF_LINE);
        for (int i = 0; i < this.menuItems.size(); i++) {
            stringBuilder.append(i + INDEX_ADDITION).append(START_OF_MENU_BAR).append(menuItems.get(i))
                .append(END_OF_LINE);
        }
        stringBuilder.append(EXIT_MENU);
        return stringBuilder.toString();
    }
}