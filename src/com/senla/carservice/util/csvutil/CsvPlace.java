package com.senla.carservice.util.csvutil;

import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.enumeration.DefaultValue;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.util.PropertyLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CsvPlace {
    private static final String PLACE_PATH = PropertyLoader.getPropertyValue(DefaultValue.PROPERTY_FILE_NAME.toString(),
                                                                             DefaultValue.PROPERTY_PLACE_CSV_FILE_PATH
                                                                                 .toString());
    private static final String FIELD_SEPARATOR = PropertyLoader
        .getPropertyValue(DefaultValue.PROPERTY_FILE_NAME.toString(),
                          DefaultValue.PROPERTY_MASTER_FIELD_SEPARATOR.toString());
    private static final String ID_SEPARATOR = PropertyLoader
        .getPropertyValue(DefaultValue.PROPERTY_FILE_NAME.toString(),
                          DefaultValue.PROPERTY_MASTER_ID_SEPARATOR.toString());

    private CsvPlace() {
    }

    public static void exportPlaces(List<Place> places) {
        if (places == null) {
            throw new BusinessException("argument is null");
        }
        List<String> valueCsv = places.stream().map(CsvPlace::convertToCsv).collect(Collectors.toList());
        FileUtil.saveCsv(valueCsv, PLACE_PATH);
    }

    public static List<Place> importPlaces(List<Order> orders) {
        List<String> csvLinesPlace = FileUtil.getCsv(PLACE_PATH);
        return csvLinesPlace.stream().map(line -> getPlaceFromCsv(line, orders)).collect(Collectors.toList());
    }

    private static Place getPlaceFromCsv(String line, List<Order> orders) {
        if (line == null) {
            throw new BusinessException("argument is null");
        }
        String[] lineValue = (line.split(ID_SEPARATOR));
        List<String> values = Arrays.asList(lineValue[0].split(FIELD_SEPARATOR));
        List<String> arrayIdOrder = new ArrayList<>();
        if (lineValue.length > 1) {
            arrayIdOrder = Arrays.asList(line.split(ID_SEPARATOR)[1].split(FIELD_SEPARATOR));
        }
        Place place = new Place();
        place.setId(ParameterUtil.getValueLong(values.get(0)));
        place.setNumber(ParameterUtil.getValueInteger(values.get(1)));
        place.setBusyStatus(ParameterUtil.getValueBoolean(values.get(2)));
        if (!arrayIdOrder.isEmpty()) {
            place.setOrders(CsvMaster.getOrdersById(orders, arrayIdOrder));
        }
        return place;
    }

    private static String convertToCsv(Place place) {
        if (place == null) {
            throw new BusinessException("argument is null");
        }
        StringBuilder stringValue = new StringBuilder();
        stringValue.append(place.getId());
        stringValue.append(FIELD_SEPARATOR);
        stringValue.append(place.getNumber());
        stringValue.append(FIELD_SEPARATOR);
        stringValue.append(place.getBusyStatus());
        stringValue.append(FIELD_SEPARATOR);
        stringValue.append(ID_SEPARATOR);
        List<Order> orders = place.getOrders();
        IntStream.range(0, orders.size())
            .forEachOrdered(i -> {
                if (i == orders.size() - 1) {
                    stringValue.append(orders.get(i).getId());
                } else {
                    stringValue.append(orders.get(i).getId()).append(FIELD_SEPARATOR);
                }
            });
        stringValue.append(ID_SEPARATOR);
        return stringValue.toString();
    }
}