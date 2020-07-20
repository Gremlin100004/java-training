package com.senla.carservice.util.stringutil;

import com.senla.carservice.domain.Order;

import java.util.Collections;
import java.util.List;

public class StringOrder {
    private static final int LINE_LENGTH = 167;
    private static final String EMPTY_LITERAL = "";
    private static final String SPACE = " ";
    private static final String DASH = "-";
    private static final String NEWLINE_CHARACTER = "\n";
    private static final String VERTICAL_BAR = "|";
    private static final String NUMBER_SYMBOL = "№";
    private static final String AUTOMAKER = "Automaker";
    private static final String MODEL = "MODEL";
    private static final String REGISTRATION_NUMBER = "Registration Number";
    private static final String CREATION_TIME = "Creation Time";
    private static final String EXECUTION_START_TIME = "Execution Start Time";
    private static final String LEAD_TIME = "Lead Time";
    private static final String PRICE = "Price";
    private static final String STATUS = "Status";
    private static final String DELETED = "Deleted";
    private static final int LENGTH_SPACE_NUMBER = 4;
    private static final int LENGTH_SPACE_AUTOMAKER = 10;
    private static final int LENGTH_SPACE_MODEL = 12;
    private static final int LENGTH_SPACE_REGISTRATION_NUMBER = 19;
    private static final int LENGTH_SPACE_TIME = 28;
    private static final int LENGTH_SPACE_PRICE = 10;
    private static final int LENGTH_SPACE_STATUS = 12;
    private static final int LENGTH_SPACE_DELETED = 7;
    private static final int INDEX_ADDITION = 1;

    public static String getStringFromOrder(List<Order> orders) {
        String line = SPACE + String.join(EMPTY_LITERAL, Collections.nCopies(LINE_LENGTH, DASH)) + NEWLINE_CHARACTER;
        StringBuilder stringBuilder = new StringBuilder(line);
        stringBuilder.append(VERTICAL_BAR).append(StringUtil.fillStringSpace(NUMBER_SYMBOL, LENGTH_SPACE_NUMBER));
        stringBuilder.append(VERTICAL_BAR).append(StringUtil.fillStringSpace(AUTOMAKER, LENGTH_SPACE_AUTOMAKER));
        stringBuilder.append(VERTICAL_BAR).append(StringUtil.fillStringSpace(MODEL, LENGTH_SPACE_MODEL));
        stringBuilder.append(VERTICAL_BAR)
            .append(StringUtil.fillStringSpace(REGISTRATION_NUMBER, LENGTH_SPACE_REGISTRATION_NUMBER));
        stringBuilder.append(VERTICAL_BAR).append(StringUtil.fillStringSpace(CREATION_TIME, LENGTH_SPACE_TIME));
        stringBuilder.append(VERTICAL_BAR).append(StringUtil.fillStringSpace(EXECUTION_START_TIME, LENGTH_SPACE_TIME));
        stringBuilder.append(VERTICAL_BAR).append(StringUtil.fillStringSpace(LEAD_TIME, LENGTH_SPACE_TIME));
        stringBuilder.append(VERTICAL_BAR).append(StringUtil.fillStringSpace(PRICE, LENGTH_SPACE_PRICE));
        stringBuilder.append(VERTICAL_BAR).append(StringUtil.fillStringSpace(STATUS, LENGTH_SPACE_STATUS));
        stringBuilder.append(VERTICAL_BAR).append(StringUtil.fillStringSpace(DELETED, LENGTH_SPACE_DELETED))
            .append(VERTICAL_BAR + NEWLINE_CHARACTER);
        stringBuilder.append(line);
        for (int i = 0; i < orders.size(); i++) {
            stringBuilder.append(VERTICAL_BAR)
                .append(StringUtil.fillStringSpace(String.valueOf(i + INDEX_ADDITION), LENGTH_SPACE_NUMBER));
            stringBuilder.append(VERTICAL_BAR)
                .append(StringUtil.fillStringSpace(orders.get(i).getAutomaker(), LENGTH_SPACE_AUTOMAKER));
            stringBuilder.append(VERTICAL_BAR)
                .append(StringUtil.fillStringSpace(orders.get(i).getModel(), LENGTH_SPACE_MODEL));
            stringBuilder.append(VERTICAL_BAR)
                .append(StringUtil
                            .fillStringSpace(orders.get(i).getRegistrationNumber(), LENGTH_SPACE_REGISTRATION_NUMBER));
            stringBuilder.append(VERTICAL_BAR)
                .append(StringUtil.fillStringSpace(String.valueOf(orders.get(i).getCreationTime()), LENGTH_SPACE_TIME));
            stringBuilder.append(VERTICAL_BAR)
                .append(StringUtil
                            .fillStringSpace(String.valueOf(orders.get(i).getExecutionStartTime()), LENGTH_SPACE_TIME));
            stringBuilder.append(VERTICAL_BAR)
                .append(StringUtil.fillStringSpace(String.valueOf(orders.get(i).getLeadTime()), LENGTH_SPACE_TIME));
            stringBuilder.append(VERTICAL_BAR)
                .append(StringUtil.fillStringSpace(String.valueOf(orders.get(i).getPrice()), LENGTH_SPACE_PRICE));
            stringBuilder.append(VERTICAL_BAR)
                .append(StringUtil.fillStringSpace(String.valueOf(orders.get(i).getStatus()), LENGTH_SPACE_STATUS));
            stringBuilder.append(VERTICAL_BAR)
                .append(
                    StringUtil.fillStringSpace(String.valueOf(orders.get(i).isDeleteStatus()), LENGTH_SPACE_DELETED))
                .append(VERTICAL_BAR + NEWLINE_CHARACTER);
        }
        stringBuilder.append(line);
        return stringBuilder.toString();
    }
}