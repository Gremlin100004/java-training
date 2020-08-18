package com.senla.carservice;

import com.senla.carservice.container.Container;
import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.domain.Master;
import com.senla.carservice.ui.menu.MenuController;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        MasterDao masterDao = Container.getObject(MasterDao.class);
        List<Master> masters = masterDao.getAllRecords();
        System.out.println(masters);
        MenuController menuController = Container.getObject(MenuController.class);
        menuController.run();
    }
}