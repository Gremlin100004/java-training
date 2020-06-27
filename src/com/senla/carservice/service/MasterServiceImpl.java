package com.senla.carservice.service;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.repository.MasterRepository;
import com.senla.carservice.repository.MasterRepositoryImpl;
import com.senla.carservice.util.FileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MasterServiceImpl implements MasterService {
    private static MasterService instance;
    private final MasterRepository masterRepository;
    private final String MASTER_PATH = "csv//masters.csv";

    private MasterServiceImpl() {
        this.masterRepository = MasterRepositoryImpl.getInstance();
    }

    public static MasterService getInstance() {
        if (instance == null) {
            instance = new MasterServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Master> getMasters() {
        return this.masterRepository.getMasters();
    }

    @Override
    public void addMaster(String name) {
        masterRepository.addMaster(new Master(name));
    }

    @Override
    public List<Master> getFreeMastersByDate(Date executeDate, Date leadDate, List<Order> sortOrders) {
        List<Master> freeMasters = new ArrayList<>(masterRepository.getMasters());
        if (executeDate == null || leadDate == null) {
            return freeMasters;
        }
        for (Order order : sortOrders) {
            order.getMasters().forEach(freeMasters::remove);
        }
        return freeMasters;
    }

    @Override
    public void deleteMaster(Master master) {
        masterRepository.deleteMaster(master);
    }

    @Override
    public List<Master> sortMasterByAlphabet(List<Master> masters) {
        return masters.stream().sorted(Comparator.comparing(Master::getName,
                Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());
    }

    @Override
    public List<Master> sortMasterByBusy(List<Master> masters) {
        return masters.stream().sorted(Comparator.comparing(Master::getNumberOrder,
                Comparator.nullsFirst(Comparator.naturalOrder()))).collect(Collectors.toList());
    }

    @Override
    public String exportMasters() {
        List<Master> masters = masterRepository.getMasters();
        StringBuilder valueCsv = new StringBuilder();
        for (int i = 0; i < masters.size(); i++) {
            if (i == masters.size() - 1) {
                valueCsv.append(convertToCsv(masters.get(i), false));
            } else {
                valueCsv.append(convertToCsv(masters.get(i), true));
            }
        }
        return FileUtil.SaveCsv(valueCsv, MASTER_PATH);
    }

    @Override
    public String importMasters() {
        List<String> csvLinesMaster = FileUtil.GetCsv(MASTER_PATH);
        if (csvLinesMaster.isEmpty()) {
            return "export problem";
        }
        List<Master> masters = masterRepository.getMasters();
        csvLinesMaster.forEach(line -> {
                    Master master = getMasterFromCsv(line);
                    masters.remove(master);
                    masters.add(master);
                }
        );
        return "import successfully";
    }

    private Master getMasterFromCsv(String line) {
        List<String> values = Arrays.asList(line.split(","));
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

    private String convertToCsv(Master master, boolean isLineFeed) {
        if (isLineFeed) {
            return String.format("%s,%s,%s\n", master.getId(), master.getName(), master.getNumberOrder());
        } else {
            return String.format("%s,%s,%s", master.getId(), master.getName(), master.getNumberOrder());
        }
    }
}