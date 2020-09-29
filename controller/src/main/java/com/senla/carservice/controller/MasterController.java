package com.senla.carservice.controller;

import com.senla.carservice.controller.util.StringMaster;
import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.util.DateUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
@NoArgsConstructor
@Slf4j
public class MasterController {

    @Autowired
    private MasterService masterService;

    public List<String> getMasters() {
        log.info("Method getMasters");
        List<String> stringList = new ArrayList<>();
        try {
            List<MasterDto> mastersDto = masterService.getMasters();
            stringList.add(StringMaster.getStringFromMasters(mastersDto));
            stringList.addAll(StringMaster.getListId(mastersDto));
            return stringList;
        } catch (BusinessException e) {
            log.warn(e.getMessage());
            stringList.add(e.getMessage());
            return stringList;
        }
    }

    public String addMaster(String name) {
        log.info("Method addMaster");
        log.trace("Parameter name: {}", name);
        try {
            masterService.addMaster(name);
            return " -master \"" + name + "\" has been added to service.";
        } catch (BusinessException e) {
            log.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String checkMasters() {
        log.info("Method checkMasters");
        try {
            if (masterService.getNumberMasters() == 0) {
                throw new  BusinessException("There are no masters");
            }
            return "verification was successfully";
        } catch (BusinessException e) {
            log.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String deleteMaster(Long idMaster) {
        log.info("Method deleteMaster");
        log.trace("Parameter index: {}", idMaster);
        try {
            masterService.deleteMaster(idMaster);
            return " -master has been deleted successfully!";
        } catch (BusinessException | DaoException e) {
            log.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getMasterByAlphabet() {
        log.info("Method getMasterByAlphabet");
        try {
            return StringMaster.getStringFromMasters(masterService.getMasterByAlphabet());
        } catch (BusinessException | DaoException e) {
            log.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getMasterByBusy() {
        log.info("Method getMasterByBusy");
        try {
            return StringMaster.getStringFromMasters(masterService.getMasterByBusy());
        } catch (BusinessException | DaoException e) {
            log.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public List<String> getFreeMasters(String stringExecuteDate) {
        log.info("Method getFreeMasters");
        log.trace("Parameter stringExecuteDate: {}", stringExecuteDate);
        List<String> stringList = new ArrayList<>();
        try {
            List<MasterDto> mastersDto = masterService.getFreeMastersByDate(
                DateUtil.getDatesFromString(stringExecuteDate, true));
            stringList.add(StringMaster.getStringFromMasters(mastersDto));
            stringList.addAll(StringMaster.getListId(mastersDto));
            return stringList;
        } catch (BusinessException | DaoException e) {
            log.warn(e.getMessage());
            stringList.add(e.getMessage());
            return stringList;
        }
    }
}