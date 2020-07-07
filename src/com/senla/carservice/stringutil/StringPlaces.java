package com.senla.carservice.stringutil;

import com.senla.carservice.domain.Place;

import java.util.Collections;
import java.util.List;

public class StringPlaces {
    private static final int LENGTH = 17;

    public static String getStringFromPlaces(List<Place> places) {
        String line = String.format(" %s\n", String.join("", Collections.nCopies(LENGTH, "-")));
        StringBuilder stringBuilder = new StringBuilder(line);
        stringBuilder.append(String.format("|%-3s|%-6s|%-6s|\n",
                                           "№", "Number", "Status"
                                          ));
        stringBuilder.append(line);
        int bound = places.size();
        for (int i = 0; i < bound; i++) {
            if (places.get(i).getBusyStatus()) {
                stringBuilder.append(String.format("|%-3s|%6s|%-6s|\n",
                                                   i + 1, places.get(i).getNumber(),
                                                   "busy"
                                                  ));
            } else {
                stringBuilder.append(String.format("|%-3s|%-6s|%-6s|\n",
                                                   i + 1, places.get(i).getNumber(),
                                                   "free"
                                                  ));
            }
        }
        stringBuilder.append(line);
        return stringBuilder.toString();
    }
}