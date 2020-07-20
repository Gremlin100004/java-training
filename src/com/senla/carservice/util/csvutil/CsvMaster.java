package com.senla.carservice.util.csvutil;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.exception.BusinessException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Singleton
public class CsvMaster {

    @ConfigProperty
    private String masterPath;
    @ConfigProperty
    private String fieldSeparator;
    @ConfigProperty
    private String idSeparator;
    private static final int SIZE_INDEX = 1;

    public CsvMaster() {
    }

    public void exportMasters(List<Master> masters) {
        FileUtil.saveCsv(masters.stream()
                             .map(this::convertToCsv)
                             .collect(Collectors.toList()),
                         masterPath);
    }

    public List<Master> importMasters(List<Order> orders) {
        List<String> csvLinesMaster = FileUtil.getCsv(masterPath);
        return csvLinesMaster.stream()
            .map((String line) -> getMasterFromCsv(line, orders))
            .collect(Collectors.toList());
    }

    private Master getMasterFromCsv(String line, List<Order> orders) {
        if (line == null) {
            throw new BusinessException("argument is null");
        }
        List<String> values = Arrays.asList((line.split(idSeparator))[0].split(fieldSeparator));
        List<String> arrayIdOrder = Arrays.asList(line.split(idSeparator)[1].split(fieldSeparator));
        Master master = new Master();
        master.setId(ParameterUtil.getValueLong(values.get(0)));
        master.setName(values.get(1));
        master.setOrders(getOrdersById(orders, arrayIdOrder));
        return master;
    }

    private String convertToCsv(Master master) {
        if (master == null) {
            throw new BusinessException("argument is null");
        }
        StringBuilder stringValue = new StringBuilder();
        stringValue.append(master.getId());
        stringValue.append(fieldSeparator);
        stringValue.append(master.getName());
        stringValue.append(fieldSeparator);
        stringValue.append(idSeparator);
        List<Order> orders = master.getOrders();
        IntStream.range(0, orders.size())
            .forEachOrdered(i -> {
                if (i == orders.size() - SIZE_INDEX) {
                    stringValue.append(orders.get(i).getId());
                } else {
                    stringValue.append(orders.get(i).getId()).append(fieldSeparator);
                }
            });
        stringValue.append(idSeparator);
        return stringValue.toString();
    }

    public List<Order> getOrdersById(List<Order> orders, List<String> arrayIdOrder) {
        if (orders == null || arrayIdOrder == null) {
            throw new BusinessException("argument is null");
        }
        List<Order> masterOrders = new ArrayList<>();
        for (Order order : orders) {
            for (String stringIndex : arrayIdOrder) {
                if (order.getId().equals(ParameterUtil.getValueLong(stringIndex))) {
                    masterOrders.add(order);
                }
            }
        }
        return masterOrders;
    }
}