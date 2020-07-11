package com.senla.carservice.util.stringutil;

import com.senla.carservice.domain.Place;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class StringPlaces {
    private static final int LINE_LENGTH = 17;

    public static String getStringFromPlaces(List<Place> places) {
        String line = " " + String.join("", Collections.nCopies(LINE_LENGTH, "-")) + "\n";
        StringBuilder stringBuilder = new StringBuilder(line);
        stringBuilder.append("|").append(StringUtil.fillStringSpace("№", 3));
        stringBuilder.append("|").append(StringUtil.fillStringSpace("Number", 6));
        stringBuilder.append("|").append(StringUtil.fillStringSpace("Status", 6)).append("|\n");
        stringBuilder.append(line);
        int bound = places.size();
        IntStream.range(0, bound).forEach(
            i -> {
                stringBuilder.append("|").append(StringUtil.fillStringSpace(String.valueOf(i + 1), 3));
                stringBuilder.append("|")
                    .append(StringUtil.fillStringSpace(String.valueOf(places.get(i).getNumber()), 6));
                stringBuilder.append("|").append(places.get(i).getBusyStatus() ?
                                                 StringUtil.fillStringSpace("busy", 6) :
                                                 StringUtil.fillStringSpace("free", 6));
                stringBuilder.append("|\n");
            });
        stringBuilder.append(line);
        return stringBuilder.toString();
    }
}