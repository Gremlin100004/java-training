package com.senla.carservice.util.stringutil;

import com.senla.carservice.domain.Master;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class StringMaster {
    private static final int LINE_LENGTH = 37;
    private static final String EMPTY_LITERAL = "";
    private static final String DASH = "-";
    private static final String SPACE = " ";
    private static final String NEWLINE_CHARACTER = "\n";
    private static final String VERTICAL_BAR = "|";
    private static final String NUMBER_SYMBOL = "№";
    private static final String NAME = "Name";
    private static final String NUMBER_ORDER = "Number order";
    private static final int LENGTH_SPACE_NUMBER_SYMBOL = 3;
    private static final int LENGTH_SPACE_NAME = 20;
    private static final int LENGTH_SPACE_NUMBER_ORDER = 12;
    private static final int INDEX_ADDITION = 1;

    public static String getStringFromMasters(List<Master> masters) {
        String line = SPACE + String.join(EMPTY_LITERAL, Collections.nCopies(LINE_LENGTH, DASH)) + NEWLINE_CHARACTER;
        StringBuilder stringBuilder = new StringBuilder(line);
        stringBuilder.append(VERTICAL_BAR)
            .append(StringUtil.fillStringSpace(NUMBER_SYMBOL, LENGTH_SPACE_NUMBER_SYMBOL));
        stringBuilder.append(VERTICAL_BAR).append(StringUtil.fillStringSpace(NAME, LENGTH_SPACE_NAME));
        stringBuilder.append(VERTICAL_BAR).append(StringUtil.fillStringSpace(NUMBER_ORDER, LENGTH_SPACE_NUMBER_ORDER))
            .append(VERTICAL_BAR + NEWLINE_CHARACTER);
        stringBuilder.append(line);
        int bound = masters.size();
        IntStream.range(0, bound)
            .forEach(i -> {
                stringBuilder.append(VERTICAL_BAR)
                    .append(StringUtil.fillStringSpace(String.valueOf(i + INDEX_ADDITION), LENGTH_SPACE_NUMBER_SYMBOL));
                stringBuilder.append(VERTICAL_BAR)
                    .append(StringUtil.fillStringSpace(masters.get(i).getName(), LENGTH_SPACE_NAME));
                stringBuilder.append(VERTICAL_BAR)
                    .append(StringUtil.fillStringSpace(String.valueOf(masters.get(i).getOrders().size()),
                                                       LENGTH_SPACE_NUMBER_ORDER));
                stringBuilder.append(VERTICAL_BAR + NEWLINE_CHARACTER);
            });
        stringBuilder.append(line);
        return stringBuilder.toString();
    }
}