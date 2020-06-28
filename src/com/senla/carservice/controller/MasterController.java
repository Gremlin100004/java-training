package com.senla.carservice.controller;

import com.senla.carservice.domain.Order;
import com.senla.carservice.exception.DateException;
import com.senla.carservice.exception.NullDateException;
import com.senla.carservice.exception.NumberObjectZeroException;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.MasterServiceImpl;
import com.senla.carservice.service.OrderService;
import com.senla.carservice.service.OrderServiceImpl;
import com.senla.carservice.ui.string.StringMaster;
import com.senla.carservice.ui.string.StringPlaces;
import com.senla.carservice.util.DateUtil;

import java.util.Date;
import java.util.List;

public class MasterController {
    private static MasterController instance;
    private final MasterService masterService;
    private final OrderService orderService;

    private MasterController() {
        masterService = MasterServiceImpl.getInstance();
        orderService = OrderServiceImpl.getInstance();
    }

    public static MasterController getInstance() {
        if (instance == null) {
            instance = new MasterController();
        }
        return instance;
    }

    public String getMasters() {
        try {
            return StringMaster.getStringFromMasters(masterService.getMasters());
        } catch (NumberObjectZeroException e) {
            return String.valueOf(e);
        }
    }

    public String addMaster(String name) {
        masterService.addMaster(name);
        return String.format(" -master \"%s\" has been added to service.", name);
    }

    public String deleteMaster(int index) {
        try {
            masterService.deleteMaster(masterService.getMasters().get(index));
            return String.format(" -master with name \"%s\" has been deleted",
                    masterService.getMasters().get(index).getName());
        } catch (NumberObjectZeroException e) {
            return String.valueOf(e);
        } catch (IndexOutOfBoundsException e){
            return "There are no such master";
        }
    }

    public String getMasterByAlphabet() {
        try {
            return StringMaster.getStringFromMasters(masterService.getMasterByAlphabet());
        } catch (NumberObjectZeroException e) {
            return String.valueOf(e);
        }
    }

    public String getMasterByBusy() {
        try {
            return StringMaster.getStringFromMasters(masterService.getMasterByBusy());
        } catch (NumberObjectZeroException e) {
            return String.valueOf(e);
        }
    }

    public String getFreeMasters(String stringExecuteDate, String stringLeadDate){
        Date executeDate = DateUtil.getDatesFromString(stringExecuteDate);
        Date leadDate = DateUtil.getDatesFromString(stringLeadDate);
        try {
            List<Order> orders = orderService.getOrderByPeriod(executeDate, leadDate);
            return StringMaster.getStringFromMasters(masterService.getFreeMastersByDate(executeDate, leadDate, orders));
        } catch (NumberObjectZeroException | DateException | NullDateException e) {
            return String.valueOf(e);
        }
    }
//    public String exportMasters() {
//        if (masterService.exportMasters().equals("save successfully")) {
//            return "Masters have been export successfully!";
//        } else {
//            return "export problem.";
//        }
//    }

//    public String importMasters() {
//        if (masterService.importMasters().equals("import successfully")) {
//            return "Masters have been import successfully!";
//        } else {
//            return "import problem.";
//        }
//    }
}