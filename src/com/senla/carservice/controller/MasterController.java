package com.senla.carservice.controller;

import com.senla.carservice.factory.annotation.Dependency;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.exception.DateException;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.stringutil.StringMaster;
import com.senla.carservice.util.DateUtil;

import java.util.Date;

public class MasterController {
    @Dependency
    private MasterService masterService;

    public MasterController() {
    }

    public String getMasters() {
        try {
            return StringMaster.getStringFromMasters(masterService.getMasters());
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String addMaster(String name) {
        masterService.addMaster(name);
        return " -master \"" + name + "\" has been added to service.";
    }

    public String deleteMaster(int index) {
        try {
            if (masterService.getMasters().size() < index || index < 0) {
                return "There are no such master";
            } else {
                masterService.deleteMaster(masterService.getMasters().get(index));
                return " -master has been deleted successfully!";
            }
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getMasterByAlphabet() {
        try {
            return StringMaster.getStringFromMasters(masterService.getMasterByAlphabet());
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getMasterByBusy() {
        try {
            return StringMaster.getStringFromMasters(masterService.getMasterByBusy());
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getFreeMasters(String stringExecuteDate) {
        Date executeDate = DateUtil.getDatesFromString(stringExecuteDate, true);
        try {
            return StringMaster.getStringFromMasters(masterService.getFreeMastersByDate(executeDate));
        } catch (DateException | BusinessException e) {
            return e.getMessage();
        }
    }
}