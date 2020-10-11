package com.senla.carservice.ui.menu;

import com.senla.carservice.ui.util.ScannerUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Slf4j
public class MenuController {
    private static final String MENU_ITEM_INPUT_HEADER = "Enter number item menu:";
    @Autowired
    private Navigator navigator;
    @Autowired
    private Builder builder;

    public void run() {
        log.info("[run]");
        builder.buildMenu();
        navigator.addCurrentMenu(builder.getRootMenu());
        int answer = 1;
        while (answer != 0) {
            this.navigator.printMenu();
            answer = ScannerUtil.getIntUser(MENU_ITEM_INPUT_HEADER);
            if (answer != 0) {
                navigator.navigate(answer);
            }
        }
    }

}
