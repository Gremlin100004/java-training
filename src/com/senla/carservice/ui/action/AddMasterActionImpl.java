package com.senla.carservice.ui.action;

import com.senla.carservice.controller.MasterController;
import com.senla.carservice.ui.util.ScannerUtil;

public class AddMasterActionImpl implements Action {

    public AddMasterActionImpl() {
    }

    @Override
    public void execute() {
        MasterController masterController = MasterController.getInstance();
        String name = ScannerUtil.getStringUser("Enter the name of master");
        System.out.println(masterController.addMaster(name));
    }
}