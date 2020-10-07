package com.senla.carservice.controller;

import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.util.DateUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/masters")
@NoArgsConstructor
@Slf4j
public class MasterController {
    @Autowired
    private MasterService masterService;
    @GetMapping
    public List<MasterDto> getMasters() {
        log.info("Method getMasters");
        return masterService.getMasters();
    }

    @PostMapping()
    public MasterDto addMaster(@RequestBody MasterDto masterDto) {
        log.info("Method addMaster");
        log.trace("Parameter masterDto: {}", masterDto);
        return masterService.addMaster(masterDto);
    }

    @GetMapping("check")
    public ClientMessageDto checkMasters() {
        log.info("Method checkMasters");
        masterService.checkMasters();
        return new ClientMessageDto("verification was successfully");
    }

    @DeleteMapping("{id}")
    public ClientMessageDto deleteMaster(@PathVariable("id") Long masterId) {
        log.info("Method deleteMaster");
        log.trace("Parameter masterId: {}", masterId);
        masterService.deleteMaster(masterId);
        return new ClientMessageDto(" -master has been deleted successfully!");
    }

    @GetMapping("sort/byAlphabet")
    public List<MasterDto> getMasterByAlphabet() {
        log.info("Method getMasterByAlphabet");
        return masterService.getMasterByAlphabet();
    }

    @GetMapping("sort/byBusy")
    public List<MasterDto> getMasterByBusy() {
        log.info("Method getMasterByBusy");
        return masterService.getMasterByBusy();
    }

    @GetMapping("free")
    public List<MasterDto> getFreeMasters(@RequestParam String stringExecuteDate) {
        log.info("Method getFreeMasters");
        log.trace("Parameter stringExecuteDate: {}", stringExecuteDate);
        return masterService.getFreeMastersByDate(DateUtil.getDatesFromString(stringExecuteDate.replace('%', ' '), true));
    }

    @GetMapping("{id}/master/orders")
    public List<OrderDto> getMasterOrders(@RequestParam Long masterId) {
        log.info("Method getMasterOrders");
        log.trace("Parameter masterId: {}", masterId);
        return masterService.getMasterOrders(masterId);
    }

}
