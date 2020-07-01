package com.senla.carservice.ui.util;

import java.util.regex.Pattern;

public class Checker {

    private Checker() {
    }

    public static boolean isSymbolsString(String text) {
        return !Pattern.compile("[a-zA-Z_]").matcher(text).find();
        }

    public static boolean isSymbolsStringNumber(String text) {
        // я могу ошибаться, но кажется, эту регулярку можно упростить до
        // .compile("[0-9.:]")
        return !Pattern.compile("[0-9\\.\\:]").matcher(text).find();
    }
}