package com.senla.carservice.csvutil;

import com.senla.carservice.domain.Place;
import com.senla.carservice.repository.PlaceRepositoryImpl;

import java.util.Arrays;
import java.util.List;

public class CsvPlace {
    private static final String PLACE_PATH = "csv//places.csv";
    private static final String COMMA = ",";

    private CsvPlace() {
    }

    public static String exportPlaces(List<Place> places) {
        String valueCsv;
        for (int i = 0; i < places.size(); i++) {
            if (i == places.size() - 1) {
                valueCsv = convertToCsv(places.get(i), false);
            } else {
                valueCsv = convertToCsv(places.get(i), true);
            }
            FileUtil.saveCsv(valueCsv, PLACE_PATH, i != 0);
        }
        return "save successfully";
    }

    public static String importPlaces() {
        List<String> csvLinesPlace = FileUtil.getCsv(PLACE_PATH);
        csvLinesPlace.forEach(line -> {
            Place place = getPlaceFromCsv(line);
            PlaceRepositoryImpl.getInstance().updatePlace(place);
        });
        return "Places have been import successfully!";
    }

    private static Place getPlaceFromCsv(String line) {
        List<String> values = Arrays.asList(line.split(COMMA));
        Place place = new Place();
        place.setId(Long.valueOf(values.get(0)));
        place.setNumber(Integer.valueOf(values.get(1)));
        place.setBusyStatus(Boolean.valueOf(values.get(2)));
        return place;
    }

    private static String convertToCsv(Place place, boolean isLineFeed) {
        if (isLineFeed) {
            return String.format("%s,%s,%s\n", place.getId(), place.getNumber(), place.isBusyStatus());
        } else {
            return String.format("%s,%s,%s", place.getId(), place.getNumber(), place.isBusyStatus());
        }
    }
}