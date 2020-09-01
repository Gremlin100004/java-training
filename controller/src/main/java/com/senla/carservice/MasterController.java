package com.senla.carservice;

import com.senla.carservice.annotation.Singleton;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.exception.DaoException;
import com.senla.carservice.exception.DateException;
import com.senla.carservice.objectadjuster.dependencyinjection.annotation.Dependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class MasterController {

    @Dependency
    private MasterService masterService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MasterController.class);


    public MasterController() {
    }

    public String getMasters() {
        LOGGER.info("Method getMasters");
        try {
            return StringMaster.getStringFromMasters(masterService.getMasters());
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String addMaster(String name) {
        LOGGER.info("Method addMaster");
        LOGGER.debug("Parameter name: {}", name);
        try {
            masterService.addMaster(name);
            return " -master \"" + name + "\" has been added to service.";
        } catch (DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String deleteMaster(int index) {
        LOGGER.info("Method deleteMaster");
        LOGGER.debug("Parameter index: {}", index);
        try {
            if (masterService.getNumberMasters() < index || index < 0) {
                return "There are no such master";
            } else {
                masterService.deleteMaster(masterService.getMasters().get(index));
                return " -master has been deleted successfully!";
            }
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getMasterByAlphabet() {
        LOGGER.info("Method getMasterByAlphabet");
        try {
            return StringMaster.getStringFromMasters(masterService.getMasterByAlphabet());
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getMasterByBusy() {
        LOGGER.info("Method getMasterByBusy");
        try {
            return StringMaster.getStringFromMasters(masterService.getMasterByBusy());
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getFreeMasters(String stringExecuteDate) {
        LOGGER.info("Method getFreeMasters");
        LOGGER.debug("Parameter stringExecuteDate: {}", stringExecuteDate);
        try {
            return StringMaster.getStringFromMasters(
                masterService.getFreeMastersByDate(DateUtil.getDatesFromString(stringExecuteDate, true)));
        } catch (BusinessException | DaoException | DateException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }
}