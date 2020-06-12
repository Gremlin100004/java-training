package com.senla.carservice.controller;

import com.senla.carservice.domain.Master;
import com.senla.carservice.service.IAdministrator;

public class MasterController {
    private IAdministrator carService;

    public MasterController(IAdministrator carService) {
        this.carService = carService;
    }
    public Master[] getMasters(){
        Master[] masters = this.carService.getMasters();
        return masters;
    }

    public String addMaster(String name) {
        this.carService.addMaster(name);
        return name;
    }

    public String deleteMaster(Master master) {
        this.carService.deleteMaster(master);
        return master.getName();
    }

    public Master[] sortMasterByAlphabet() {
        return this.carService.sortMasterByAlphabet(this.carService.getMasters());
    }

    public Master[] sortMasterByBusy() {
        return this.carService.sortMasterByBusy(this.carService.getMasters());
    }
}