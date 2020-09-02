package com.senla.carservice.csv.util;

import com.senla.carservice.csv.exception.CsvException;
import com.senla.carservice.service.enumaration.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ParameterUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterUtil.class);

    private ParameterUtil() {
    }

    public static Long getValueLong(String value) {
        LOGGER.debug("Method getValueLong");
        LOGGER.trace("Parameter value: {}", value);
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            LOGGER.error(e.getMessage());
            throw new CsvException("wrong structure csv file");
        }
    }

    public static Integer getValueInteger(String value) {
        LOGGER.debug("Method getValueInteger");
        LOGGER.trace("Parameter value: {}", value);
        if (value.equals("null")) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            LOGGER.error(e.getMessage());
            throw new CsvException("wrong structure csv file");
        }
    }

    public static String checkValueString(String value) {
        LOGGER.debug("Method checkValueString");
        LOGGER.trace("Parameter value: {}", value);
        if (value == null) {
            throw new CsvException("wrong structure csv file");
        }
        return value;
    }

    public static Status getValueStatus(String value) {
        LOGGER.debug("Method getValueStatus");
        LOGGER.trace("Parameter value: {}", value);
        try {
            return Status.valueOf(value);
        } catch (NumberFormatException e) {
            LOGGER.error(e.getMessage());
            throw new CsvException("wrong structure csv file");
        }
    }

    public static Boolean getValueBoolean(String value) {
        LOGGER.debug("Method getValueBoolean");
        LOGGER.trace("Parameter value: {}", value);
        if (!value.equals("true") && !value.equals("false")) {
            throw new CsvException("wrong structure csv file");
        }
        return Boolean.parseBoolean(value);
    }
}