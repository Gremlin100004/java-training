package com.senla.carservice.services;

import com.senla.carservice.domain.ICarService;
import com.senla.carservice.domain.IMaster;
import com.senla.carservice.domain.Master;
import com.senla.carservice.util.Deleter;
import com.senla.carservice.util.Sort;

import java.util.Arrays;

public class MasterService implements IMasterService {
    private ICarService carService;

    public MasterService(ICarService carService) {
        this.carService = carService;
    }

    @Override
    public IMaster[] getMasters() {
        return Arrays.copyOf(this.carService.getMasters(), this.carService.getMasters().length);
    }

    @Override
    public void addMaster(String name) {
        int index = this.carService.getMasters().length;
        this.carService.setMasters(Arrays.copyOf(this.carService.getMasters(), index + 1));
        this.carService.getMasters()[index] = new Master(name);
    }

    @Override
    public void deleteMaster(IMaster master) {
        Deleter deleter = new Deleter();
        this.carService.setMasters(deleter.deleteElementArray(this.carService.getMasters(), master));
    }

    @Override
    public IMaster[] getMasterByAlphabet() {
        String[] arrayMasterName = new String[this.carService.getMasters().length];
        IMaster[] sortArrayMaster;
        for (int i = 0; i < this.carService.getMasters().length; i++) {
            arrayMasterName[i] = this.carService.getMasters()[i].getName();
        }
        Sort arrayMaintenance = new Sort();
        sortArrayMaster = arrayMaintenance.bubbleSort(arrayMasterName, this.carService.getMasters());
        return sortArrayMaster;
    }

    @Override
    public IMaster[] getMasterByBusy() {
        Integer[] arrayNumberOrder = new Integer[this.carService.getMasters().length];
        IMaster[] sortArrayMaster;
        for (int i = 0; i < this.carService.getMasters().length; i++) {
            arrayNumberOrder[i] = this.carService.getMasters()[i].getNumberOrder();
        }
        Sort arrayMaintenance = new Sort();
        sortArrayMaster = arrayMaintenance.bubbleSort(arrayNumberOrder, this.carService.getMasters());
        return sortArrayMaster;
    }
}