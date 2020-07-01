package com.senla.carservice.service;

import com.senla.carservice.csvutil.CsvMaster;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.exception.NumberObjectZeroException;
import com.senla.carservice.repository.MasterRepository;
import com.senla.carservice.repository.MasterRepositoryImpl;
import com.senla.carservice.util.DateUtil;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MasterServiceImpl implements MasterService {
    private static MasterService instance;
    private final MasterRepository masterRepository;

    private MasterServiceImpl() {
        masterRepository = MasterRepositoryImpl.getInstance();
    }

    public static MasterService getInstance() {
        if (instance == null) {
            instance = new MasterServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Master> getMasters() {
        checkMasters();
        return masterRepository.getMasters();
    }

    @Override
    public void addMaster(String name) {
        masterRepository.addMaster(new Master(name));
    }

    @Override
    public List<Master> getFreeMastersByDate(Date executeDate, Date leadDate, List<Order> sortOrders) {
        // фигурные скобки ставим ВСЕГДА!!!!
        if (getFreeMaster(sortOrders, executeDate, leadDate).isEmpty())
            throw new NumberObjectZeroException("There are no free masters");
        return masterRepository.getFreeMasters(sortOrders);
    }

    @Override
    public int getNumberFreeMastersByDate(Date executeDate, Date leadDate, List<Order> sortOrders) {
        return getFreeMaster(sortOrders, executeDate, leadDate).size();
    }

    @Override
    public void deleteMaster(Master master) {
        checkMasters();
        masterRepository.deleteMaster(master);
    }

    @Override
    public List<Master> getMasterByAlphabet() {
        checkMasters();
        // делай из стрима столбик по точке
        return masterRepository.getMasters().stream().sorted(Comparator.comparing(Master::getName,
                Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());
    }

    @Override
    public List<Master> getMasterByBusy() {
        checkMasters();
        return masterRepository.getMasters().stream().sorted(Comparator.comparing(Master::getNumberOrder,
                Comparator.nullsFirst(Comparator.naturalOrder()))).collect(Collectors.toList());
    }

    @Override
    public void exportMasters() {
        checkMasters();
        CsvMaster.exportMasters(masterRepository.getMasters());
    }

    @Override
    public String importMasters() {
        return CsvMaster.importMasters();
    }

    private void checkMasters() {
        // скобки, строки
        if (masterRepository.getMasters().isEmpty()) throw new NumberObjectZeroException("There are no masters");
    }

    private List<Master> getFreeMaster(List<Order> orders, Date executeDate, Date leadDate) {
        checkMasters();
        DateUtil.checkDateTime(executeDate, leadDate);
        return masterRepository.getFreeMasters(orders);
    }
}