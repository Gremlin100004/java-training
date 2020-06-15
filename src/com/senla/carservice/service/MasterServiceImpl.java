package com.senla.carservice.service;

import com.senla.carservice.comporator.MasterAlphabetComparator;
import com.senla.carservice.comporator.MasterBusyComparator;
import com.senla.carservice.domain.Master;
import com.senla.carservice.repository.CarOfficeRepository;
import com.senla.carservice.repository.CarOfficeRepositoryImpl;

import java.util.ArrayList;
import java.util.Collections;

public final class MasterServiceImpl implements MasterService {
    private static MasterServiceImpl instance;
    private final CarOfficeRepository carOfficeRepository;

    public MasterServiceImpl() {
        this.carOfficeRepository = CarOfficeRepositoryImpl.getInstance();
    }

    public static MasterServiceImpl getInstance() {
        if (instance == null) {
            instance = new MasterServiceImpl();
        }
        return instance;
    }

    @Override
    public ArrayList<Master> getMasters() {
        return this.carOfficeRepository.getMasters();
    }

    @Override
    public void addMaster(String name) {
        Master master = new Master(name);
        master.setId(this.carOfficeRepository.getIdGeneratorMaster().getId());
        this.carOfficeRepository.getMasters().add(master);
    }

    @Override
    public void deleteMaster(Master master) {
        this.carOfficeRepository.getMasters().remove(master);
    }

    @Override
    public ArrayList<Master> sortMasterByAlphabet(ArrayList<Master> masters) {
        ArrayList<Master> sortArrayMaster = new ArrayList<>();
        Collections.copy(sortArrayMaster, masters);
        MasterAlphabetComparator masterAlphabetComparator = new MasterAlphabetComparator();
        sortArrayMaster.sort(masterAlphabetComparator);
        return sortArrayMaster;
    }

    @Override
    public ArrayList<Master> sortMasterByBusy(ArrayList<Master> masters) {
        ArrayList<Master> sortArrayMaster = new ArrayList<>();
        Collections.copy(sortArrayMaster, masters);
        MasterBusyComparator masterBusyComparator = new MasterBusyComparator();
        sortArrayMaster.sort(masterBusyComparator);
        return sortArrayMaster;
    }
}