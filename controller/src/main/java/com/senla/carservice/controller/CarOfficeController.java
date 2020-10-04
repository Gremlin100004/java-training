package com.senla.carservice.controller;

import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.service.CarOfficeService;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.util.DateUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("number-of-free-places")
    public ClientMessageDto getFreePlacesMastersByDate(@RequestParam String date) {
        log.info("Method getFreePlacesMastersByDate");
        log.trace("Parameter date: {}", date);
        Date dateFree = DateUtil.getDatesFromString(date, false);
        Date startDayDate = DateUtil.bringStartOfDayDate(dateFree);
        Long numberFreeMasters = masterService.getNumberFreeMastersByDate(startDayDate);
        Long numberFreePlace = placeService.getNumberFreePlaceByDate(startDayDate);
        return new ClientMessageDto("- number free places in service: " + numberFreePlace + "\n- number free masters in service: " +
                                 numberFreeMasters);
    }
    @GetMapping("nearest-free-date")
    public ClientMessageDto getNearestFreeDate() {
        log.info("Method getNearestFreeDate");
        return new ClientMessageDto("Nearest free date: " + DateUtil.getStringFromDate(carOfficeService.getNearestFreeDate(), false));
    }

    @GetMapping("export")
    public ClientMessageDto exportEntities() {
        log.info("Method exportEntities");
        carOfficeService.exportEntities();
        return new ClientMessageDto("Export completed successfully!");
    }

    @GetMapping("import")
    public ClientMessageDto importEntities() {
        log.info("Method importEntities");
        carOfficeService.importEntities();
        return new ClientMessageDto("Imported completed successfully!");
    }
}