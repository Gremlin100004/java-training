package com.senla.carservice.service;

import com.senla.carservice.comparator.MasterAlphabetComparator;
import com.senla.carservice.comparator.MasterBusyComparator;
import com.senla.carservice.domain.Master;
import com.senla.carservice.repository.MasterRepository;
import com.senla.carservice.repository.MasterRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public  class MasterServiceImpl implements MasterService {
    private static MasterService instance;
    private final MasterRepository masterRepository;

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
    public void deleteMaster(Master master) {
        this.masterRepository.getMasters().remove(master);
    }

    @Override
    public List<Master> sortMasterByAlphabet(List<Master> masters) {
        List<Master> sortArrayMaster = new ArrayList<>(masters);
        MasterAlphabetComparator masterAlphabetComparator = new MasterAlphabetComparator();
        sortArrayMaster.sort(masterAlphabetComparator);
        return sortArrayMaster;
    }

    @Override
    public List<Master> sortMasterByBusy(List<Master> masters) {
        List<Master> sortArrayMaster = new ArrayList<>(masters);
        MasterBusyComparator masterBusyComparator = new MasterBusyComparator();
        sortArrayMaster.sort(masterBusyComparator);
        return sortArrayMaster;
    }
}