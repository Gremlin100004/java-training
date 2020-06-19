package com.senla.carservice.controller;

import com.senla.carservice.domain.Master;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.MasterServiceImpl;

import java.util.ArrayList;

public class MasterController {
    private final MasterService masterService;

    public MasterController() {
        this.masterService = new MasterServiceImpl();
    }

    public ArrayList<Master> getMasters() {
        return this.masterService.getMasters();
    }

    public String addMaster(String name) {
        this.masterService.addMaster(name);
        return String.format(" -master \"%s\" has been added to service.", name);
    }

    public String deleteMaster(Master master) {
        this.masterService.deleteMaster(master);
        return String.format(" -master with name \"%s\" has been deleted", master.getName());
    }

    public ArrayList<Master> sortMasterByAlphabet() {
        return this.masterService.sortMasterByAlphabet(this.masterService.getMasters());
    }

    public ArrayList<Master> sortMasterByBusy() {
        return this.masterService.sortMasterByBusy(this.masterService.getMasters());
    }
}