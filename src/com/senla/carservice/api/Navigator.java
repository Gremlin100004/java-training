package com.senla.carservice.api;

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
        System.out.println(String.format("%s:",this.currentMenu.getName()));
        for (int i = 0; i < this.currentMenu.getMenuItems().length; i++)
            System.out.println(String.format("%s. %s", i + 1, this.currentMenu.getMenuItems()[i]));
        System.out.println("0. Exit program");
    }

    public void navigate(Integer index) {
        this.currentMenu.getMenuItems()[index-1].doAction();
        this.currentMenu = this.currentMenu.getMenuItems()[index-1].getNextMenu();
    }
}
