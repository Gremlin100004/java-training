package com.senla.carservice.csv;

import com.senla.carservice.csv.exception.CsvException;
import com.senla.carservice.csv.util.FileUtil;
import com.senla.carservice.csv.util.ParameterUtil;
import com.senla.carservice.domain.Place;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
@Slf4j
public class CsvPlace {

    @Value("${com.senla.carservice.csv.CsvPlace.placePath:place.csv}")
    private String placePath;
    @Value("${com.senla.carservice.csv.CsvPlace.fieldSeparator:|}")
    private String fieldSeparator;

    public void exportPlaces(List<Place> places) {
        log.debug("Method exportPlaces");
        log.trace("Parameter places: {}", places);
        if (places == null) {
            throw new CsvException("argument is null");
        }
        List<String> valueCsv = places.stream().map(this::convertToCsv).collect(Collectors.toList());
        FileUtil.saveCsv(valueCsv, placePath);
    }

    public List<Place> importPlaces() {
        log.debug("Method importPlaces");
        List<String> csvLinesPlace = FileUtil.getCsv(placePath);
        return csvLinesPlace.stream().map(this::getPlaceFromCsv).collect(Collectors.toList());
    }

    private Place getPlaceFromCsv(String line) {
        log.debug("Method getPlaceFromCsv");
        log.trace("Parameter line: {}", line);
        if (line == null) {
            throw new CsvException("argument is null");
        }
        List<String> values = Arrays.asList(line.split(fieldSeparator));
        Place place = new Place();
        place.setId(ParameterUtil.getValueLong(values.get(0)));
        place.setNumber(ParameterUtil.getValueInteger(values.get(1)));
        place.setIsBusy(ParameterUtil.getValueBoolean(values.get(2)));
        place.setDeleteStatus(ParameterUtil.getValueBoolean(values.get(3)));
        return place;
    }

    private String convertToCsv(Place place) {
        log.debug("Method convertToCsv");
        log.trace("Parameter place: {}", place);
        if (place == null) {
            throw new CsvException("argument is null");
        }
        return place.getId() + fieldSeparator + place.getNumber() + fieldSeparator + place.getIsBusy() +
               fieldSeparator + place.getDeleteStatus();
    }

}
