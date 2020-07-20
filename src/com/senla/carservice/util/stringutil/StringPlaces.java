package com.senla.carservice.util.stringutil;

import com.senla.carservice.domain.Place;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class StringPlaces {
    private static final int LINE_LENGTH = 17;
    private static final String EMPTY_LITERAL = "";
    private static final String SPACE = " ";
    private static final String DASH = "-";
    private static final String NEWLINE_CHARACTER = "\n";
    private static final String VERTICAL_BAR = "|";
    private static final String NUMBER_SYMBOL = "№";
    private static final String NUMBER = "Number";
    private static final String STATUS = "Status";
    private static final String BUSY = "busy";
    private static final String FREE = "free";
    private static final int LENGTH_SPACE_NUMBER_SYMBOL = 3;
    private static final int LENGTH_SPACE_NUMBER = 6;
    private static final int LENGTH_SPACE_STATUS = 6;
    private static final int INDEX_ADDITION = 1;

    public static String getStringFromPlaces(List<Place> places) {
        String line = SPACE + String.join(EMPTY_LITERAL, Collections.nCopies(LINE_LENGTH, DASH)) + NEWLINE_CHARACTER;
        StringBuilder stringBuilder = new StringBuilder(line);
        stringBuilder.append(VERTICAL_BAR)
            .append(StringUtil.fillStringSpace(NUMBER_SYMBOL, LENGTH_SPACE_NUMBER_SYMBOL));
        stringBuilder.append(VERTICAL_BAR).append(StringUtil.fillStringSpace(NUMBER, LENGTH_SPACE_NUMBER));
        stringBuilder.append(VERTICAL_BAR).append(StringUtil.fillStringSpace(STATUS, LENGTH_SPACE_STATUS))
            .append(VERTICAL_BAR + NEWLINE_CHARACTER);
        stringBuilder.append(line);
        int bound = places.size();
        IntStream.range(0, bound)
            .forEach(i -> {
                stringBuilder.append(VERTICAL_BAR)
                    .append(StringUtil.fillStringSpace(String.valueOf(i + INDEX_ADDITION), LENGTH_SPACE_NUMBER_SYMBOL));
                stringBuilder.append(VERTICAL_BAR)
                    .append(StringUtil.fillStringSpace(String.valueOf(places.get(i).getNumber()), LENGTH_SPACE_NUMBER));
                stringBuilder.append(VERTICAL_BAR).append(places.get(i).getBusyStatus() ?
                                                          StringUtil.fillStringSpace(BUSY, LENGTH_SPACE_STATUS) :
                                                          StringUtil.fillStringSpace(FREE, LENGTH_SPACE_STATUS));
                stringBuilder.append(VERTICAL_BAR + NEWLINE_CHARACTER);
            });
        stringBuilder.append(line);
        return stringBuilder.toString();
    }
}