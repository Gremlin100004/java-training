package com.senla.carservice.ui.menu;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.ui.util.ScannerUtil;

@Singleton
public class MenuController {

    @Dependency
    private Navigator navigator;
    @Dependency
    private Builder builder;

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