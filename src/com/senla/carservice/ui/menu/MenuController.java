package com.senla.carservice.ui.menu;

import com.senla.carservice.controller.MasterController;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.controller.PlaceController;
import com.senla.carservice.ui.util.Printer;
import com.senla.carservice.ui.util.ScannerUtil;

public class MenuController {
    private static MenuController instance;
    private final Navigator navigator;

    private MenuController() {
        Builder builder = Builder.getInstance();
        builder.buildMenu();
        this.navigator = Navigator.getInstance(builder.getRootMenu());
    }

    public static MenuController getInstance() {
        if (instance == null) {
            instance = new MenuController();
        }
        return instance;
    }

    public void run() {
        int answer = 1;
        Printer.printInfo(MasterController.getInstance().deserializeMaster());
        Printer.printInfo(PlaceController.getInstance().deserializePlace());
        Printer.printInfo(OrderController.getInstance().deserializeOrder());
        while (answer != 0) {
            this.navigator.printMenu();
            answer = ScannerUtil.getIntUser("Enter number item menu:");
            if (answer != 0) {
                navigator.navigate(answer);
            }
        }
        Printer.printInfo(MasterController.getInstance().serializeMaster());
        Printer.printInfo(PlaceController.getInstance().serializePlace());
        Printer.printInfo(OrderController.getInstance().serializeOrder());
    }
}