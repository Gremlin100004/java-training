package com.senla.carservice;

import com.senla.carservice.annotation.Singleton;
import com.senla.carservice.objectadjuster.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.Place;
import com.senla.carservice.exception.CsvException;
import com.senla.carservice.util.FileUtil;
import com.senla.carservice.util.ParameterUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class CsvPlace {
    @ConfigProperty
    private String placePath;
    @ConfigProperty
    private String fieldSeparator;

    public CsvPlace() {
    }

    public void exportPlaces(List<Place> places) {
        if (places == null) {
            throw new CsvException("argument is null");
        }
        List<String> valueCsv = places.stream()
            .map(this::convertToCsv)
            .collect(Collectors.toList());
        FileUtil.saveCsv(valueCsv, placePath);
    }

    public List<Place> importPlaces() {
        List<String> csvLinesPlace = FileUtil.getCsv(placePath);
        return csvLinesPlace.stream()
            .map(this::getPlaceFromCsv)
            .collect(Collectors.toList());
    }

    private Place getPlaceFromCsv(String line) {
        if (line == null) {
            throw new CsvException("argument is null");
        }
        List<String> values = Arrays.asList(line.split(fieldSeparator));
        Place place = new Place();
        place.setId(ParameterUtil.getValueLong(values.get(0)));
        place.setNumber(ParameterUtil.getValueInteger(values.get(1)));
        place.setBusyStatus(ParameterUtil.getValueBoolean(values.get(2)));
        place.setDelete(ParameterUtil.getValueBoolean(values.get(3)));
        return place;
    }

    private String convertToCsv(Place place) {
        if (place == null) {
            throw new CsvException("argument is null");
        }
        return place.getId() +
               fieldSeparator +
               place.getNumber() +
               fieldSeparator +
               place.getBusyStatus() +
               fieldSeparator +
               place.getDelete();
    }
}