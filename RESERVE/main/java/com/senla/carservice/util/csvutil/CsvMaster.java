package com.senla.carservice.util.csvutil;

import com.senla.carservice.annotation.Singleton;
import com.senla.carservice.objectadjuster.propertyinjection.annotation.ConfigProperty;
import com.senla.carservice.domain.Master;
import com.senla.carservice.exception.BusinessException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class CsvMaster {
    @ConfigProperty
    private String masterPath;
    @ConfigProperty
    private String fieldSeparator;

    public CsvMaster() {
    }

    public void exportMasters(List<Master> masters) {
        if (masters == null) {
            throw new BusinessException("argument is null");
        }
        FileUtil.saveCsv(masters.stream()
                             .map(this::convertToCsv)
                             .collect(Collectors.toList()),
                         masterPath);
    }

    public List<Master> importMasters() {
        List<String> csvLinesMaster = FileUtil.getCsv(masterPath);
        return csvLinesMaster.stream()
            .map(this::getMasterFromCsv)
            .collect(Collectors.toList());
    }

    private Master getMasterFromCsv(String line) {
        if (line == null) {
            throw new BusinessException("argument is null");
        }
        List<String> values = Arrays.asList((line.split(fieldSeparator)));
        Master master = new Master();
        master.setId(ParameterUtil.getValueLong(values.get(0)));
        master.setName(values.get(1));
        master.setNumberOrders(Integer.valueOf(values.get(2)));
        master.setDelete(Boolean.valueOf(values.get(3)));
        return master;
    }

    private String convertToCsv(Master master) {
        if (master == null) {
            throw new BusinessException("argument is null");
        }
        return master.getId() +
               fieldSeparator +
               master.getName() +
               fieldSeparator +
               master.getNumberOrders() +
               fieldSeparator +
               master.getDelete();
    }
}