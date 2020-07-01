package com.senla.carservice.csvutil;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.domain.Status;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.repository.OrderRepositoryImpl;
import com.senla.carservice.repository.PlaceRepositoryImpl;
import com.senla.carservice.util.DateUtil;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CsvOrder {
    private static final String ORDER_PATH = "resources/csv/order.csv";
    private static final String COMMA = ",";
    private static final String QUOTATION_MARK = "\"";

    private CsvOrder () {
    }

    public static void exportOrder (List<Order> orders) {
        List<String> valueOrderCsv = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            if (i == orders.size() - 1) {
                valueOrderCsv.add(convertOrderToCsv(orders.get(i), false));
            } else {
                valueOrderCsv.add(convertOrderToCsv(orders.get(i), true));
            }
        }
        FileUtil.saveCsv(valueOrderCsv, ORDER_PATH);
    }

    public static List<Order> importOrder (List<Master> masters) {
        List<String> csvLinesOrder = FileUtil.getCsv(ORDER_PATH);
        List<Order> orders = new ArrayList<>();
        csvLinesOrder.stream().map(line -> getOrderFromCsv(line, masters)).forEachOrdered(order -> {
            if (order.getMasters().isEmpty()) {
                throw new BusinessException("masters not imported");
            }
            orders.add(order);
        });
        return orders;
    }

    private static Order getOrderFromCsv (@NotNull String line, @NotNull List<Master> masters) {
        List<String> values = Arrays.asList((line.split(QUOTATION_MARK))[0].split(COMMA));
        List<String> arrayIdMaster = Arrays.asList(line.split(QUOTATION_MARK)[1]
                .split(COMMA));
        Order order = new Order(Long.valueOf(values.get(0)), values.get(5), values.get(6), values.get(7));
        order.setCreationTime(DateUtil.getDatesFromString(values.get(1), true));
        order.setExecutionStartTime(DateUtil.getDatesFromString(values.get(2), true));
        order.setLeadTime(DateUtil.getDatesFromString(values.get(3), true));
        order.setPlace(getPlaceById(PlaceRepositoryImpl.getInstance().getPlaces(), Long.valueOf(values.get(4))));
        order.setPrice(new BigDecimal(values.get(8)));
        order.setStatus(Status.valueOf(values.get(9)));
        order.setDeleteStatus(Boolean.parseBoolean(values.get(10)));
        order.setMasters(getMastersById(masters, arrayIdMaster));
        return order;
    }

    private static List<Master> getMastersById (List<Master> masters, @NotNull List<String> arrayIdMaster) {
        List<Master> orderMasters = new ArrayList<>();
        for (String stringIndex : arrayIdMaster) {
            masters.forEach(master -> {
                if (master.getId().equals(Long.valueOf(stringIndex))) {
                    orderMasters.add(master);
                }
            });
        }
        return orderMasters;
    }

    private static Place getPlaceById (List<Place> places, Long id) {
        for (Place place : places) {
            if (place.getId().equals(id)) {
                return place;
            }
        }
        return null;
    }

    private static String convertOrderToCsv (@NotNull Order order, boolean isLineFeed) {
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
        if (isLineFeed) {
            stringValue.append("\n");
        }
        return stringValue.toString();
    }
}