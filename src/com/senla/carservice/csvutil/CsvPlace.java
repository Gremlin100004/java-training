package com.senla.carservice.csvutil;

import com.senla.carservice.domain.Place;
import com.senla.carservice.exception.BusinessException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvPlace {
    private static final String PLACE_PATH = "resources/csv/places.csv";
    private static final String COMMA = ",";

    private CsvPlace() {
    }

    public static void exportPlaces(List<Place> places) {
        if (places == null) {
            throw new BusinessException("argument is null");
        }
        List<String> valueCsv = places.stream().map(CsvPlace::convertToCsv).collect(Collectors.toList());
        FileUtil.saveCsv(valueCsv, PLACE_PATH);
    }

    public static List<Place> importPlaces() {
        List<String> csvLinesPlace = FileUtil.getCsv(PLACE_PATH);
        return csvLinesPlace.stream().map(CsvPlace::getPlaceFromCsv).collect(Collectors.toList());
    }

    private static Place getPlaceFromCsv(String line) {
        if (line == null) {
            throw new BusinessException("argument is null");
        }
        List<String> values = Arrays.asList(line.split(COMMA));
        Place place = new Place();
        place.setId(ParametrUtil.getValueLong(values.get(0)));
        place.setNumber(ParametrUtil.getValueInteger(values.get(1)));
        place.setBusyStatus(ParametrUtil.getValueBoolean(values.get(2)));
        return place;
    }

    private static String convertToCsv(Place place) {
        return place.getId() + COMMA + place.getNumber() + COMMA + place.isBusyStatus();
    }
}