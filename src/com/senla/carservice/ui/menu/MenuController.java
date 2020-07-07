package com.senla.carservice.ui.menu;

import com.senla.carservice.annotation.InjectDependency;
import com.senla.carservice.ui.util.ScannerUtil;

public class MenuController {
    @InjectDependency
    private Navigator navigator;
    @InjectDependency
    private Builder builder;

    public MenuController() {
    }

    public void configure() {
        builder.buildMenu();
        navigator.addCurrentMenu(builder.getRootMenu());
    }

    public void run() {
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