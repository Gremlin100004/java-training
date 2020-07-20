package com.senla.carservice.util.stringutil;

public class StringUtil {
    private static final String SPACE = " ";

    public static String fillStringSpace(String value, int lengthString) {
        StringBuilder stringBuilder = new StringBuilder(value);
        if (value.length() < lengthString) {
            stringBuilder.append(SPACE.repeat(lengthString - value.length()));
        }
        return stringBuilder.toString();
    }
}