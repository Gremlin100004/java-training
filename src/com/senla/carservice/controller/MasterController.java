package com.senla.carservice.controller;

import com.senla.carservice.domain.Master;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.MasterServiceImpl;

public class MasterController {
    private final MasterService masterService;

    public MasterController() {
        this.masterService = new MasterServiceImpl();
    }

    public Master[] getMasters() {
        return this.masterService.getMasters();
    }

    public String addMaster(String name) {
        this.masterService.addMaster(name);
        return String.format(" -master \"%s\" has been added to service.", name);
    }

    public String deleteMaster(Master master) {
        this.masterService.deleteMaster(master);
        return master.getName();
    }

    public Master[] sortMasterByAlphabet() {
        return this.masterService.sortMasterByAlphabet(this.masterService.getMasters());
    }

    public Master[] sortMasterByBusy() {
        return this.masterService.sortMasterByBusy(this.masterService.getMasters());
    }
}