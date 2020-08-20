package com.senla.carservice.ui.util;

import java.math.BigDecimal;
import java.util.Scanner;

public class ScannerUtil {

    private ScannerUtil() {
    }

    public static String getStringUser(String textForUser, boolean isNumber) {
        Scanner scanner = new Scanner(System.in);
        String textUser = null;
        boolean isText = false;
        while (!isText) {
            Printer.printInfo(textForUser);
            textUser = scanner.nextLine();
            if (!Checker.isSymbolsString(textUser) && !isNumber) {
                isText = true;
            } else if (!Checker.isSymbolsStringNumber(textUser) && isNumber){
                isText = true;
            }
            else {
                Printer.printInfo("You enter wrong value!!!");
            }
        }
        return textUser;
    }

    public static String getStringUser(String textForUser) {
        Scanner scanner = new Scanner(System.in);
        String textUser = null;
        boolean isText = false;
        while (!isText) {
            Printer.printInfo(textForUser);
            textUser = scanner.nextLine();
            if (!Checker.isSymbolsString(textUser)) {
                isText = true;
            } else {
                Printer.printInfo("You enter wrong value!!!");
            }
        }
        return textUser;
    }

    public static String getStringDateUser(String textForUser, boolean isTime) {
        Scanner scanner = new Scanner(System.in);
        String textUser = null;
        boolean isText = false;
        while (!isText) {
            Printer.printInfo(textForUser);
            textUser = scanner.nextLine();
            if (isTime && Checker.isSymbolsDateTime(textUser) || Checker.isSymbolsStringDate(textUser)) {
                isText = true;
            } else {
                Printer.printInfo("You enter wrong value!!!");
            }
        }
        return textUser;
    }

    public static int getIntUser(String textForUser) {
        Scanner scanner = new Scanner(System.in);
        Printer.printInfo(textForUser);
        while (!scanner.hasNextInt()) {
            Printer.printInfo("You enter wrong value!!!");
            Printer.printInfo("Try again:");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public static BigDecimal getBigDecimalUser(String textForUser) {
        Scanner scanner = new Scanner(System.in);
        Printer.printInfo(textForUser);
        while (!scanner.hasNextBigDecimal()) {
            Printer.printInfo("You enter wrong value!!!");
            Printer.printInfo("Try again:");
            scanner.next();
        }
        return scanner.nextBigDecimal();
    }

    public static boolean isAnotherMaster() {
        String answer = "";
        while (!answer.equals("y") && !answer.equals("n")) {
            answer = getStringUser("Add another master to the order? y/n");
            if (!answer.equals("y") && !answer.equals("n")) {
                Printer.printInfo("You have entered wrong answer!");
            }
        }
        return answer.equals("n");
    }
}