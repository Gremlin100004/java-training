package com.senla.carservice.api.action;

import com.senla.carservice.api.printer.PrinterMaster;
import com.senla.carservice.controller.MasterController;
import com.senla.carservice.domain.Master;

import java.util.ArrayList;

public final class AlphabetListMasterActionImpl implements Action {
    private static AlphabetListMasterActionImpl instance;

    public AlphabetListMasterActionImpl() {
    }

    public static AlphabetListMasterActionImpl getInstance() {
        if (instance == null) {
            instance = new AlphabetListMasterActionImpl();
        }
        return instance;
    }

    @Override
    public void execute() {
        MasterController masterController = new MasterController();
        ArrayList<Master> sortArrayMasters = masterController.sortMasterByAlphabet();
        if (sortArrayMasters.size() == 0) {
            System.out.println("There are no masters.");
            return;
        }
        PrinterMaster.printMasters(sortArrayMasters);
    }
}