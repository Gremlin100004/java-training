package com.senla.carservice.controller;

import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.service.CarOfficeService;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.util.DateUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/")
@NoArgsConstructor
@Slf4j
public class CarOfficeController {
    @Autowired
    private CarOfficeService carOfficeService;
    @Autowired
    private MasterService masterService;
    @Autowired
    private PlaceService placeService;

    //Todo move to Order, return DTO, change in front
    @GetMapping("numberOfFreePlaces")
    @ResponseStatus(HttpStatus.OK)
    public ClientMessageDto getFreePlacesMastersByDate(@RequestParam String date) {
        Date dateFree = DateUtil.getDatesFromString(date, false);
        Date startDayDate = DateUtil.bringStartOfDayDate(dateFree);
        Long numberFreeMasters = masterService.getNumberFreeMastersByDate(startDayDate);
        Long numberFreePlace = placeService.getNumberFreePlaceByDate(startDayDate);
        return new ClientMessageDto("- number free places in service: " + numberFreePlace + "\n- number free masters in service: " +
                                 numberFreeMasters);
    }

    //Todo move to Order and return date , change in front
    @GetMapping("nearestFreeDate")
    @ResponseStatus(HttpStatus.OK)
    public ClientMessageDto getNearestFreeDate() {
        return new ClientMessageDto("Nearest free date: " + DateUtil.getStringFromDate(carOfficeService.getNearestFreeDate(), false));
    }

    //Todo move to Order
    @PostMapping("export")
    @ResponseStatus(HttpStatus.OK)
    public ClientMessageDto exportEntities() {
        carOfficeService.exportEntities();
        return new ClientMessageDto("Export completed successfully!");
    }

    //Todo move to Order
    @PostMapping("import")
    @ResponseStatus(HttpStatus.OK)
    public ClientMessageDto importEntities() {
        carOfficeService.importEntities();
        return new ClientMessageDto("Imported completed successfully!");
    }

}
