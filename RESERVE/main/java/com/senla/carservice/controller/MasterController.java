package com.senla.carservice.controller;

import com.senla.carservice.annotation.Singleton;
import com.senla.carservice.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.util.DateUtil;
import com.senla.carservice.util.stringutil.StringMaster;

@Singleton
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
        try {
            return StringMaster.getStringFromMasters(
                masterService.getFreeMastersByDate(DateUtil.getDatesFromString(stringExecuteDate, true)));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }
}