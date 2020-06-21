package com.senla.carservice.ui.action;

import com.senla.carservice.controller.MasterController;

public class ExportMastersActionImpl implements Action {
    public ExportMastersActionImpl() {
    }

    @Override
    public void execute() {
        MasterController masterController = MasterController.getInstance();
        System.out.println(masterController.exportMasters());
    }
}