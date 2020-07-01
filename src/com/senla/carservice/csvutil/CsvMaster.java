package com.senla.carservice.csvutil;

import com.senla.carservice.domain.Master;

import com.senla.carservice.repository.MasterRepositoryImpl;

import java.util.Arrays;
import java.util.List;

public class CsvMaster {
    private static final String MASTER_PATH = "csv/masters.csv";
    private static final String COMMA = ",";

    private CsvMaster() {
    }

    public static String exportMasters(List<Master> masters) {
        String valueCsv;
        for (int i = 0; i < masters.size(); i++) {
            if (i == masters.size() - 1) {
                valueCsv = convertToCsv(masters.get(i), false);
            } else {
                valueCsv = convertToCsv(masters.get(i), true);
            }
            FileUtil.saveCsv(valueCsv, MASTER_PATH, i != 0);
        }
        return "save successfully";
    }

    public static String importMasters() {
        List<String> csvLinesMaster = FileUtil.getCsv(MASTER_PATH);
        csvLinesMaster.forEach(line -> {
            Master master = getMasterFromCsv(line);
            MasterRepositoryImpl.getInstance().updateMaster(master);
        });
        return "Masters have been import successfully!";
    }

    private static Master getMasterFromCsv(String line) {
        List<String> values = Arrays.asList(line.split(COMMA));
        Master master = new Master();
        master.setId(Long.valueOf(values.get(0)));
        master.setName(values.get(1));
        if (values.get(2).equals("null")) {
            master.setNumberOrder(null);
        } else {
            master.setNumberOrder(Integer.valueOf(values.get(2)));
        }
        return master;
    }

    private static String convertToCsv(Master master, boolean isLineFeed) {
        if (isLineFeed) {
            return String.format("%s,%s,%s\n", master.getId(), master.getName(), master.getNumberOrder());
        } else {
            return String.format("%s,%s,%s", master.getId(), master.getName(), master.getNumberOrder());
        }
    }
}