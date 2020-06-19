package com.senla.carservice.api.menu;

import java.util.Collections;

public final class Navigator {
    private static Navigator instance;

    private Menu currentMenu;

    // это не синглтон, если у него публичный конструктор, да еще и два
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

    public void printMenu() {
        StringBuilder stringBuilder = new StringBuilder(String.format("%s\n",
                String.join("", Collections.nCopies(this.currentMenu.getName().length(), "~"))));
        // зачем использовать стринг билдер и при это еще и стринг формат? да еще и в такой пустяковой строке?
        stringBuilder.append(String.format("%s\n", this.currentMenu.getName()));
        stringBuilder.append(String.format("%s\n",
                String.join("", Collections.nCopies(this.currentMenu.getName().length(), "~"))));
        // фигурные скобки надо ставить ВСЕГДА!!!!!!! и так метод не разберешь, еще и без скобок
        for (int i = 0; i < this.currentMenu.getMenuItems().size(); i++)
            stringBuilder.append(String.format("%s. %s\n", i + 1, this.currentMenu.getMenuItems().get(i)));
        stringBuilder.append("0. Exit program\n");
        stringBuilder.append("---------------");
        System.out.println(stringBuilder.toString());
    }

    public void navigate(Integer index) {
        if (index > this.currentMenu.getMenuItems().size()) {
            System.out.println("There is no such item!!!");
            return;
        }
        this.currentMenu.getMenuItems().get(index - 1).doAction();
        this.currentMenu = this.currentMenu.getMenuItems().get(index - 1).nextMenu;
    }
}