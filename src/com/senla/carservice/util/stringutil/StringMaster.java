package com.senla.carservice.util.stringutil;

import com.senla.carservice.domain.Master;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class StringMaster {
    private static final int LINE_LENGTH = 38;

    public static String getStringFromMasters(List<Master> masters) {
        String line = String.join("", Collections.nCopies(LINE_LENGTH, "-")) + "\n";
        StringBuilder stringBuilder = new StringBuilder(line);
        stringBuilder.append("|").append(StringUtil.fillStringSpace("№", 3));
        stringBuilder.append("|").append(StringUtil.fillStringSpace("Name", 20));
        stringBuilder.append("|").append(StringUtil.fillStringSpace("Number order", 12)).append("|\n");
        stringBuilder.append(line);
        int bound = masters.size();
        IntStream.range(0, bound)
            .forEach(i -> {
            stringBuilder.append("|").append(StringUtil.fillStringSpace(String.valueOf(i + 1), 3));
            stringBuilder.append("|").append(StringUtil.fillStringSpace(masters.get(i).getName(), 20));
            stringBuilder.append("|")
                .append(StringUtil.fillStringSpace(String.valueOf(masters.get(i).getOrders().size()), 12));
            stringBuilder.append("|\n");
        });
        stringBuilder.append(line);
        return stringBuilder.toString();
    }
}