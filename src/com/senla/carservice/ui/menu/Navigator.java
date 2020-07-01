package com.senla.carservice.ui.menu;

import com.senla.carservice.ui.util.Printer;

public class Navigator {
    private static Navigator instance;
    private Menu currentMenu;

    // в синглтоне если дефолтный конструктор не нужен, его можно убрать
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
        // this.currentMenu.getMenuItems() используется 3 раза,
        // currentMenu.getMenuItems().get(index - 1) - 2 раза
        // что-то надо вынести в переменную
        if (index > this.currentMenu.getMenuItems().size()) {
            // у тебя есть принтер
            System.out.println("There is no such item!!!");
            return;
        }
        this.currentMenu.getMenuItems().get(index - 1).doAction();
        this.currentMenu = this.currentMenu.getMenuItems().get(index - 1).getNextMenu();
    }
}