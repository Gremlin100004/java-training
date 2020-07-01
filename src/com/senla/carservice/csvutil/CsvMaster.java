package com.senla.carservice.csvutil;

import com.senla.carservice.domain.Master;
import com.senla.carservice.exception.BusinessException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CsvMaster {
    private static final String MASTER_PATH = "resources/csv/masters.csv";
    private static final String COMMA = ",";

    private CsvMaster () {
    }

    public static void exportMasters (List<Master> masters) {
        List<String> valueCsv = new ArrayList<>();
        IntStream.range(0, masters.size()).forEachOrdered(i -> {
            if (i == masters.size() - 1) {
                valueCsv.add(convertToCsv(masters.get(i), false));
            } else {
                valueCsv.add(convertToCsv(masters.get(i), true));
            }
        });
        FileUtil.saveCsv(valueCsv, MASTER_PATH);
    }

    public static List<Master> importMasters () {
        List<String> csvLinesMaster = FileUtil.getCsv(MASTER_PATH);
        return csvLinesMaster.stream().map(CsvMaster::getMasterFromCsv).collect(Collectors.toList());
    }


    private static Master getMasterFromCsv (@NotNull String line) {
        List<String> values = Arrays.asList(line.split(COMMA));
        Master master = new Master();
        master.setId(Long.parseLong(values.get(0)));
        master.setName(values.get(1));
        if (values.get(2).equals("null")) {
            master.setNumberOrder(null);
        } else {
            master.setNumberOrder(Integer.valueOf(values.get(2)));
        }
        return master;
    }

    private static String convertToCsv (@NotNull Master master, boolean isLineFeed) {
        if (isLineFeed) {
            return master.getId() + COMMA + master.getName() + COMMA + master.getNumberOrder() + "\n";
        }
        return master.getId() + COMMA + master.getName() + COMMA + master.getNumberOrder();
    }

    private static void checkValue (List<String> values){
        try {
            Long.parseLong(values.get(0));
        } catch (NumberFormatException e){
            throw new BusinessException("wrong structure csv file");
        }

    }
}


