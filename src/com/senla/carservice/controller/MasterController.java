package com.senla.carservice.controller;

import com.senla.carservice.domain.Master;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.MasterServiceImpl;

import java.util.List;

public class MasterController {
    private static MasterController instance;
    private final MasterService masterService;


    private MasterController() {
        this.masterService = MasterServiceImpl.getInstance();
    }

    public static MasterController getInstance() {
        if (instance == null) {
            instance = new MasterController();
        }
        return instance;
    }

    public List<Master> getMasters() {
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

    public List<Master> sortMasterByAlphabet() {
        return this.masterService.sortMasterByAlphabet(this.masterService.getMasters());
    }

    public List<Master> sortMasterByBusy() {
        return this.masterService.sortMasterByBusy(this.masterService.getMasters());
    }

    public String exportMasters() {
        if (this.masterService.exportMasters().equals("save successfully")) {
            return "Masters have been export successfully!";
        } else {
            return "export problem.";
        }
    }
    public String importMasters() {
        if (this.masterService.importMasters().equals("import successfully")) {
            return "Masters have been import successfully!";
        } else {
            return "export problem.";
        }
    }

}