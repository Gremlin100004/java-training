package com.senla.carservice.api.action;

import com.senla.carservice.api.printer.PrinterMaster;
import com.senla.carservice.controller.MasterController;
import com.senla.carservice.domain.Master;

import java.util.ArrayList;

public final class BusyListMastersActionImpl implements Action {
    private static BusyListMastersActionImpl instance;

    public BusyListMastersActionImpl() {
    }

    public static BusyListMastersActionImpl getInstance() {
        if (instance == null) {
            instance = new BusyListMastersActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        MasterController masterController = new MasterController();
        ArrayList<Master> sortArrayMasters = masterController.sortMasterByBusy();
        if (sortArrayMasters.size() == 0) {
            System.out.println("There are no masters.");
            return;
        }
        PrinterMaster.printMasters(sortArrayMasters);
    }
}