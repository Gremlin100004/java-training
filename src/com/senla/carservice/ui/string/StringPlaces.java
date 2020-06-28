package com.senla.carservice.ui.string;

import com.senla.carservice.domain.Place;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class StringPlaces {
    private static final int LENGTH = 57;

    public static String getStringFromPlaces(List<Place> places) {
        String line = String.format(" %s\n", String.join("", Collections.nCopies(LENGTH, "-")));
        StringBuilder stringBuilder = new StringBuilder(line);
        stringBuilder.append(String.format("|%-3s|%-3s|%-6s|\n",
                "№", "Number", "Status"
        ));
        stringBuilder.append(line);
        IntStream.range(0, places.size()).forEach(i -> {
            stringBuilder.append(places.get(i).isBusyStatus() ? String.format("|%-3s|%-3s|%-6s|\n",
                    i + 1, places.get(i).getNumber(),
                    "busy"
            ) : String.format("|%-3s|%-3s|%-6s|\n",
                    i + 1, places.get(i).getNumber(),
                    "free"
            ));
        });
        stringBuilder.append(line);
        return stringBuilder.toString();
    }
}