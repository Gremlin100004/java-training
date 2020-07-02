package com.senla.carservice.service;

import com.senla.carservice.csvutil.CsvMaster;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.repository.MasterRepository;
import com.senla.carservice.repository.MasterRepositoryImpl;
import com.senla.carservice.util.DateUtil;
import com.senla.carservice.util.Serializer;

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
        if (getFreeMaster(sortOrders, executeDate, leadDate).isEmpty()) {
            throw new BusinessException("There are no free masters");
        }
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
        return masterRepository.getMasters().stream()
            .sorted(Comparator.comparing(Master::getName, Comparator.nullsLast(Comparator.naturalOrder())))
            .collect(Collectors.toList());
    }

    @Override
    public List<Master> getMasterByBusy() {
        checkMasters();
        return masterRepository.getMasters()
            .stream()
            .sorted(Comparator.comparing(Master::getNumberOrder, Comparator.nullsFirst(Comparator.naturalOrder())))
            .collect(Collectors.toList());
    }

    @Override
    public void exportMasters() {
        checkMasters();
        CsvMaster.exportMasters(masterRepository.getMasters());
    }

    @Override
    public void importMasters() {
        CsvMaster.importMasters().forEach(masterRepository::updateMaster);
    }

    @Override
    public void serializeMaster() {
        Serializer.serializeMaster(MasterRepositoryImpl.getInstance());
    }

    @Override
    public void deserializeMaster() {
        MasterRepository masterRepositoryRestore = Serializer.deserializeMaster();
        masterRepository.updateListMaster(masterRepositoryRestore.getMasters());
        masterRepository.updateGenerator(masterRepositoryRestore.getIdGeneratorMaster());
    }

    private void checkMasters() {
        if (masterRepository.getMasters().isEmpty()) {
            throw new BusinessException("There are no masters");
        }
    }

    private List<Master> getFreeMaster(List<Order> orders, Date executeDate, Date leadDate) {
        checkMasters();
        DateUtil.checkDateTime(executeDate, leadDate);
        return masterRepository.getFreeMasters(orders);
    }
}