package com.senla.carservice.csv;

import com.senla.carservice.domain.Place;
import com.senla.carservice.exception.NumberObjectZeroException;
import com.senla.carservice.repository.PlaceRepository;
import com.senla.carservice.repository.PlaceRepositoryImpl;

import java.util.Arrays;
import java.util.List;

public class CsvPlace {
    private static final String PLACE_PATH = "csv//places.csv";
    private static final String COMMA = ",";

    private CsvPlace() {
    }

    public static String exportPlaces(List<Place> places) {
        StringBuilder valueCsv = new StringBuilder();
        for (int i = 0; i < places.size(); i++) {
            if (i == places.size() - 1) {
                valueCsv.append(convertToCsv(places.get(i), false));
            } else {
                valueCsv.append(convertToCsv(places.get(i), true));
            }
        }
        return FileUtil.saveCsv(valueCsv, PLACE_PATH);
    }

    public static String importPlaces() {
        List<String> csvLinesPlace = FileUtil.getCsv(PLACE_PATH);
        if (csvLinesPlace == null) throw new NumberObjectZeroException("export problem");
        csvLinesPlace.forEach(line -> {
            Place place = getPlaceFromCsv(line);
            PlaceRepositoryImpl.getInstance().updatePlace(place);
        });
        return "Masters have been import successfully!";
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