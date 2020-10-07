package com.senla.carservice.ui.menu;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.senla.carservice.ui.util.Printer;

import java.util.List;

@Component
@NoArgsConstructor
@Slf4j
public class Navigator {

    private static final int INDEX_OFFSET = 1;
    private static final String WARNING_ITEM_MESSAGE = "There is no such item!!!";
    private Menu currentMenu;

    public void addCurrentMenu(Menu currentMenu) {
        log.debug("Method addCurrentMenu");
        log.trace("Parameter currentMenu: {}", currentMenu);
        this.currentMenu = currentMenu;
    }

    public void printMenu() {
        log.info("Method printMenu");
        log.info(this.currentMenu.toString());
        Printer.printInfo(this.currentMenu.toString());
    }

    public void navigate(Integer index) {
        log.debug("Method navigate");
        log.trace("Parameter index: {}", index);
        List<MenuItem> menuItems = this.currentMenu.getMenuItems();
        if (index > menuItems.size()) {
            Printer.printInfo(WARNING_ITEM_MESSAGE);
            return;
        }
        MenuItem menuItem = menuItems.get(index - INDEX_OFFSET);
        menuItem.doAction();
        this.currentMenu = menuItem.getNextMenu();
    }

}
