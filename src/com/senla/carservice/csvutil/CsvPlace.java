package com.senla.carservice.csvutil;

import com.senla.carservice.domain.Place;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.util.PropertyLoader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvPlace {
    private static final String PLACE_PATH = PropertyLoader.getPropertyValue("csvPathPlace");
    private static final String FIELD_SEPARATOR = PropertyLoader.getPropertyValue("fieldSeparator");

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
        List<String> values = Arrays.asList(line.split(FIELD_SEPARATOR));
        Place place = new Place();
        place.setId(ParameterUtil.getValueLong(values.get(0)));
        place.setNumber(ParameterUtil.getValueInteger(values.get(1)));
        place.setBusyStatus(ParameterUtil.getValueBoolean(values.get(2)));
        return place;
    }

    private static String convertToCsv(Place place) {
        return place.getId() + FIELD_SEPARATOR + place.getNumber() + FIELD_SEPARATOR + place.isBusyStatus();
    }
}