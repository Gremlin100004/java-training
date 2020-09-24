package com.senla.carservice.controller.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {

    private static final String REPEAT_SYMBOL = " ";
    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);

    public static String fillStringSpace(String value, int lengthString) {
        LOGGER.debug("Method fillStringSpace");
        LOGGER.trace("Parameter value: {}, lengthString: {}", value, lengthString);
        StringBuilder stringBuilder = new StringBuilder(value);
        if (value.length() < lengthString) {
            stringBuilder.append(REPEAT_SYMBOL.repeat(lengthString - value.length()));
        }
        return stringBuilder.toString();
    }
}