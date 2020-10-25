package com.senla.carservice.controller;

import com.senla.carservice.controller.exception.ControllerException;
import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.dto.LongDto;
import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.enumaration.MasterSortParameter;
import com.senla.carservice.util.DateUtil;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/masters")
@NoArgsConstructor
public class MasterController {
    @Autowired
    private MasterService masterService;

    @GetMapping
    public List<MasterDto> getMasters(@RequestParam(required = false) MasterSortParameter sortParameter,
            @RequestParam (required = false) String stringExecuteDate) {
        if (sortParameter == null && stringExecuteDate == null) {
            return masterService.getMasters();
        } else if (stringExecuteDate == null) {
            return masterService.getSortMasters(sortParameter);
        } else if (sortParameter == null) {
            return masterService.getFreeMastersByDate(DateUtil.getDatesFromString(stringExecuteDate.replace('%', ' '), true));
        } else {
            throw new ControllerException("Wrong request parameters");
        }
    }

    @GetMapping("/numberMasters")
    public LongDto getNumberMasters(@RequestParam(required = false) String date) {
        LongDto longDto = new LongDto();
        if (date == null) {
            longDto.setNumber(masterService.getNumberMasters());
        } else {
            Date dateFree = DateUtil.getDatesFromString(date, false);
            Date startDayDate = DateUtil.bringStartOfDayDate(dateFree);
            longDto.setNumber(masterService.getNumberFreeMastersByDate(startDayDate));
        }
        return longDto;
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MasterDto addMaster(@RequestBody MasterDto masterDto) {

        return masterService.addMaster(masterDto);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ClientMessageDto deleteMaster(@PathVariable("id") Long masterId) {
        masterService.deleteMaster(masterId);
        return new ClientMessageDto("Master has been deleted successfully");
    }

    @GetMapping("/{id}/orders")
    public List<OrderDto> getMasterOrders(@PathVariable("id") Long masterId) {
        return masterService.getMasterOrders(masterId);
    }

}
