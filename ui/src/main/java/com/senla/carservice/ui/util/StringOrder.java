package com.senla.carservice.ui.util;

import com.senla.carservice.dto.OrderDto;
import com.senla.carservice.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringOrder {

    private static final int LINE_LENGTH = 167;
    private static final String SYMBOL_FOR_JOIN_METHOD = "";
    private static final String START_OF_LINE_DELIMITER = " ";
    private static final String LINE_SEPARATOR = "-";
    private static final String END_OF_LINE = "\n";
    private static final String SPLIT_COLUMNS = "|";
    private static final String FIRST_COLUMN_HEADING = "№";
    private static final String SECOND_COLUMN_HEADING = "Automaker";
    private static final String THIRD_COLUMN_HEADING = "model";
    private static final String FORTH_COLUMN_HEADING = "Registration Number";
    private static final String FIFTH_COLUMN_HEADING = "Creation Time";
    private static final String SIXTH_COLUMN_HEADING = "Execution Start Time";
    private static final String SEVENTH_COLUMN_HEADING = "Lead Time";
    private static final String EIGHTH_COLUMN_HEADING = "Price";
    private static final String NINTH_COLUMN_HEADING = "Status";
    private static final String TENTH_COLUMN_HEADING = "Deleted";
    private static final int LENGTH_SPACE_FIRST_COLUMN = 4;
    private static final int LENGTH_SPACE_SECOND_COLUMN = 10;
    private static final int LENGTH_SPACE_THIRD_COLUMN = 12;
    private static final int LENGTH_SPACE_FORTH = 19;
    private static final int LENGTH_SPACE_TIME = 28;
    private static final int LENGTH_SPACE_EIGHTH_COLUMN = 10;
    private static final int LENGTH_SPACE_NINTH_COLUMN = 12;
    private static final int LENGTH_SPACE_TENTH_COLUMN = 7;
    private static final int INDEX_ADDITION = 1;
    private static final Logger LOGGER = LoggerFactory.getLogger(StringOrder.class);

    public static String getStringFromOrder(List<OrderDto> ordersDto) {
        LOGGER.debug("Method getStringFromOrder");
        LOGGER.trace("Parameter orders: {}", ordersDto);
        String line = START_OF_LINE_DELIMITER + String.join(SYMBOL_FOR_JOIN_METHOD, Collections.nCopies(LINE_LENGTH, LINE_SEPARATOR)) +
                      END_OF_LINE;
        StringBuilder stringBuilder = new StringBuilder(line);
        stringBuilder.append(SPLIT_COLUMNS).append(StringUtil.fillStringSpace(FIRST_COLUMN_HEADING,
                                                                              LENGTH_SPACE_FIRST_COLUMN));
        stringBuilder.append(SPLIT_COLUMNS).append(StringUtil.fillStringSpace(SECOND_COLUMN_HEADING,
                                                                              LENGTH_SPACE_SECOND_COLUMN));
        stringBuilder.append(SPLIT_COLUMNS).append(StringUtil.fillStringSpace(THIRD_COLUMN_HEADING,
                                                                              LENGTH_SPACE_THIRD_COLUMN));
        stringBuilder.append(SPLIT_COLUMNS)
            .append(StringUtil.fillStringSpace(FORTH_COLUMN_HEADING, LENGTH_SPACE_FORTH));
        stringBuilder.append(SPLIT_COLUMNS).append(StringUtil.fillStringSpace(FIFTH_COLUMN_HEADING, LENGTH_SPACE_TIME));
        stringBuilder.append(SPLIT_COLUMNS).append(StringUtil.fillStringSpace(SIXTH_COLUMN_HEADING, LENGTH_SPACE_TIME));
        stringBuilder.append(SPLIT_COLUMNS).append(StringUtil.fillStringSpace(SEVENTH_COLUMN_HEADING, LENGTH_SPACE_TIME));
        stringBuilder.append(SPLIT_COLUMNS).append(StringUtil.fillStringSpace(EIGHTH_COLUMN_HEADING,
                                                                              LENGTH_SPACE_EIGHTH_COLUMN));
        stringBuilder.append(SPLIT_COLUMNS).append(StringUtil.fillStringSpace(NINTH_COLUMN_HEADING,
                                                                              LENGTH_SPACE_NINTH_COLUMN));
        stringBuilder.append(SPLIT_COLUMNS).append(StringUtil.fillStringSpace(TENTH_COLUMN_HEADING,
                                                                              LENGTH_SPACE_TENTH_COLUMN))
            .append(SPLIT_COLUMNS + END_OF_LINE);
        stringBuilder.append(line);
        IntStream.range(0, ordersDto.size()).forEach(i -> {
            stringBuilder.append(SPLIT_COLUMNS)
                .append(StringUtil.fillStringSpace(String.valueOf(i + INDEX_ADDITION), LENGTH_SPACE_FIRST_COLUMN));
            stringBuilder.append(SPLIT_COLUMNS)
                .append(StringUtil.fillStringSpace(ordersDto.get(i).getAutomaker(), LENGTH_SPACE_SECOND_COLUMN));
            stringBuilder.append(SPLIT_COLUMNS)
                .append(StringUtil.fillStringSpace(ordersDto.get(i).getModel(), LENGTH_SPACE_THIRD_COLUMN));
            stringBuilder.append(SPLIT_COLUMNS)
                .append(StringUtil.fillStringSpace(ordersDto.get(i).getRegistrationNumber(), LENGTH_SPACE_FORTH));
            stringBuilder.append(SPLIT_COLUMNS).append(StringUtil.fillStringSpace(
                DateUtil.getStringFromDate(ordersDto.get(i).getCreationTime(), true), LENGTH_SPACE_TIME));
            stringBuilder.append(SPLIT_COLUMNS).append(StringUtil.fillStringSpace(
                DateUtil.getStringFromDate(ordersDto.get(i).getExecutionStartTime(), true), LENGTH_SPACE_TIME));
            stringBuilder.append(SPLIT_COLUMNS).append(StringUtil.fillStringSpace(
                DateUtil.getStringFromDate(ordersDto.get(i).getLeadTime(), true), LENGTH_SPACE_TIME));
            stringBuilder.append(SPLIT_COLUMNS).append(
                StringUtil.fillStringSpace(String.valueOf(ordersDto.get(i).getPrice()), LENGTH_SPACE_EIGHTH_COLUMN));
            stringBuilder.append(SPLIT_COLUMNS).append(
                StringUtil.fillStringSpace(String.valueOf(ordersDto.get(i).getStatus()), LENGTH_SPACE_NINTH_COLUMN));
            stringBuilder.append(SPLIT_COLUMNS).append(
                StringUtil.fillStringSpace(String.valueOf(ordersDto.get(i).isDeleteStatus()), LENGTH_SPACE_TENTH_COLUMN))
                .append(SPLIT_COLUMNS + END_OF_LINE);
        });
        stringBuilder.append(line);
        return stringBuilder.toString();
    }

    public static List<String> getListId(List<OrderDto> ordersDto) {
        return ordersDto.stream()
            .map(order -> String.valueOf(order.getId()))
            .collect(Collectors.toList());
    }
}