package com.senla.carservice.ui.action;

import com.senla.carservice.ui.printer.PrinterMaster;
import com.senla.carservice.controller.MasterController;
import com.senla.carservice.domain.Master;

import java.util.ArrayList;
import java.util.List;

public class AlphabetListMasterActionImpl implements Action {

    public AlphabetListMasterActionImpl() {
    }

    @Override
    public void execute() {
        MasterController masterController = MasterController.getInstance();
        List<Master> sortArrayMasters = masterController.sortMasterByAlphabet();
        if (sortArrayMasters.isEmpty()) {
            System.out.println("There are no masters.");
            return;
        }
        PrinterMaster.printMasters(sortArrayMasters);
    }
}