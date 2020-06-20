package com.senla.carservice.ui.action;

import com.senla.carservice.ui.printer.PrinterMaster;
import com.senla.carservice.controller.MasterController;
import com.senla.carservice.domain.Master;

import java.util.ArrayList;
import java.util.List;

public class BusyListMastersActionImpl implements Action {

    public BusyListMastersActionImpl() {
    }

    @Override
    public void execute() {
        MasterController masterController = MasterController.getInstance();
        List<Master> sortArrayMasters = masterController.sortMasterByBusy();
        if (sortArrayMasters.size() == 0) {
            System.out.println("There are no masters.");
            return;
        }
        PrinterMaster.printMasters(sortArrayMasters);
    }
}