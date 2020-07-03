package com.senla.carservice.service;

import com.senla.carservice.domain.Master;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.repository.MasterRepository;
import com.senla.carservice.repository.MasterRepositoryImpl;
import com.senla.carservice.repository.OrderRepository;
import com.senla.carservice.repository.OrderRepositoryImpl;
import com.senla.carservice.util.Serializer;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MasterServiceImpl implements MasterService {
    private static MasterService instance;
    private final MasterRepository masterRepository;
    private final OrderRepository orderRepository;

    private MasterServiceImpl() {
        masterRepository = MasterRepositoryImpl.getInstance();
        orderRepository = OrderRepositoryImpl.getInstance();
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
    public List<Master> getFreeMastersByDate(Date executeDate) {
        List<Master> freeMasters = getFreeMaster(executeDate);
        if (freeMasters.isEmpty()) {
            throw new BusinessException("There are no free masters");
        }
        return freeMasters;
    }

    @Override
    public int getNumberFreeMastersByDate(Date startDayDate) {
        return getFreeMaster(startDayDate).size();
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
            .sorted(Comparator.comparing(master -> master.getOrders().size(),
                                         Comparator.nullsFirst(Comparator.naturalOrder())))
            .collect(Collectors.toList());
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

    private List<Master> getFreeMaster(Date startDate) {
        checkMasters();
        return masterRepository.getFreeMasters(startDate);
    }
}