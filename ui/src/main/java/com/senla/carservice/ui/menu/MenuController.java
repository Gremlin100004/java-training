package com.senla.carservice.ui.menu;

import com.senla.carservice.controller.CarOfficeController;
import com.senla.carservice.ui.util.ScannerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MenuController {

    @Autowired
    private Navigator navigator;
    @Autowired
    private Builder builder;
    @Autowired
    private CarOfficeController carOfficeController;

    public MenuController() {
    }

    public void run() {
        builder.buildMenu();
        navigator.addCurrentMenu(builder.getRootMenu());
        int answer = 1;
        while (answer != 0) {
            this.navigator.printMenu();
            answer = ScannerUtil.getIntUser("Enter number item menu:");
            if (answer != 0) {
                navigator.navigate(answer);
            }
        }
    }
}