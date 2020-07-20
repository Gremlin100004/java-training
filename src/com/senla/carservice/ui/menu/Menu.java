package com.senla.carservice.ui.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Menu {
    private String name;
    private List<MenuItem> menuItems = new ArrayList<>();
    private static final String EXIT = "0. Exit program\n---------------";
    private static final String NEWLINE_CHARACTER = "\n";
    private static final String EMPTY_LINE_CHARACTER = "";
    private static final String TILDE = "~";
    private static final String POINT_PLUS_SPACE = ". ";
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
        stringBuilder.append(String.join(EMPTY_LINE_CHARACTER, Collections.nCopies(name.length(), TILDE)))
            .append(NEWLINE_CHARACTER);
        stringBuilder.append(name).append(NEWLINE_CHARACTER);
        stringBuilder.append(String.join(EMPTY_LINE_CHARACTER, Collections.nCopies(name.length(), TILDE)))
            .append(NEWLINE_CHARACTER);
        for (int i = 0; i < this.menuItems.size(); i++) {
            stringBuilder.append(i + INDEX_ADDITION).append(POINT_PLUS_SPACE).append(menuItems.get(i))
                .append(NEWLINE_CHARACTER);
        }
        stringBuilder.append(EXIT);
        return stringBuilder.toString();
    }
}