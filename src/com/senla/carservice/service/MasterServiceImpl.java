package com.senla.carservice.service;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.repository.MasterRepository;
import com.senla.carservice.repository.MasterRepositoryImpl;
import com.senla.carservice.util.FileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
        Master master = new Master(name);
        master.setId(this.masterRepository.getIdGeneratorMaster().getId());
        this.masterRepository.getMasters().add(master);
    }

    @Override
    public List<Master> getFreeMasters(Date executeDate, Date leadDate, List<Order> sortOrders) {
        List<Master> freeMasters = new ArrayList<>(this.masterRepository.getMasters());
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
        this.masterRepository.getMasters().remove(master);
    }

    @Override
    public List<Master> sortMasterByAlphabet(List<Master> masters) {
        List<Master> sortArrayMaster = new ArrayList<>(masters);
        sortArrayMaster.sort((masterOne, masterTwo) -> {
            if (masterOne.getName() == null && masterTwo.getName() == null) return 0;
            if (masterOne.getName() == null) return -1;
            if (masterTwo.getName() == null) return 1;
            return masterOne.getName().compareTo(masterTwo.getName());
        });
        return sortArrayMaster;
    }

    @Override
    public List<Master> sortMasterByBusy(List<Master> masters) {
        List<Master> sortArrayMaster = new ArrayList<>(masters);
        sortArrayMaster.sort((masterOne, masterTwo) -> {
            if (masterOne.getNumberOrder() == null && masterTwo.getNumberOrder() == null) return 0;
            if (masterOne.getNumberOrder() == null) return -1;
            if (masterTwo.getNumberOrder() == null) return 1;
            return masterOne.getNumberOrder() - masterTwo.getNumberOrder();
        });
        return sortArrayMaster;
    }

    @Override
    public String exportMasters() {
        MasterRepository masterRepository = MasterRepositoryImpl.getInstance();
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
        List<Master> masters = this.masterRepository.getMasters();
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