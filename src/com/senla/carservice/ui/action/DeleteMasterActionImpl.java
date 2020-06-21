package com.senla.carservice.ui.action;

import com.senla.carservice.controller.MasterController;
import com.senla.carservice.domain.Master;
import com.senla.carservice.ui.printer.PrinterMaster;
import com.senla.carservice.ui.util.ScannerUtil;

import java.util.List;

public class DeleteMasterActionImpl implements Action {

    public DeleteMasterActionImpl() {
    }

    @Override
    public void execute() {
        MasterController masterController = MasterController.getInstance();
        List<Master> masters = masterController.getMasters();
        if (masters.isEmpty()) {
            System.out.println("There are no masters to delete!");
            return;
        }
        PrinterMaster.printMasters(masters);
        System.out.println("0. Previous menu");
        String message = null;
        int index;
        while (message == null) {
            System.out.println();
            index = ScannerUtil.getIntUser("Enter the index number of the master to delete:");
            if (index == 0) {
                return;
            }
            if (index > masters.size() || index < 0) {
                System.out.println("There is no such master");
                continue;
            }
            message = masterController.deleteMaster(masters.get(index - 1));
        }
        System.out.println(message);
    }
}