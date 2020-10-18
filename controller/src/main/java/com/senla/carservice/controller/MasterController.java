package com.senla.carservice.controller;

import com.senla.carservice.controller.exception.ControllerException;
import com.senla.carservice.controller.util.EnumUtil;
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
import org.springframework.security.access.prepost.PreAuthorize;
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

//    @PreAuthorize("hasPermission('READ_PRIVILEGE')")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MasterDto> getMasters(@RequestParam(required = false) String sortParameter,
            @RequestParam (required = false) String stringExecuteDate) {
        if (sortParameter == null && stringExecuteDate == null) {
            return masterService.getMasters();
        } else if (stringExecuteDate == null) {
            if (EnumUtil.isValidEnum(MasterSortParameter.values(), sortParameter)) {
                return masterService.getSortMasters(MasterSortParameter.valueOf(sortParameter));
            } else {
                throw new ControllerException("Wrong sort parameter");
            }
        } else if (sortParameter == null) {
            return masterService.getFreeMastersByDate(DateUtil.getDatesFromString(stringExecuteDate.replace('%', ' '), true));
        } else {
            throw new ControllerException("Wrong request parameters");
        }
    }

    @GetMapping("/numberMasters")
    @ResponseStatus(HttpStatus.OK)
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MasterDto addMaster(@RequestBody MasterDto masterDto) {

        return masterService.addMaster(masterDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientMessageDto deleteMaster(@PathVariable("id") Long masterId) {
        masterService.deleteMaster(masterId);
        return new ClientMessageDto("Master has been deleted successfully");
    }

    @GetMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getMasterOrders(@PathVariable("id") Long masterId) {
        return masterService.getMasterOrders(masterId);
    }

}
