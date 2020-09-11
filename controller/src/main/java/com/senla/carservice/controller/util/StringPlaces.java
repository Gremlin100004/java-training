package com.senla.carservice.controller.util;

import com.senla.carservice.domain.Place;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class StringPlaces {

    private static final int LINE_LENGTH = 24;
    private static final String SYMBOL_FOR_JOIN_METHOD = "";
    private static final String START_OF_LINE_DELIMITER = " ";
    private static final String LINE_SEPARATOR = "-";
    private static final String END_OF_LINE = "\n";
    private static final String SPLIT_COLUMNS = "|";
    private static final String FIRST_COLUMN_HEADING = "№";
    private static final String SECOND_COLUMN_HEADING = "Number";
    private static final String THIRD_COLUMN_HEADING = "Status";
    private static final String FOURTH_COLUMN_HEADING = "Delete";
    private static final String STATUS_ONE = "busy";
    private static final String STATUS_TWO = "free";
    private static final int LENGTH_SPACE_FIRST_COLUMN = 3;
    private static final int LENGTH_SPACE_SECOND_COLUMN = 6;
    private static final int LENGTH_SPACE_THIRD_COLUMN = 6;
    private static final int LENGTH_SPACE_FOURTH_COLUMN = 6;
    private static final int INDEX_ADDITION = 1;
    private static final Logger LOGGER = LoggerFactory.getLogger(StringPlaces.class);

    public static String getStringFromPlaces(List<Place> places) {
        LOGGER.debug("Method getStringFromPlaces");
        LOGGER.trace("Parameter places: {}", places);
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
        int bound = places.size();
        IntStream.range(0, bound)
            .forEach(i -> {
                stringBuilder.append(SPLIT_COLUMNS)
                    .append(StringUtil.fillStringSpace(String.valueOf(i + INDEX_ADDITION), LENGTH_SPACE_FIRST_COLUMN));
                stringBuilder.append(SPLIT_COLUMNS)
                    .append(StringUtil.fillStringSpace(String.valueOf(places.get(i).getNumber()),
                                                       LENGTH_SPACE_SECOND_COLUMN));
                stringBuilder.append(SPLIT_COLUMNS).append(places.get(i).getBusy() ?
                                                   StringUtil.fillStringSpace(STATUS_ONE, LENGTH_SPACE_THIRD_COLUMN) :
                                                   StringUtil.fillStringSpace(STATUS_TWO, LENGTH_SPACE_THIRD_COLUMN));
                stringBuilder.append(SPLIT_COLUMNS)
                   .append(StringUtil.fillStringSpace(String.valueOf(places.get(i).getDelete()), LENGTH_SPACE_FOURTH_COLUMN));
                stringBuilder.append(SPLIT_COLUMNS + END_OF_LINE);
            });
        stringBuilder.append(line);
        return stringBuilder.toString();
    }
}