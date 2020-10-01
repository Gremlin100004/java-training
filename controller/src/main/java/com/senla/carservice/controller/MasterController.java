package com.senla.carservice.controller;

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

import java.util.List;

@RestController
@RequestMapping("/")
@NoArgsConstructor
@Slf4j
public class MasterController {
    @Autowired
    private MasterService masterService;

    @GetMapping("masters")
    public List<MasterDto> getMasters() {
        log.info("Method getMasters");
        return masterService.getMasters();
    }

    @PostMapping("masters")
    public String addMaster(@RequestBody MasterDto masterDto) {
        log.info("Method addMaster");
        log.trace("Parameter masterDto: {}", masterDto);
        masterService.addMaster(masterDto);
        return " -master \"" + masterDto.getName() + "\" has been added to service.";
    }

    @GetMapping("masters/check")
    public String checkMasters() {
        log.info("Method checkMasters");
        if (masterService.getNumberMasters() == 0) {
                throw new BusinessException("There are no masters");
            }
        return "verification was successfully";
    }

    @DeleteMapping("masters")
    public String deleteMaster(@RequestBody MasterDto masterDto) {
        log.info("Method deleteMaster");
        log.trace("Parameter index: {}", masterDto);
        masterService.deleteMaster(masterDto);
        return " -master has been deleted successfully!";
    }

    @GetMapping("masters/sort-by-alphabet")
    public List<MasterDto> getMasterByAlphabet() {
        log.info("Method getMasterByAlphabet");
        return masterService.getMasterByAlphabet();
    }

    @GetMapping("masters/sort-by-busy")
    public List<MasterDto> getMasterByBusy() {
        log.info("Method getMasterByBusy");
        return masterService.getMasterByBusy();
    }

    @GetMapping("masters/free")
    public List<MasterDto> getFreeMasters(@RequestHeader String stringExecuteDate) {
        log.info("Method getFreeMasters");
        log.trace("Parameter stringExecuteDate: {}", stringExecuteDate);
        return masterService.getFreeMastersByDate(DateUtil.getDatesFromString(stringExecuteDate, true));
    }
    //ToDo all method crud ???
}