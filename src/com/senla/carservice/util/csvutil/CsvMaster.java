package com.senla.carservice.util.csvutil;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.util.PropertyLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CsvMaster {
    private static final String MASTER_PATH = PropertyLoader.getPropertyValue("csv.master.pathFile");
    private static final String FIELD_SEPARATOR = PropertyLoader.getPropertyValue("csv.separator.field");
    private static final String SEPARATOR_ID = PropertyLoader.getPropertyValue("csv.separator.id");

    private CsvMaster() {
    }

    public static void exportMasters(List<Master> masters) {
        List<String> valueCsv;
        valueCsv = masters.stream().map(CsvMaster::convertToCsv).collect(Collectors.toList());
        FileUtil.saveCsv(valueCsv, MASTER_PATH);
    }

    public static List<Master> importMasters(List<Order> orders) {
        List<String> csvLinesMaster = FileUtil.getCsv(MASTER_PATH);
        return csvLinesMaster.stream()
            .map((String line) -> getMasterFromCsv(line, orders))
            .collect(Collectors.toList());
    }

    private static Master getMasterFromCsv(String line, List<Order> orders) {
        if (line == null) {
            throw new BusinessException("argument is null");
        }
        List<String> values = Arrays.asList((line.split(SEPARATOR_ID))[0].split(FIELD_SEPARATOR));
        List<String> arrayIdOrder = Arrays.asList(line.split(SEPARATOR_ID)[1].split(FIELD_SEPARATOR));
        Master master = new Master();
        master.setId(ParameterUtil.getValueLong(values.get(0)));
        master.setName(values.get(1));
        master.setOrders(getOrdersById(orders, arrayIdOrder));
        return master;
    }

    private static String convertToCsv(Master master) {
        if (master == null) {
            throw new BusinessException("argument is null");
        }
        StringBuilder stringValue = new StringBuilder();
        stringValue.append(master.getId());
        stringValue.append(FIELD_SEPARATOR);
        stringValue.append(master.getName());
        stringValue.append(FIELD_SEPARATOR);
        stringValue.append(SEPARATOR_ID);
        List<Order> orders = master.getOrders();
        IntStream.range(0, orders.size()).forEachOrdered(i -> {
            if (i == orders.size() - 1) {
                stringValue.append(orders.get(i).getId());
            } else {
                stringValue.append(orders.get(i).getId()).append(FIELD_SEPARATOR);
            }
        });
        stringValue.append(SEPARATOR_ID);
        return stringValue.toString();
    }

    public static List<Order> getOrdersById(List<Order> orders, List<String> arrayIdOrder) {
        if (orders == null || arrayIdOrder == null) {
            throw new BusinessException("argument is null");
        }
        List<Order> masterOrders = new ArrayList<>();
        orders.forEach(order -> arrayIdOrder.stream()
            .filter(stringIndex -> order.getId().equals(ParameterUtil.getValueLong(stringIndex)))
            .map(stringIndex -> order).forEach(masterOrders::add));
        return masterOrders;
    }
}