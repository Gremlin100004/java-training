package com.senla.carservice.ui.menu;

import java.util.ArrayList;

public class Menu {
    private String name;
    private ArrayList<MenuItem> menuItems;

    public Menu() {
    }

    public Menu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMenuItems(ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}