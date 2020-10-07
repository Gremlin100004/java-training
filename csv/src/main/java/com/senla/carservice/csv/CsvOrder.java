package com.senla.carservice.csv;

import com.senla.carservice.csv.exception.CsvException;
import com.senla.carservice.csv.util.FileUtil;
import com.senla.carservice.csv.util.ParameterUtil;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.util.DateUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
@Slf4j
public class CsvOrder {

    private static final int SIZE_INDEX = 1;
    @Value("${com.senla.carservice.csv.CsvOrder.orderPath:order.csv}")
    private String orderPath;
    @Value("${com.senla.carservice.csv.CsvOrder.fieldSeparator:|}")
    private String fieldSeparator;
    @Value("${com.senla.carservice.csv.CsvOrder.idSeparator:,}")
    private String idSeparator;

    public void exportOrder(List<Order> orders) {
        log.debug("Method exportOrder");
        log.trace("Parameter orders: {}", orders);
        List<String> valueOrderCsv = orders.stream()
            .map(this::convertOrderToCsv)
            .collect(Collectors.toList());
        FileUtil.saveCsv(valueOrderCsv, orderPath);
    }

    public List<Order> importOrder(List<Master> masters, List<Place> places) {
        log.debug("Method importOrder");
        log.trace("Parameter masters: {}, places: {}", masters, places);
        List<String> csvLinesOrder = FileUtil.getCsv(orderPath);
        return csvLinesOrder.stream()
            .map(line -> getOrderFromCsv(line, masters, places))
            .peek(
                order -> {
                    if (order.getMasters().isEmpty()) {
                        throw new CsvException("orders not imported");
                    }
                })
            .collect(Collectors.toList());
    }

    private Order getOrderFromCsv(String line, List<Master> masters, List<Place> places) {
        log.debug("Method getOrderFromCsv");
        log.trace("Parameter line: {}, masters: {}, places: {}", line, masters, places);
        if (line == null || masters == null || places == null) {
            throw new CsvException("argument is null");
        }
        List<String> values = Arrays.asList((line.split(idSeparator))[0].split(fieldSeparator));
        List<String> arrayIdMaster = Arrays.asList(line.split(idSeparator)[1].split(fieldSeparator));
        Order order = new Order();
        order.setAutomaker(ParameterUtil.checkValueString(values.get(5)));
        order.setModel(ParameterUtil.checkValueString(values.get(6)));
        order.setRegistrationNumber(ParameterUtil.checkValueString(values.get(7)));
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
        log.debug("Method getMastersById");
        log.trace("Parameter masters: {}, arrayIdMaster: {}", masters, arrayIdMaster);
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
        log.debug("Method getPlaceById");
        log.trace("Parameter places: {}, id: {}", places, id);
        if (places == null || id == null) {
            throw new CsvException("argument is null");
        }
        return places.stream()
            .filter(place -> place.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    private String convertOrderToCsv(Order order) {
        log.debug("Method convertOrderToCsv");
        log.trace("Parameter order: {}", order);
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
