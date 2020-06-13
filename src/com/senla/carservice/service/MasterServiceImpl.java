package com.senla.carservice.service;

import com.senla.carservice.comporator.MasterAlphabetComparator;
import com.senla.carservice.comporator.MasterBusyComparator;
import com.senla.carservice.domain.Master;
import com.senla.carservice.repository.CarOfficeRepository;
import com.senla.carservice.repository.CarOfficeRepositoryImpl;
import com.senla.carservice.util.Deleter;

import java.util.Arrays;

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
    public Master[] getMasters() {
        return Arrays.copyOf(this.carOfficeRepository.getMasters(), this.carOfficeRepository.getMasters().length);
    }

    @Override
    public void addMaster(String name) {
        int index = this.carOfficeRepository.getMasters().length;
        Master master = new Master(name);
        master.setId(this.carOfficeRepository.getIdGeneratorMaster().getId());
        this.carOfficeRepository.setMasters(Arrays.copyOf(this.carOfficeRepository.getMasters(), index + 1));
        this.carOfficeRepository.getMasters()[index] = master;
    }

    @Override
    public void deleteMaster(Master master) {
        this.carOfficeRepository.setMasters(Deleter.deleteElementArray(this.carOfficeRepository.getMasters(), master));
    }

    @Override
    public Master[] sortMasterByAlphabet(Master[] masters) {
        Master[] sortArrayMaster = masters.clone();
        MasterAlphabetComparator masterAlphabetComparator = new MasterAlphabetComparator();
        Arrays.sort(sortArrayMaster, masterAlphabetComparator);
        return sortArrayMaster;
    }

    @Override
    public Master[] sortMasterByBusy(Master[] masters) {
        Master[] sortArrayMaster = masters.clone();
        MasterBusyComparator masterBusyComparator = new MasterBusyComparator();
        Arrays.sort(sortArrayMaster, masterBusyComparator);
        return sortArrayMaster;
    }
}