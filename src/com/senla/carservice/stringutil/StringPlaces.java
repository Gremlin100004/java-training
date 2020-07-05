package com.senla.carservice.stringutil;

import com.senla.carservice.domain.Place;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class StringPlaces {
    private static final int LENGTH = 17;

    public static String getStringFromPlaces(List<Place> places) {
        String line = String.format(" %s\n", String.join("", Collections.nCopies(LENGTH, "-")));
        StringBuilder stringBuilder = new StringBuilder(line);
        stringBuilder.append(String.format("|%-3s|%-6s|%-6s|\n",
                                           "№", "Number", "Status"
                                          ));
        stringBuilder.append(line);
        // ужасное форматирование, неужели тебе самому нравится на это смотреть?
        // основная рабочая область пустая, а весь код уехал столбиком вправо за пределы рекомендуемой длины
        // строки
        // стримы можно переносить на строку ниже через точку (точка на новой строке)
        // а тернарный оператор можно переносить по ? и : оставляя их на предыдущей
        // я отформатирую, уберу также ненужные скобки, сравни
        IntStream.range(0, places.size())
                .forEach(i ->
                        stringBuilder.append(places.get(i).isBusyStatus() ?
                                String.format("|%-3s|%6s|%-6s|\n", i + 1, places.get(i).getNumber(), "busy") :
                                String.format("|%-3s|%-6s|%-6s|\n", i + 1, places.get(i).getNumber(), "free")));
        stringBuilder.append(line);
        return stringBuilder.toString();
    }
}