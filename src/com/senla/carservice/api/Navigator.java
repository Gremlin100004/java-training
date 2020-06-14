package com.senla.carservice.api;

import java.util.Arrays;

public final class Navigator {
    private static Navigator instance;

    private Menu currentMenu;

    public Navigator() {
    }

    public Navigator(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }
    public static Navigator getInstance(Menu currentMenu) {
        if (instance == null) {
            instance = new Navigator(currentMenu);
        }
        return instance;
    }
    public void printMenu(){
        char lineChar = '~';
        char [] arrayChar = new char[this.currentMenu.getName().length()];
        Arrays.fill(arrayChar, lineChar);
        StringBuilder stringBuilder = new StringBuilder(String.format("%s\n", String.valueOf(arrayChar)));
        stringBuilder.append(String.format("%s\n", this.currentMenu.getName()));
        stringBuilder.append(String.format("%s\n", String.valueOf(arrayChar)));
        for (int i = 0; i < this.currentMenu.getMenuItems().length; i++)
            stringBuilder.append(String.format("%s. %s\n", i + 1, this.currentMenu.getMenuItems()[i]));
        stringBuilder.append("0. Exit program\n");
        stringBuilder.append("---------------");
        System.out.println(stringBuilder.toString());
    }

    public void navigate(Integer index) {
        if (index > this.currentMenu.getMenuItems().length){
            System.out.println("The is no such item!!!");
            return;
        }
        this.currentMenu.getMenuItems()[index-1].doAction();
        this.currentMenu = this.currentMenu.getMenuItems()[index-1].getNextMenu();
    }
}
