package com.senla.carservice.api.menu;

import java.util.Scanner;

public class MenuController {
    Builder builder;
    Navigator navigator;

    public MenuController() {
        this.builder = Builder.getInstance();
        this.builder.buildMenu();
        this.navigator = Navigator.getInstance(this.builder.getRootMenu());
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            this.navigator.printMenu();
            System.out.println("Enter number item menu:");
            while (!scanner.hasNextInt()) {
                System.out.println("You enter wrong value!!!");
                System.out.println("Try again:");
                scanner.skip("\n");
                scanner.next();
            }
            int answer = scanner.nextInt();
            if (answer == 0) {
                break;
            }
            navigator.navigate(answer);
        }
    }
}