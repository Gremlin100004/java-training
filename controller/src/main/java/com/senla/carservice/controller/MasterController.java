package com.senla.carservice.controller;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.senla.carservice.controller.util.StringMaster;
import com.senla.carservice.domain.Master;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Component
public class MasterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MasterController.class);
    @Autowired
    private MasterService masterService;

    public MasterController() {
    }

    public List<String> getMasters() {
        LOGGER.info("Method getMasters");
        List<String> stringList = new ArrayList<>();
        try {
            List<Master> masters = masterService.getMasters();
            stringList.add(StringMaster.getStringFromMasters(masters));
            stringList.addAll(StringMaster.getListId(masters));
            return stringList;
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            stringList.add(e.getMessage());
            return stringList;
        }
    }

    public String addMaster(String name) {
        LOGGER.info("Method addMaster");
        LOGGER.trace("Parameter name: {}", name);
        try {
            masterService.addMaster(name);
            return " -master \"" + name + "\" has been added to service.";
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String checkMasters() {
        LOGGER.info("Method checkMasters");
        try {
            if (masterService.getNumberMasters() == 0) {
                throw new  BusinessException("There are no masters");
            }
            return "verification was successfully";
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String deleteMaster(Long idMaster) {
        LOGGER.info("Method deleteMaster");
        LOGGER.trace("Parameter index: {}", idMaster);
        try {
            masterService.deleteMaster(idMaster);
            return " -master has been deleted successfully!";
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getMasterByAlphabet() {
        LOGGER.info("Method getMasterByAlphabet");
        try {
            return StringMaster.getStringFromMasters(masterService.getMasterByAlphabet());
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getMasterByBusy() {
        LOGGER.info("Method getMasterByBusy");
        try {
            return StringMaster.getStringFromMasters(masterService.getMasterByBusy());
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public List<String> getFreeMasters(String stringExecuteDate) {
        LOGGER.info("Method getFreeMasters");
        LOGGER.trace("Parameter stringExecuteDate: {}", stringExecuteDate);
        List<String> stringList = new ArrayList<>();
        try {
            List<Master> masters = masterService.getFreeMastersByDate(
                DateUtil.getDatesFromString(stringExecuteDate, true));
            stringList.add(StringMaster.getStringFromMasters(masters));
            stringList.addAll(StringMaster.getListId(masters));
            return stringList;
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            stringList.add(e.getMessage());
            return stringList;
        }
    }
}