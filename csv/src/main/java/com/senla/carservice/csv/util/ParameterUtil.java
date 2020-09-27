package com.senla.carservice.csv.util;

import com.senla.carservice.csv.exception.CsvException;
import com.senla.carservice.domain.enumaration.StatusOrder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public final class ParameterUtil {

    public static Long getValueLong(String value) {
        log.debug("Method getValueLong");
        log.trace("Parameter value: {}", value);
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            throw new CsvException("wrong structure csv file");
        }
    }

    public static Integer getValueInteger(String value) {
        log.debug("Method getValueInteger");
        log.trace("Parameter value: {}", value);
        if (value.equals("null")) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            throw new CsvException("wrong structure csv file");
        }
    }

    public static String checkValueString(String value) {
        log.debug("Method checkValueString");
        log.trace("Parameter value: {}", value);
        if (value == null) {
            throw new CsvException("wrong structure csv file");
        }
        return value;
    }

    public static StatusOrder getValueStatus(String value) {
        log.debug("Method getValueStatus");
        log.trace("Parameter value: {}", value);
        try {
            return StatusOrder.valueOf(value);
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            throw new CsvException("wrong structure csv file");
        }
    }

    public static Boolean getValueBoolean(String value) {
        log.debug("Method getValueBoolean");
        log.trace("Parameter value: {}", value);
        if (!value.equals("true") && !value.equals("false")) {
            throw new CsvException("wrong structure csv file");
        }
        return Boolean.parseBoolean(value);
    }
}