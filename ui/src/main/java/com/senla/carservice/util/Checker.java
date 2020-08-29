package com.senla.carservice.util;

import java.util.regex.Pattern;

public class Checker {

    private Checker() {
    }

    public static boolean isSymbolsString(String text) {
        return !Pattern.compile("^[a-zA-Z_]").matcher(text).find();
    }

    public static boolean isSymbolsStringNumber(String text) {
        return !Pattern.compile("^[a-zA-Z_0-9]").matcher(text).find();
    }

    public static boolean isSymbolsStringDate(String text) {
        return Pattern.compile("[0-9]{2}.[0-9]{2}.[0-9]{4}").matcher(text).find();
    }

    public static boolean isSymbolsDateTime(String text) {
        return Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}").matcher(text).find();
    }
}