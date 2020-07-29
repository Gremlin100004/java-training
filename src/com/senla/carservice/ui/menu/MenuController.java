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

    // вызов этого метода лучше сделать из метода ран,
    // иначе пользоваться таким классом очень неудобно - можно забыть вызывать configure()
    // или вызывать его дважды
    // учитывая, что метод ран вызывается единожды в приложении, будет норм, если поместить эту логику
    // туда
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