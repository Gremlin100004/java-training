package com.senla.carservice.service;

import com.senla.carservice.domain.Master;
import com.senla.carservice.repository.CarService;
import com.senla.carservice.util.Deleter;

import java.util.Arrays;

public class MasterService implements IMasterService {
    // везде были поля файнал (которые инифиализируются через конструктор), а тут забыл?
    private CarService carService;

    public MasterService(CarService carService) {
        this.carService = carService;
    }

    @Override
    public Master[] getMasters() {
        return Arrays.copyOf(this.carService.getMasters(), this.carService.getMasters().length);
    }

    @Override
    public void addMaster(String name) {
        int index = this.carService.getMasters().length;
        Master master = new Master(name);
        master.setId(this.carService.getGeneratorIdMaster().getId());
        this.carService.setMasters(Arrays.copyOf(this.carService.getMasters(), index + 1));
        this.carService.getMasters()[index] = master;
    }

    @Override
    public void deleteMaster(Master master) {
        this.carService.setMasters(Deleter.deleteElementArray(this.carService.getMasters(), master));
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