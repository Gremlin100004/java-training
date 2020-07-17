package com.senla.carservice.util.stringutil;

import com.senla.carservice.domain.Order;

import java.util.Collections;
import java.util.List;

public class StringOrder {
    private static final int LINE_LENGTH = 167;

    // литералы в константы
    public static String getStringFromOrder(List<Order> orders) {
        String line = " " + String.join("", Collections.nCopies(LINE_LENGTH, "-")) + "\n";
        StringBuilder stringBuilder = new StringBuilder(line);
        stringBuilder.append("|").append(StringUtil.fillStringSpace("№", 4));
        stringBuilder.append("|").append(StringUtil.fillStringSpace("Automaker", 10));
        stringBuilder.append("|").append(StringUtil.fillStringSpace("Model", 12));
        stringBuilder.append("|").append(StringUtil.fillStringSpace("Registration Number", 19));
        stringBuilder.append("|").append(StringUtil.fillStringSpace("Creation Time", 28));
        stringBuilder.append("|").append(StringUtil.fillStringSpace("Execution Start Time", 28));
        stringBuilder.append("|").append(StringUtil.fillStringSpace("Lead Time", 28));
        stringBuilder.append("|").append(StringUtil.fillStringSpace("Price", 10));
        stringBuilder.append("|").append(StringUtil.fillStringSpace("Status", 12));
        stringBuilder.append("|").append(StringUtil.fillStringSpace("Deleted", 7)).append("|\n");
        stringBuilder.append(line);
        for (int i = 0; i < orders.size(); i++) {
            stringBuilder.append("|").append(StringUtil.fillStringSpace(String.valueOf(i + 1), 4));
            stringBuilder.append("|").append(StringUtil.fillStringSpace(orders.get(i).getAutomaker(), 10));
            stringBuilder.append("|").append(StringUtil.fillStringSpace(orders.get(i).getModel(), 12));
            stringBuilder.append("|").append(StringUtil.fillStringSpace(orders.get(i).getRegistrationNumber(), 19));
            stringBuilder.append("|")
                .append(StringUtil.fillStringSpace(String.valueOf(orders.get(i).getCreationTime()), 28));
            stringBuilder.append("|")
                .append(StringUtil.fillStringSpace(String.valueOf(orders.get(i).getExecutionStartTime()), 28));
            stringBuilder.append("|")
                .append(StringUtil.fillStringSpace(String.valueOf(orders.get(i).getLeadTime()), 28));
            stringBuilder.append("|").append(StringUtil.fillStringSpace(String.valueOf(orders.get(i).getPrice()), 10));
            stringBuilder.append("|").append(StringUtil.fillStringSpace(String.valueOf(orders.get(i).getStatus()), 12));
            stringBuilder.append("|")
                .append(StringUtil.fillStringSpace(String.valueOf(orders.get(i).isDeleteStatus()), 7)).append("|\n");
        }
        stringBuilder.append(line);
        return stringBuilder.toString();
    }
}