package com.senla.carservice.util.stringutil;

public class StringUtil {
    private static final String REPEAT_SYMBOL = " ";

    public static String fillStringSpace(String value, int lengthString) {
        StringBuilder stringBuilder = new StringBuilder(value);
        if (value.length() < lengthString) {
            stringBuilder.append(REPEAT_SYMBOL.repeat(lengthString - value.length()));
        }
        return stringBuilder.toString();
    }
}