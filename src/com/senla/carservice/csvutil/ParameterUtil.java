package com.senla.carservice.csvutil;

import com.senla.carservice.domain.Status;
import com.senla.carservice.exception.BusinessException;

public class ParameterUtil {

    private ParameterUtil() {
    }

    public static Long getValueLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new BusinessException("wrong structure csv file");
        }
    }

    public static Integer getValueInteger(String value) {
        if (value.equals("null")) {
            return null;
        } else {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new BusinessException("wrong structure csv file");
            }
        }
    }

    public static String checkValueString(String value) {
        if (value == null) {
            throw new BusinessException("wrong structure csv file");
        }
        return value;
    }

    public static Status getValueStatus(String value) {
        try {
            return Status.valueOf(value);
        } catch (NumberFormatException e) {
            throw new BusinessException("wrong structure csv file");
        }
    }

    public static Boolean getValueBoolean(String value) {
        if (!value.equals("true") && !value.equals("false")) {
            throw new BusinessException("wrong structure csv file");
        }
        return Boolean.parseBoolean(value);
    }
}
