package com.senla.carservice.util.csvutil;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.util.DateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class CsvOrder {
    @ConfigProperty
    private String orderPath;
    @ConfigProperty
    private String fieldSeparator;

    public CsvOrder() {
    }

    public void exportOrder(List<Order> orders) {
        List<String> valueOrderCsv = orders.stream()
            .map(this::convertOrderToCsv)
            .collect(Collectors.toList());
        FileUtil.saveCsv(valueOrderCsv, orderPath);
    }

    public List<Order> importOrder(List<Place> places) {
        List<String> csvLinesOrder = FileUtil.getCsv(orderPath);
        List<Order> list = new ArrayList<>();
        for (String line : csvLinesOrder) {
            Order order = getOrderFromCsv(line, places);
            list.add(order);
        }
        return list;
    }

    private Order getOrderFromCsv(String line, List<Place> places) {
        if (line == null || places == null) {
            throw new BusinessException("argument is null");
        }
        List<String> values = Arrays.asList((line.split(fieldSeparator)));
        Order order = new Order(ParameterUtil.checkValueString(values.get(5)),
                                ParameterUtil.checkValueString(values.get(6)),
                                ParameterUtil.checkValueString(values.get(7)));
        order.setId(Long.valueOf(values.get(0)));
        order.setCreationTime(DateUtil.getDatesFromString(values.get(1), true));
        order.setExecutionStartTime(DateUtil.getDatesFromString(values.get(2), true));
        order.setLeadTime(DateUtil.getDatesFromString(values.get(3), true));
        order.setPlace(getPlaceById(places, Long.valueOf(values.get(4))));
        order.setPrice(new BigDecimal(values.get(8)));
        order.setStatus(ParameterUtil.getValueStatus(values.get(9)));
        order.setDeleteStatus(ParameterUtil.getValueBoolean(values.get(10)));
        return order;
    }

    private Place getPlaceById(List<Place> places, Long id) {
        if (places == null || id == null) {
            throw new BusinessException("argument is null");
        }
        return places.stream()
            .filter(place -> place.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    private String convertOrderToCsv(Order order) {
        if (order == null) {
            throw new BusinessException("argument is null");
        }
        return order.getId() +
               fieldSeparator +
               DateUtil.getStringFromDate(order.getCreationTime(), true) +
               fieldSeparator +
               DateUtil.getStringFromDate(order.getExecutionStartTime(), true) +
               fieldSeparator +
               DateUtil.getStringFromDate(order.getLeadTime(), true) +
               fieldSeparator +
               order.getPlace().getId() +
               fieldSeparator +
               order.getAutomaker() +
               fieldSeparator +
               order.getModel() +
               fieldSeparator +
               order.getRegistrationNumber() +
               fieldSeparator +
               order.getPrice() +
               fieldSeparator +
               order.getStatus() +
               fieldSeparator +
               order.isDeleteStatus();
    }
}