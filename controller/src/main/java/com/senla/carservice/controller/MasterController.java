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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
@NoArgsConstructor
@Slf4j
public class MasterController {
    @Autowired
    private MasterService masterService;

    @GetMapping("masters/get")
    public List<MasterDto> getMasters() {
        log.info("Method getMasters");
        return masterService.getMasters();
//        try {
//
//            return stringList;
//        } catch (BusinessException e) {
//            log.warn(e.getMessage());
//            stringList.add(e.getMessage());
//            return stringList;
//        }
    }

    @PostMapping("masters/add")
    public String addMaster(@RequestBody MasterDto masterDto) {
        log.info("Method addMaster");
        log.trace("Parameter masterDto: {}", masterDto);
        try {
            System.out.println(masterDto.getName());
            masterService.addMaster(masterDto);
            return " -master \"" + masterDto.getName() + "\" has been added to service.";
        } catch (BusinessException e) {
            log.warn(e.getMessage());
            return e.getMessage();
        }
    }

    @GetMapping("masters/check")
    public String checkMasters() {
        log.info("Method checkMasters");
        try {
            if (masterService.getNumberMasters() == 0) {
                throw new BusinessException("There are no masters");
            }
            return "verification was successfully";
        } catch (BusinessException e) {
            log.warn(e.getMessage());
            return e.getMessage();
        }
    }

    @DeleteMapping("masters/delete/{idMaster}")
    public String deleteMaster(@PathVariable Long idMaster) {
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

    @GetMapping("masters/get/sort-by-alphabet")
    public List<MasterDto> getMasterByAlphabet() {
        log.info("Method getMasterByAlphabet");
        try {
            return masterService.getMasterByAlphabet();
        } catch (BusinessException | DaoException e) {
            log.warn(e.getMessage());
//            return e.getMessage();
            return null;
        }
    }

    @GetMapping("masters/get/sort-by-busy")
    public List<MasterDto> getMasterByBusy() {
        log.info("Method getMasterByBusy");
        try {
            return masterService.getMasterByBusy();
        } catch (BusinessException | DaoException e) {
            log.warn(e.getMessage());
//            return e.getMessage();
            return null;
        }
    }

    @GetMapping("masters/get/free")
    public List<MasterDto> getFreeMasters(@RequestHeader String stringExecuteDate) {
        log.info("Method getFreeMasters");
        log.trace("Parameter stringExecuteDate: {}", stringExecuteDate);
        try {
            return masterService.getFreeMastersByDate(DateUtil.getDatesFromString(stringExecuteDate, true));
        } catch (BusinessException | DaoException e) {
            log.warn(e.getMessage());
            return null;
        }
    }

    //ToDo all method crud
}