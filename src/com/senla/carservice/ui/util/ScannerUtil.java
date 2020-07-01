package com.senla.carservice.ui.util;

import java.math.BigDecimal;
import java.util.Scanner;

public class ScannerUtil {

    private ScannerUtil() {
    }

    public static String getStringUser(String textForUser) {
        Scanner scanner = new Scanner(System.in);
        String textUser = null;
        boolean isText = false;
        while (!isText) {
            System.out.println(textForUser);
            textUser = scanner.nextLine();
            if (!Checker.isSymbolsString(textUser)) {
                isText = true;
            } else {
                // у тебя есть утилита Принтер
                System.out.println("You enter wrong value!!!");
            }
        }
        return textUser;
    }

    public static String getStringDateUser(String textForUser) {
        Scanner scanner = new Scanner(System.in);
        String textUser = null;
        boolean isText = false;
        while (!isText) {
            System.out.println(textForUser);
            textUser = scanner.nextLine();
            if (Checker.isSymbolsStringNumber(textUser)) {
                isText = true;
            } else {
                System.out.println("You enter wrong value!!!");
            }
        }
        return textUser;
    }

    public static int getIntUser(String textForUser) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(textForUser);
        while (!scanner.hasNextInt()) {
            System.out.println("You enter wrong value!!!");
            System.out.println("Try again:");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public static BigDecimal getBigDecimalUser(String textForUser) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(textForUser);
        while (!scanner.hasNextBigDecimal()) {
            System.out.println("You enter wrong value!!!");
            System.out.println("Try again:");
            scanner.next();
        }
        return scanner.nextBigDecimal();
    }
}