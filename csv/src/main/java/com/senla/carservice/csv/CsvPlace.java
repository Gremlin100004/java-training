package com.senla.carservice.csv;

import com.senla.carservice.Place;
import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.csv.exception.CsvException;
import com.senla.carservice.csv.util.FileUtil;
import com.senla.carservice.csv.util.ParameterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class CsvPlace {

    @ConfigProperty
    private String placePath;
    @ConfigProperty
    private String fieldSeparator;
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvPlace.class);

    public CsvPlace() {
    }

    public void exportPlaces(List<Place> places) {
        LOGGER.debug("Method exportPlaces");
        LOGGER.trace("Parameter places: {}", places);
        if (places == null) {
            throw new CsvException("argument is null");
        }
        List<String> valueCsv = places.stream()
            .map(this::convertToCsv)
            .collect(Collectors.toList());
        FileUtil.saveCsv(valueCsv, placePath);
    }

    public List<Place> importPlaces() {
        LOGGER.debug("Method importPlaces");
        List<String> csvLinesPlace = FileUtil.getCsv(placePath);
        return csvLinesPlace.stream()
            .map(this::getPlaceFromCsv)
            .collect(Collectors.toList());
    }

    private Place getPlaceFromCsv(String line) {
        LOGGER.debug("Method getPlaceFromCsv");
        LOGGER.debug("Parameter line: {}", line);
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
        LOGGER.debug("Method convertToCsv");
        LOGGER.debug("Parameter place: {}", place);
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