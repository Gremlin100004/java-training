package com.senla.carservice.csvutil;

import com.senla.carservice.domain.Place;
import com.senla.carservice.repository.PlaceRepositoryImpl;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvPlace {
    private static final String PLACE_PATH = "resources/csv/places.csv";
    private static final String COMMA = ",";

    private CsvPlace () {
    }

    public static void exportPlaces (List<Place> places) {
        List<String> valueCsv = new ArrayList<>();
        for (int i = 0; i < places.size(); i++) {
            if (i == places.size() - 1) {
                valueCsv.add(convertToCsv(places.get(i), false));
            } else {
                valueCsv.add(convertToCsv(places.get(i), true));
            }
        }
        FileUtil.saveCsv(valueCsv, PLACE_PATH);
    }

    public static List<Place> importPlaces () {
        List<String> csvLinesPlace = FileUtil.getCsv(PLACE_PATH);
        return csvLinesPlace.stream().map(CsvPlace::getPlaceFromCsv).collect(Collectors.toList());
    }

    private static Place getPlaceFromCsv (@NotNull String line) {
        List<String> values = Arrays.asList(line.split(COMMA));
        Place place = new Place();
        place.setId(Long.valueOf(values.get(0)));
        place.setNumber(Integer.valueOf(values.get(1)));
        place.setBusyStatus(Boolean.valueOf(values.get(2)));
        return place;
    }

    private static String convertToCsv (@NotNull Place place, boolean isLineFeed) {
        return isLineFeed ? place.getId() + COMMA + place.getNumber() + COMMA + place.isBusyStatus() + "\n"
                : place.getId() + COMMA + place.getNumber() + COMMA + place.isBusyStatus();
    }
}