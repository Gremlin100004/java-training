package com.senla.carservice.csvutil;

import com.senla.carservice.domain.Master;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.util.PropertyLoader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvMaster {
    private static final String MASTER_PATH = PropertyLoader.getPropertyValue("csvPathMaster");
    private static final String FIELD_SEPARATOR = PropertyLoader.getPropertyValue("fieldSeparator");

    private CsvMaster() {
    }

    public static void exportMasters(List<Master> masters) {
        List<String> valueCsv;
        valueCsv = masters.stream().map(CsvMaster::convertToCsv).collect(Collectors.toList());
        FileUtil.saveCsv(valueCsv, MASTER_PATH);
    }

    public static List<Master> importMasters() {
        List<String> csvLinesMaster = FileUtil.getCsv(MASTER_PATH);
        return csvLinesMaster.stream().map(CsvMaster::getMasterFromCsv).collect(Collectors.toList());
    }

    private static Master getMasterFromCsv(String line) {
        if (line == null) {
            throw new BusinessException("argument is null");
        }
        List<String> values = Arrays.asList(line.split(FIELD_SEPARATOR));
        Master master = new Master();
        master.setId(ParameterUtil.getValueLong(values.get(0)));
        master.setName(values.get(1));
        master.setNumberOrder(ParameterUtil.getValueInteger(values.get(2)));
        return master;
    }

    private static String convertToCsv(Master master) {
        return master.getId() + FIELD_SEPARATOR + master.getName() + FIELD_SEPARATOR + master.getNumberOrder();
    }
}