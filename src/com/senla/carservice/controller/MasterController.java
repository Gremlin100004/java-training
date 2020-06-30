package com.senla.carservice.controller;

import com.senla.carservice.domain.Order;
import com.senla.carservice.exception.*;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.MasterServiceImpl;
import com.senla.carservice.service.OrderService;
import com.senla.carservice.service.OrderServiceImpl;
import com.senla.carservice.string.StringMaster;
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
            return e.getMessage();
        }
    }

    public String addMaster(String name) {
        masterService.addMaster(name);
        return String.format(" -master \"%s\" has been added to service.", name);
    }

    public String deleteMaster(int index) {
        try {
            masterService.deleteMaster(masterService.getMasters().get(index));
            return " -master has been deleted successfully!";
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        } catch (IndexOutOfBoundsException e){
            return "There are no such master";
        }
    }

    public String getMasterByAlphabet() {
        try {
            return StringMaster.getStringFromMasters(masterService.getMasterByAlphabet());
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getMasterByBusy() {
        try {
            return StringMaster.getStringFromMasters(masterService.getMasterByBusy());
        } catch (NumberObjectZeroException e) {
            return e.getMessage();
        }
    }

    public String getFreeMasters(String stringExecuteDate, String stringLeadDate){
        Date executeDate = DateUtil.getDatesFromString(stringExecuteDate, true);
        Date leadDate = DateUtil.getDatesFromString(stringLeadDate, true);
        try {
            List<Order> orders = orderService.getOrderByPeriod(executeDate, leadDate);
            return StringMaster.getStringFromMasters(masterService.getFreeMastersByDate(executeDate, leadDate, orders));
        } catch (NumberObjectZeroException | DateException | NullDateException e) {
            return e.getMessage();
        }
    }
    public String exportMasters() {
        try {
            masterService.exportMasters();
            return "Masters have been export successfully!";
        } catch (NumberObjectZeroException | ExportException e){
            return e.getMessage();
        }
    }

    public String importMasters() {
        try {
            return masterService.importMasters();
        } catch (NumberObjectZeroException e){
            return e.getMessage();
        }
    }

    public String serializeMaster() {
        try {
            masterService.serializeMaster();
            return "Masters have been serialize successfully!";
        } catch (SerializeException e){
            return e.getMessage();
        }
    }

    public String deserializeMaster() {
        try {
            masterService.deserializeMaster();
            return "Masters have been deserialize successfully!";
        } catch (SerializeException e){
            return e.getMessage();
        }
    }
}