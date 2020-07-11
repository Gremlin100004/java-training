package com.senla.carservice.util.stringutil;

public class StringUtil {
    public static String fillStringSpace(String value, int lengthString) {
        StringBuilder stringBuilder = new StringBuilder(value);
        if (value.length() < lengthString) {
            stringBuilder.append(" ".repeat(lengthString - value.length()));
        }
        return stringBuilder.toString();
    }
}