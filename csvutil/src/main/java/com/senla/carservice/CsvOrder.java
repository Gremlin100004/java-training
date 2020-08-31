package com.senla.carservice;

import com.senla.carservice.annotation.Singleton;
import com.senla.carservice.exception.CsvException;
import com.senla.carservice.objectadjuster.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.util.FileUtil;
import com.senla.carservice.util.ParameterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class CsvOrder {

    @ConfigProperty(configName = "csv.properties")
    private String orderPath;
    @ConfigProperty(configName = "csv.properties")
    private String fieldSeparator;
    @ConfigProperty(configName = "csv.properties")
    private String idSeparator;
    private static final int SIZE_INDEX = 1;
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvOrder.class);

    public CsvOrder() {
    }

    public void exportOrder(List<Order> orders) {
        LOGGER.debug("Method exportOrder");
        LOGGER.debug("Parameter orders: {}", orders);
        List<String> valueOrderCsv = orders.stream()
            .map(this::convertOrderToCsv)
            .collect(Collectors.toList());
        FileUtil.saveCsv(valueOrderCsv, orderPath);
    }

    public List<Order> importOrder(List<Master> masters, List<Place> places) {
        LOGGER.debug("Method importOrder");
        LOGGER.debug("Parameter masters: {}", masters);
        LOGGER.debug("Parameter places: {}", places);
        List<String> csvLinesOrder = FileUtil.getCsv(orderPath);
        return csvLinesOrder.stream()
            .map(line -> getOrderFromCsv(line, masters, places))
            .peek(
                order -> {
                    if (order.getMasters().isEmpty()) {
                        throw new CsvException("masters not imported");
                    }
                })
            .collect(Collectors.toList());
    }

    private Order getOrderFromCsv(String line, List<Master> masters, List<Place> places) {
        LOGGER.debug("Method getOrderFromCsv");
        LOGGER.debug("Parameter line: {}", line);
        LOGGER.debug("Parameter masters: {}", masters);
        LOGGER.debug("Parameter places: {}", places);
        if (line == null || masters == null || places == null) {
            throw new CsvException("argument is null");
        }
        List<String> values = Arrays.asList((line.split(idSeparator))[0].split(fieldSeparator));
        List<String> arrayIdMaster = Arrays.asList(line.split(idSeparator)[1].split(fieldSeparator));
        Order order = new Order(ParameterUtil.checkValueString(values.get(5)),
                                ParameterUtil.checkValueString(values.get(6)),
                                ParameterUtil.checkValueString(values.get(7)));
        order.setId(ParameterUtil.getValueLong(values.get(0)));
        order.setCreationTime(DateUtil.getDatesFromString(values.get(1), true));
        order.setExecutionStartTime(DateUtil.getDatesFromString(values.get(2), true));
        order.setLeadTime(DateUtil.getDatesFromString(values.get(3), true));
        order.setPlace(getPlaceById(places, Long.valueOf(values.get(4))));
        order.setPrice(new BigDecimal(values.get(8)));
        order.setStatus(ParameterUtil.getValueStatus(values.get(9)));
        order.setDeleteStatus(ParameterUtil.getValueBoolean(values.get(10)));
        order.setMasters(getMastersById(masters, arrayIdMaster));
        return order;
    }

    private List<Master> getMastersById(List<Master> masters, List<String> arrayIdMaster) {
        LOGGER.debug("Method getMastersById");
        LOGGER.debug("Parameter masters: {}", masters);
        LOGGER.debug("Parameter arrayIdMaster: {}", arrayIdMaster);
        if (masters == null || arrayIdMaster == null) {
            throw new CsvException("argument is null");
        }
        List<Master> orderMasters = new ArrayList<>();
        masters
            .forEach(master -> arrayIdMaster.stream()
                .filter(stringIndex -> master.getId().equals(ParameterUtil.getValueLong(stringIndex)))
                .map(stringIndex -> master)
                .forEach(orderMasters::add));
        return orderMasters;
    }

    private Place getPlaceById(List<Place> places, Long id) {
        LOGGER.debug("Method getPlaceById");
        LOGGER.debug("Parameter places: {}", places);
        LOGGER.debug("Parameter id: {}", id);
        if (places == null || id == null) {
            throw new CsvException("argument is null");
        }
        return places.stream()
            .filter(place -> place.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    private String convertOrderToCsv(Order order) {
        LOGGER.debug("Method convertOrderToCsv");
        LOGGER.debug("Parameter order: {}", order);
        if (order == null) {
            throw new CsvException("argument is null");
        }
        StringBuilder stringValue = new StringBuilder();
        stringValue.append(order.getId());
        stringValue.append(fieldSeparator);
        stringValue.append(DateUtil.getStringFromDate(order.getCreationTime(), true));
        stringValue.append(fieldSeparator);
        stringValue.append(DateUtil.getStringFromDate(order.getExecutionStartTime(), true));
        stringValue.append(fieldSeparator);
        stringValue.append(DateUtil.getStringFromDate(order.getLeadTime(), true));
        stringValue.append(fieldSeparator);
        stringValue.append(order.getPlace().getId());
        stringValue.append(fieldSeparator);
        stringValue.append(order.getAutomaker());
        stringValue.append(fieldSeparator);
        stringValue.append(order.getModel());
        stringValue.append(fieldSeparator);
        stringValue.append(order.getRegistrationNumber());
        stringValue.append(fieldSeparator);
        stringValue.append(order.getPrice());
        stringValue.append(fieldSeparator);
        stringValue.append(order.getStatus());
        stringValue.append(fieldSeparator);
        stringValue.append(order.isDeleteStatus());
        stringValue.append(fieldSeparator);
        stringValue.append(idSeparator);
        int bound = order.getMasters().size();
        for (int i = 0; i < bound; i++) {
            if (i == order.getMasters().size() - SIZE_INDEX) {
                stringValue.append(order.getMasters().get(i).getId());
            } else {
                stringValue.append(order.getMasters().get(i).getId()).append(fieldSeparator);
            }
        }
        stringValue.append(idSeparator);
        return stringValue.toString();
    }
}