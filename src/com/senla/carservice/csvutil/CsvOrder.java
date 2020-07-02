package com.senla.carservice.csvutil;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.util.DateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CsvOrder {
    private static final String ORDER_PATH = "resources/csv/order.csv";
    private static final String COMMA = ",";
    private static final String QUOTATION_MARK = "\"";

    private CsvOrder() {
    }

    public static void exportOrder(List<Order> orders) {
        List<String> valueOrderCsv = orders.stream().map(CsvOrder::convertOrderToCsv).collect(Collectors.toList());
        FileUtil.saveCsv(valueOrderCsv, ORDER_PATH);
    }

    public static List<Order> importOrder(List<Master> masters, List<Place> places) {
        List<String> csvLinesOrder = FileUtil.getCsv(ORDER_PATH);
        List<Order> orders = new ArrayList<>();
        csvLinesOrder.stream().map(line -> getOrderFromCsv(line, masters, places)).forEachOrdered(order -> {
            if (order.getMasters().isEmpty()) {
                throw new BusinessException("masters not imported");
            }
            orders.add(order);
        });
        return orders;
    }

    private static Order getOrderFromCsv(String line, List<Master> masters, List<Place> places) {
        if (line == null || masters == null || places == null) {
            throw new BusinessException("argument is null");
        }
        List<String> values = Arrays.asList((line.split(QUOTATION_MARK))[0].split(COMMA));
        List<String> arrayIdMaster = Arrays.asList(line.split(QUOTATION_MARK)[1].split(COMMA));
        Order order = new Order(ParametrUtil.getValueLong(values.get(0)),
                                ParametrUtil.checkValueString(values.get(5)),
                                ParametrUtil.checkValueString(values.get(6)),
                                ParametrUtil.checkValueString(values.get(7)));
        order.setCreationTime(DateUtil.getDatesFromString(values.get(1), true));
        order.setExecutionStartTime(DateUtil.getDatesFromString(values.get(2), true));
        order.setLeadTime(DateUtil.getDatesFromString(values.get(3), true));
        order.setPlace(getPlaceById(places, Long.valueOf(values.get(4))));
        order.setPrice(new BigDecimal(values.get(8)));
        order.setStatus(ParametrUtil.getValueStatus(values.get(9)));
        order.setDeleteStatus(ParametrUtil.getValueBoolean(values.get(10)));
        order.setMasters(getMastersById(masters, arrayIdMaster));
        return order;
    }

    private static List<Master> getMastersById(List<Master> masters, List<String> arrayIdMaster) {
        if (masters == null || arrayIdMaster == null) {
            throw new BusinessException("argument is null");
        }
        List<Master> orderMasters = new ArrayList<>();
        arrayIdMaster.stream().<Consumer<? super Master>> map(stringIndex -> master -> {
            if (master.getId().equals(ParametrUtil.getValueLong(stringIndex))) {
                orderMasters.add(master);
            }
        }).forEach(masters::forEach);
        return orderMasters;
    }

    private static Place getPlaceById(List<Place> places, Long id) {
        if (places == null || id == null) {
            throw new BusinessException("argument is null");
        }
        return places.stream().filter(place -> place.getId().equals(id)).findFirst().orElse(null);
    }

    private static String convertOrderToCsv(Order order) {
        if (order == null) {
            throw new BusinessException("argument is null");
        }
        StringBuilder stringValue = new StringBuilder();
        stringValue.append(order.getId());
        stringValue.append(COMMA);
        stringValue.append(DateUtil.getStringFromDate(order.getCreationTime()));
        stringValue.append(COMMA);
        stringValue.append(DateUtil.getStringFromDate(order.getExecutionStartTime()));
        stringValue.append(COMMA);
        stringValue.append(DateUtil.getStringFromDate(order.getLeadTime()));
        stringValue.append(COMMA);
        stringValue.append(order.getPlace().getId());
        stringValue.append(COMMA);
        stringValue.append(order.getAutomaker());
        stringValue.append(COMMA);
        stringValue.append(order.getModel());
        stringValue.append(COMMA);
        stringValue.append(order.getRegistrationNumber());
        stringValue.append(COMMA);
        stringValue.append(order.getPrice());
        stringValue.append(COMMA);
        stringValue.append(order.getStatus());
        stringValue.append(COMMA);
        stringValue.append(order.isDeleteStatus());
        stringValue.append(COMMA);
        stringValue.append(QUOTATION_MARK);
        IntStream.range(0, order.getMasters().size()).forEachOrdered(i -> {
            if (i == order.getMasters().size() - 1) {
                stringValue.append(order.getMasters().get(i).getId());
            } else {
                stringValue.append(order.getMasters().get(i).getId()).append(COMMA);
            }
        });
        stringValue.append(QUOTATION_MARK);
        return stringValue.toString();
    }
}