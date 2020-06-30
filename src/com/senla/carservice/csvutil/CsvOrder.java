package com.senla.carservice.csvutil;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.domain.Status;
import com.senla.carservice.exception.NumberObjectZeroException;
import com.senla.carservice.repository.MasterRepositoryImpl;
import com.senla.carservice.repository.OrderRepositoryImpl;
import com.senla.carservice.repository.PlaceRepositoryImpl;
import com.senla.carservice.util.DateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvOrder {
    private static final String ORDER_PATH = "csv/order.csv";
    private static final String COMMA = ",";
    private static final String QUOTATION_MARK = "\"";

    private CsvOrder() {
    }

    public static String exportOrder(List<Order> orders) {
        String valueOrderCsv;
        for (int i = 0; i < orders.size(); i++) {
            if (i == orders.size() - 1) {
                valueOrderCsv = convertOrderToCsv(orders.get(i), false);
            } else {
                valueOrderCsv = convertOrderToCsv(orders.get(i), true);
            }
            FileUtil.saveCsv(valueOrderCsv, ORDER_PATH, i != 0);
        }
        return "save successfully";
    }

    public static String importOrder() {
        List<String> csvLinesOrder = FileUtil.getCsv(ORDER_PATH);
        for (String s : csvLinesOrder) {
            Order order = getOrderFromCsv(s);
            if (order.getMasters().isEmpty()) throw new NumberObjectZeroException("masters not imported");
            OrderRepositoryImpl.getInstance().updateOrder(order);
        }
        return "Orders have been import successfully!";
    }

    private static Order getOrderFromCsv(String line) {
        List<String> values = Arrays.asList((line.split(QUOTATION_MARK))[0].split(COMMA));
        List<String> arrayIdMaster = Arrays.asList(line.split
                (QUOTATION_MARK)[1].replaceAll(QUOTATION_MARK, "").split(COMMA));
        Order order = new Order(Long.valueOf(values.get(0)), values.get(5), values.get(6), values.get(7));
        order.setCreationTime(DateUtil.getDatesFromString(values.get(1), true));
        order.setExecutionStartTime(DateUtil.getDatesFromString(values.get(2), true));
        order.setLeadTime(DateUtil.getDatesFromString(values.get(3), true));
        order.setPlace(getPlaceById(PlaceRepositoryImpl.getInstance().getPlaces(), Long.valueOf(values.get(4))));
        order.setPrice(new BigDecimal(values.get(8)));
        order.setStatus(Status.valueOf(values.get(9)));
        order.setDeleteStatus(Boolean.parseBoolean(values.get(10)));
        order.setMasters(getMastersById(MasterRepositoryImpl.getInstance().getMasters(), arrayIdMaster));
        return order;
    }

    private static List<Master> getMastersById(List<Master> masters, List<String> arrayIdMaster) {
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

    private static Place getPlaceById(List<Place> places, Long id) {
        for (Place place : places) {
            if (place.getId().equals(id)) {
                return place;
            }
        }
        return null;
    }

    private static String convertOrderToCsv(Order order, boolean isLineFeed) {
        StringBuilder stringValue = new StringBuilder();
        stringValue.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,\"", order.getId(),
                DateUtil.getStringFromDate(order.getCreationTime()),
                DateUtil.getStringFromDate(order.getExecutionStartTime()),
                DateUtil.getStringFromDate(order.getLeadTime()),
                order.getPlace().getId(), order.getAutomaker(),
                order.getModel(), order.getRegistrationNumber(),
                order.getPrice(), order.getStatus(),
                order.isDeleteStatus()));
        for (int i = 0; i < order.getMasters().size(); i++) {
            if (i == order.getMasters().size() - 1) {
                stringValue.append(order.getMasters().get(i).getId());
            } else {
                stringValue.append(order.getMasters().get(i).getId()).append(COMMA);
            }
        }
        stringValue.append(QUOTATION_MARK);
        if (isLineFeed) {
            stringValue.append("\n");
        }
        return stringValue.toString();
    }
}