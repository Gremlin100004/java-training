package com.senla.carservice.util;

import com.senla.carservice.enumaration.Status;
import com.senla.carservice.exception.CsvException;

public class ParameterUtil {

    private ParameterUtil() {
    }

    public static Long getValueLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new CsvException("wrong structure csv file");
        }
    }

    public static Integer getValueInteger(String value) {
        if (value.equals("null")) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new CsvException("wrong structure csv file");
        }
    }

    public static String checkValueString(String value) {
        if (value == null) {
            throw new CsvException("wrong structure csv file");
        }
        return value;
    }

    public static Status getValueStatus(String value) {
        try {
            return Status.valueOf(value);
        } catch (NumberFormatException e) {
            throw new CsvException("wrong structure csv file");
        }
    }

    public static Boolean getValueBoolean(String value) {
        if (!value.equals("true") && !value.equals("false")) {
            throw new CsvException("wrong structure csv file");
        }
        return Boolean.parseBoolean(value);
    }
}