package com.senla.carservice.csv;

import com.senla.carservice.csv.exception.CsvException;
import com.senla.carservice.csv.util.FileUtil;
import com.senla.carservice.csv.util.ParameterUtil;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@NoArgsConstructor
@Slf4j
public class CsvMaster {

    private static final int SIZE_INDEX = 1;
    @Value("${com.senla.carservice.csv.CsvMaster.masterPath:masters.csv}")
    private String masterPath;
    @Value("${com.senla.carservice.csv.CsvMaster.fieldSeparator:|}")
    private String fieldSeparator;
    @Value("${com.senla.carservice.csv.CsvMaster.idSeparator:,}")
    private String idSeparator;

    public void exportMasters(List<Master> masters) {
        log.debug("Method exportMasters");
        log.trace("Parameter masters: {}", masters);
        FileUtil.saveCsv(masters.stream().map(this::convertToCsv).collect(Collectors.toList()), masterPath);
    }

    public List<Master> importMasters(List<Order> orders) {
        log.debug("Method importMasters");
        List<String> csvLinesMaster = FileUtil.getCsv(masterPath);
        return csvLinesMaster.stream().map((String line) -> getMasterFromCsv(line, orders))
            .collect(Collectors.toList());
    }

    private Master getMasterFromCsv(String line, List<Order> orders) {
        log.debug("Method getMasterFromCsv");
        log.trace("Parameter line: {}, orders: {}", line, orders);
        if (line == null) {
            throw new CsvException("argument is null");
        }
        List<String> values = Arrays.asList((line.split(idSeparator))[0].split(fieldSeparator));
        List<String> arrayIdOrder = Arrays.asList(line.split(idSeparator)[1].split(fieldSeparator));
        Master master = new Master();
        master.setId(ParameterUtil.getValueLong(values.get(0)));
        master.setName(values.get(1));
        master.setNumberOrders(ParameterUtil.getValueInteger(values.get(2)));
        master.setOrders(getOrdersById(orders, arrayIdOrder));
        return master;
    }

    private String convertToCsv(Master master) {
        log.debug("Method convertToCsv");
        log.trace("Parameter master: {}", master);
        if (master == null) {
            throw new CsvException("argument is null");
        }
        StringBuilder stringValue = new StringBuilder();
        stringValue.append(master.getId());
        stringValue.append(fieldSeparator);
        stringValue.append(master.getName());
        stringValue.append(fieldSeparator);
        stringValue.append(master.getNumberOrders());
        stringValue.append(fieldSeparator);
        stringValue.append(idSeparator);
        List<Order> orders = master.getOrders();
        if (orders.isEmpty()) {
            stringValue.append(fieldSeparator);
        } else {
            IntStream.range(0, orders.size()).forEachOrdered(i -> {
                if (i == orders.size() - SIZE_INDEX) {
                    stringValue.append(orders.get(i).getId());
                } else {
                    stringValue.append(orders.get(i).getId()).append(fieldSeparator);
                }
            });
        }
        stringValue.append(idSeparator);
        return stringValue.toString();
    }

    public List<Order> getOrdersById(List<Order> orders, List<String> arrayIdOrder) {
        log.debug("Method getOrdersById");
        log.trace("Parameter orders: {}, arrayIdOrder: {}", orders, arrayIdOrder);
        if (orders == null || arrayIdOrder == null) {
            throw new CsvException("argument is null");
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