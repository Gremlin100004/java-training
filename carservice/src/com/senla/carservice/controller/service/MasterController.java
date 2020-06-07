package com.senla.carservice.controller.service;

import com.senla.carservice.controller.data.Data;
import com.senla.carservice.domain.IMaster;
import com.senla.carservice.services.IAdministrator;

public class MasterController {
    private IAdministrator carService;
    private Data data;

    public MasterController(IAdministrator carService, Data data) {
        this.carService = carService;
        this.data = data;
    }

    public void addMaster() {
        System.out.println("Add master:");
        for (String masterName : this.data.getArrayMasterNames()) {
            this.carService.addMaster(masterName);
            System.out.println(String.format(" -master \"%s\" has been added to service.", masterName));
        }
        System.out.println("******************************");
    }

    public void deleteMaster() {
        IMaster master = this.carService.getMasters()[1];
        System.out.println(String.format("Delete master with name \"%s\"", master.getName()));
        this.carService.deleteMaster(master);
        System.out.println("******************************");
    }

    public void getMasterByAlphabet() {
        IMaster[] sortArrayMasters;
        System.out.println("Get masters sort by alphabet:");
        sortArrayMasters = this.carService.getMasterByAlphabet();
        for (IMaster master : sortArrayMasters) {
            System.out.println(String.format(" -master: \"%s\"", master.getName()));
        }
        System.out.println("******************************");

    }

    public void getMasterByBusy() {
        System.out.println("Get masters sort by busy().");
        IMaster[] sortArrayMasters = this.carService.getMasterByBusy();
        for (IMaster master : sortArrayMasters) {
            System.out.println(String.format("Master: \"%s\" has %s order)", master.getName(),
                    master.getNumberOrder()));
        }
        System.out.println("******************************");
    }
}