package com.senla.carservice.util.csvutil;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.dependencyinjection.annotation.Dependency;
import com.senla.carservice.container.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.exception.BusinessException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class CsvPlace {
    @ConfigProperty
    private String placePath;
    @ConfigProperty
    private String fieldSeparator;
    @ConfigProperty
    private String idSeparator;
    @Dependency
    private CsvMaster csvMaster;
    private static final int SIZE_INDEX = 1;

    public CsvPlace() {
    }

    public void exportPlaces(List<Place> places) {
        if (places == null) {
            throw new BusinessException("argument is null");
        }
        List<String> valueCsv = places.stream()
            .map(this::convertToCsv)
            .collect(Collectors.toList());
        FileUtil.saveCsv(valueCsv, placePath);
    }

    public List<Place> importPlaces(List<Order> orders) {
        List<String> csvLinesPlace = FileUtil.getCsv(placePath);
        return csvLinesPlace.stream()
            .map(line -> getPlaceFromCsv(line, orders))
            .collect(Collectors.toList());
    }

    private Place getPlaceFromCsv(String line, List<Order> orders) {
        if (line == null) {
            throw new BusinessException("argument is null");
        }
        String[] lineValue = (line.split(idSeparator));
        List<String> values = Arrays.asList(lineValue[0].split(fieldSeparator));
        List<String> arrayIdOrder = new ArrayList<>();
        if (lineValue.length > 1) {
            arrayIdOrder = Arrays.asList(line.split(idSeparator)[1].split(fieldSeparator));
        }
        Place place = new Place();
        place.setId(ParameterUtil.getValueLong(values.get(0)));
        place.setNumber(ParameterUtil.getValueInteger(values.get(1)));
        place.setBusyStatus(ParameterUtil.getValueBoolean(values.get(2)));
        if (!arrayIdOrder.isEmpty()) {
            place.setOrders(csvMaster.getOrdersById(orders, arrayIdOrder));
        }
        return place;
    }

    private String convertToCsv(Place place) {
        if (place == null) {
            throw new BusinessException("argument is null");
        }
        StringBuilder stringValue = new StringBuilder();
        stringValue.append(place.getId());
        stringValue.append(fieldSeparator);
        stringValue.append(place.getNumber());
        stringValue.append(fieldSeparator);
        stringValue.append(place.getBusyStatus());
        stringValue.append(fieldSeparator);
        stringValue.append(idSeparator);
        List<Order> orders = place.getOrders();
        int bound = orders.size();
        for (int i = 0; i < bound; i++) {
            if (i == orders.size() - SIZE_INDEX) {
                stringValue.append(orders.get(i).getId());
            } else {
                stringValue.append(orders.get(i).getId()).append(fieldSeparator);
            }
        }
        stringValue.append(idSeparator);
        return stringValue.toString();
    }
}