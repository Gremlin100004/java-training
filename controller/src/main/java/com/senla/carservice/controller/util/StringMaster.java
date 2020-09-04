package com.senla.carservice.controller.util;

import com.senla.carservice.domain.Master;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class StringMaster {

    private static final int LINE_LENGTH = 44;
    private static final String SYMBOL_FOR_JOIN_METHOD = "";
    private static final String LINE_SEPARATOR = "-";
    private static final String START_OF_LINE_DELIMITER = " ";
    private static final String END_OF_LINE = "\n";
    private static final String SPLIT_COLUMNS = "|";
    private static final String FIRST_COLUMN_HEADING = "№";
    private static final String SECOND_COLUMN_HEADING = "Name";
    private static final String THIRD_COLUMN_HEADING = "Number order";
    private static final String FOURTH_COLUMN_HEADING = "Delete";
    private static final int LENGTH_SPACE_FIRST_COLUMN = 3;
    private static final int LENGTH_SPACE_SECOND_COLUMN = 20;
    private static final int LENGTH_SPACE_THIRD_COLUMN = 12;
    private static final int LENGTH_SPACE_FOURTH_COLUMN = 6;
    private static final int INDEX_ADDITION = 1;
    private static final Logger LOGGER = LoggerFactory.getLogger(StringMaster.class);


    public static String getStringFromMasters(List<Master> masters) {
        LOGGER.debug("Method getStringFromMasters");
        LOGGER.debug("Parameter masters: {}", masters);
        String line = START_OF_LINE_DELIMITER + String.join(SYMBOL_FOR_JOIN_METHOD, Collections.nCopies(LINE_LENGTH, LINE_SEPARATOR)) +
                      END_OF_LINE;
        StringBuilder stringBuilder = new StringBuilder(line);
        stringBuilder.append(SPLIT_COLUMNS)
            .append(StringUtil.fillStringSpace(FIRST_COLUMN_HEADING, LENGTH_SPACE_FIRST_COLUMN));
        stringBuilder.append(SPLIT_COLUMNS).append(StringUtil.fillStringSpace(SECOND_COLUMN_HEADING,
                                                                              LENGTH_SPACE_SECOND_COLUMN));
        stringBuilder.append(SPLIT_COLUMNS).append(StringUtil.fillStringSpace(THIRD_COLUMN_HEADING,
                                                                              LENGTH_SPACE_THIRD_COLUMN));
        stringBuilder.append(SPLIT_COLUMNS).append(StringUtil.fillStringSpace(FOURTH_COLUMN_HEADING,
                                                                              LENGTH_SPACE_FOURTH_COLUMN))
            .append(SPLIT_COLUMNS + END_OF_LINE);
        stringBuilder.append(line);
        int bound = masters.size();
        IntStream.range(0, bound)
            .forEach(i -> {
                stringBuilder.append(SPLIT_COLUMNS)
                    .append(StringUtil.fillStringSpace(String.valueOf(i + INDEX_ADDITION), LENGTH_SPACE_FIRST_COLUMN));
                stringBuilder.append(SPLIT_COLUMNS)
                    .append(StringUtil.fillStringSpace(masters.get(i).getName(), LENGTH_SPACE_SECOND_COLUMN));
                stringBuilder.append(SPLIT_COLUMNS)
                    .append(StringUtil.fillStringSpace(String.valueOf(masters.get(i).getOrders().size()),
                                                       LENGTH_SPACE_THIRD_COLUMN));
                stringBuilder.append(SPLIT_COLUMNS)
                    .append(StringUtil.fillStringSpace(String.valueOf(masters.get(i).getDelete()),
                                                       LENGTH_SPACE_FOURTH_COLUMN));
                stringBuilder.append(SPLIT_COLUMNS + END_OF_LINE);
            });
        stringBuilder.append(line);
        return stringBuilder.toString();
    }
}