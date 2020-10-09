package com.senla.carservice.controller;

import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.enumaration.MasterSortParameter;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.util.DateUtil;
import lombok.NoArgsConstructor;
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
public class MasterController {
    @Autowired
    private MasterService masterService;
    @GetMapping
    public List<MasterDto> getMasters() {
        return masterService.getMasters();
    }

    @PostMapping()
    public MasterDto addMaster(@RequestBody MasterDto masterDto) {
        return masterService.addMaster(masterDto);
    }

    @GetMapping("check")
    public ClientMessageDto checkMasters() {
        masterService.checkMasters();
        return new ClientMessageDto("verification was successfully");
    }

    @DeleteMapping("{id}")
    public ClientMessageDto deleteMaster(@PathVariable("id") Long masterId) {
        masterService.deleteMaster(masterId);
        return new ClientMessageDto(" -master has been deleted successfully!");
    }

    @GetMapping("sort")
    public List<MasterDto> getSortMasters(@RequestParam String sortParameter) {
        try {
            return masterService.getSortMasters(MasterSortParameter.valueOf(sortParameter));
        } catch (IllegalArgumentException exception) {
            throw new BusinessException("Wrong sortParameter");
        }
    }

    @GetMapping("free")
    public List<MasterDto> getFreeMasters(@RequestParam String stringExecuteDate) {
        return masterService.getFreeMastersByDate(DateUtil.getDatesFromString(stringExecuteDate.replace('%', ' '), true));
    }

    @GetMapping("{id}/master/orders")
    public List<OrderDto> getMasterOrders(@RequestParam Long masterId) {
        return masterService.getMasterOrders(masterId);
    }

}
