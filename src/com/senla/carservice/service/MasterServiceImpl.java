package com.senla.carservice.service;

import com.senla.carservice.container.annotation.Dependency;
import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.domain.Master;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.repository.MasterRepository;
import com.senla.carservice.repository.OrderRepository;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class MasterServiceImpl implements MasterService {
    @Dependency
    private MasterRepository masterRepository;
    @Dependency
    private OrderRepository orderRepository;

    public MasterServiceImpl() {
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
        return masterRepository.getMasters().stream()
            .sorted(Comparator.comparing(master -> master.getOrders().size(),
                                         Comparator.nullsFirst(Comparator.naturalOrder())))
            .collect(Collectors.toList());
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